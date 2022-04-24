package com.qk.dm.dataingestion.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.datacenter.client.ApiException;
import com.qk.datacenter.model.ProcessDefinition;
import com.qk.datacenter.model.Result;
import com.qk.dm.dataingestion.datax.DataxDolphinClient;
import com.qk.dm.dataingestion.entity.DisMigrationBaseInfo;
import com.qk.dm.dataingestion.entity.QDisMigrationBaseInfo;
import com.qk.dm.dataingestion.enums.IngestionType;
import com.qk.dm.dataingestion.mapstruct.mapper.DisBaseInfoMapper;
import com.qk.dm.dataingestion.mapstruct.mapper.MetaDataColumnMapper;
import com.qk.dm.dataingestion.model.*;
import com.qk.dm.dataingestion.service.DataMigrationService;
import com.qk.dm.dataingestion.service.DisBaseInfoService;
import com.qk.dm.dataingestion.service.DisColumnInfoService;
import com.qk.dm.dataingestion.service.DisSchedulerConfigService;
import com.qk.dm.dataingestion.strategy.DataSyncFactory;
import com.qk.dm.dataingestion.vo.*;
import com.qk.dm.DataBaseService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DataMigrationServiceImpl implements DataMigrationService {

    private JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;
    private final QDisMigrationBaseInfo qDisMigrationBaseInfo = QDisMigrationBaseInfo.disMigrationBaseInfo;
    private final DisBaseInfoService baseInfoService;
    private final DisColumnInfoService columnInfoService;
    private final DisSchedulerConfigService schedulerConfigService;
    private final DataSyncFactory dataSyncFactory;
    private final DataxDolphinClient dataxDolphinClient;
    private final DataBaseService dataBaseService;

    @PostConstruct
    public void initFactory() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    public DataMigrationServiceImpl(EntityManager entityManager, DisBaseInfoService baseInfoService, DisColumnInfoService columnInfoService,
                                    DisSchedulerConfigService schedulerConfigService, DataSyncFactory dataSyncFactory, DataxDolphinClient dataxDolphinClient, DataBaseService dataBaseService) {
        this.entityManager = entityManager;
        this.baseInfoService = baseInfoService;
        this.columnInfoService = columnInfoService;
        this.schedulerConfigService = schedulerConfigService;
        this.dataSyncFactory = dataSyncFactory;
        this.dataxDolphinClient = dataxDolphinClient;
        this.dataBaseService = dataBaseService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(DataMigrationVO dataMigrationVO) throws ApiException {
        //添加基础信息
        DisMigrationBaseInfoVO baseInfo = dataMigrationVO.getBaseInfo();
        if(baseInfoService.exists(baseInfo)){
            throw new BizException("作业名称"+baseInfo.getJobName()+"已存在！！！");
        }
        Long baseInfoId = baseInfoService.add(dataMigrationVO.getBaseInfo());
        //添加字段信息
        columnInfoService.batchAdd(columnMerge(dataMigrationVO,baseInfoId));
        //添加任务信息
        DisSchedulerConfigVO schedulerConfig = dataMigrationVO.getSchedulerConfig();
        schedulerConfig.setBaseInfoId(baseInfoId);
        schedulerConfigService.add(schedulerConfig);
        //生成dataxjson,创建流程定义
        createDataxJson(dataMigrationVO,baseInfoId);
        //上线
        dataxDolphinClient.dolphinProcessRelease(dataMigrationVO.getBaseInfo().getTaskCode(),
                ProcessDefinition.ReleaseStateEnum.ONLINE);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String ids) {
        List<Long> idList = Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());
        baseInfoService.delete(idList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DataMigrationVO dataMigrationVO) throws ApiException {
        DisMigrationBaseInfoVO baseInfo = dataMigrationVO.getBaseInfo();
        //修改作业基础信息
        baseInfoService.update(baseInfo);
        //修改作业字段信息
        columnInfoService.update(baseInfo.getId(),columnMerge(dataMigrationVO,baseInfo.getId()));
        //修改任务配置信息
        schedulerConfigService.update(dataMigrationVO.getSchedulerConfig());
        //生成datax json
        String dataxJson = dataSyncFactory.transJson(dataMigrationVO,
                IngestionType.getVal(baseInfo.getSourceConnectType()),
                IngestionType.getVal(baseInfo.getTargetConnectType()));
        //下线
        dataxDolphinClient.dolphinProcessRelease(dataMigrationVO.getBaseInfo().getTaskCode(),
                ProcessDefinition.ReleaseStateEnum.OFFLINE);
        //修改流程定义
        dataxDolphinClient.updateProcessDefinition(baseInfo.getJobName(),
                baseInfo.getTaskCode(),dataxJson,new DolphinTaskDefinitionPropertiesBean());
        //上线
        dataxDolphinClient.dolphinProcessRelease(dataMigrationVO.getBaseInfo().getTaskCode(),
                ProcessDefinition.ReleaseStateEnum.ONLINE);
    }

    @Override
    public DataMigrationVO detail(Long id) {
        DisMigrationBaseInfoVO baseDetail = baseInfoService.detail(id);
        List<DisColumnInfoVO> columnList = columnInfoService.list(id);
       return DataMigrationVO.builder().baseInfo(baseDetail)
               .columnList(ColumnVO.builder().sourceColumnList(sourceList(baseDetail,columnList))
               .targetColumnList(targetList(baseDetail,columnList)).build())
               .schedulerConfig(schedulerConfigService.detail(id)).build();
    }

    @Override
    public Map<String,Object> jsonDetail(Long id) {
        DisMigrationBaseInfoVO baseDetail = baseInfoService.detail(id);
        List<DisColumnInfoVO> columnList = columnInfoService.list(id);
        DataMigrationVO dataMigrationVO = DataMigrationVO.builder().baseInfo(baseDetail)
                .columnList(ColumnVO.builder().sourceColumnList(sourceList(baseDetail,columnList))
                        .targetColumnList(targetList(baseDetail,columnList)).build())
                .schedulerConfig(schedulerConfigService.detail(id)).build();
        Map<String, Object> map = Maps.newHashMap();
        String json = dataSyncFactory.transJson(dataMigrationVO,
                IngestionType.getVal(baseDetail.getSourceConnectType()),
                IngestionType.getVal(baseDetail.getTargetConnectType()));

        map.put("dataxJson",json);
        return map;

    }

    /**
     * 合并组装字段信息，根据索引下标一一对应
     * @param dataMigrationVO
     * @param baseInfoId
     * @return
     */
    private List<DisColumnInfoVO> columnMerge(DataMigrationVO dataMigrationVO, Long baseInfoId){
         ColumnVO column = dataMigrationVO.getColumnList();
        List<ColumnVO.Column> sourceColumnList = column.getSourceColumnList();
        List<ColumnVO.Column> targetColumnList = column.getTargetColumnList();
        List<DisColumnInfoVO> columnList = Lists.newArrayList();
       for(int i=0;i<sourceColumnList.size();i++){
           ColumnVO.Column sourceColumn = sourceColumnList.get(i);
           ColumnVO.Column targetColumn = targetColumnList.get(i);
           columnList.add(DisColumnInfoVO.builder().sourceName(sourceColumn.getName())
                   .sourceType(sourceColumn.getDataType()).targetName(targetColumn.getName())
                   .targetType(targetColumn.getDataType()).baseInfoId(baseInfoId).build());
       }
       return columnList;
    }

    @Override
    public PageResultVO<DisMigrationBaseInfoVO> pageList(DisParamsVO paramsVO) {
        Map<String, Object> map;
        try {
            map = queryByParams(paramsVO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("查询失败!!!");
        }
        List<DisMigrationBaseInfo> list = (List<DisMigrationBaseInfo>) map.get("list");
        List<DisMigrationBaseInfoVO > voList = process(DisBaseInfoMapper.INSTANCE.listVO(list));
        return new PageResultVO<>(
                (long) map.get("total"),
                paramsVO.getPagination().getPage(),
                paramsVO.getPagination().getSize(),
                voList);
    }

    private List<DisMigrationBaseInfoVO> process(List<DisMigrationBaseInfoVO> list){
        list.forEach(e->{
            DisBaseInfoMapper.INSTANCE.of(processInstance(e.getTaskCode()),e);;
        });
        return list;
    }

    /**
     * 获取流程
     * @param processDefinitionCode
     * @return
     */
    private ProcessInstanceVO processInstance(Long processDefinitionCode){
        Result instance = dataxDolphinClient.getProcessInstance(processDefinitionCode);

            ProcessInstanceResultVO processInstanceResult =  new Gson().fromJson(
                    GsonUtil.toJsonString(instance.getData()),ProcessInstanceResultVO.class);

            return CollectionUtils.firstElement(processInstanceResult.getTotalList());

    }

    @Override
    public ColumnVO getColumnList(DisMigrationBaseInfoVO vo) {
            List<DisColumnInfoVO> columnList = columnInfoService.list(vo.getId());

        return ColumnVO.builder().sourceColumnList(sourceList(vo,columnList))
                .targetColumnList(targetList(vo,columnList)).build();
    }

    @Override
    public void processRunning(String ids) {
        log.info("运行任务参数【{}】",ids);
        List<Long> idList = Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());
        //baseInfoService
    }

    /**
     * 组装源字段列表
     * @param vo
     * @param columnList
     * @return
     */
    private List<ColumnVO.Column> sourceList(DisMigrationBaseInfoVO vo,List<DisColumnInfoVO> columnList){
       // 如果是编辑根据连接类型、数据库、表条件查看数据库是否存在
        // 存在获取数据中的字段，如果不存在获取元数据的字段信息
        if(baseInfoService.sourceExists(vo)){
            return columnList.stream().map(e ->
                    ColumnVO.Column.builder().dataType(e.getSourceType())
                            .name(e.getSourceName()).build()).collect(Collectors.toList());
        }else {
            List sourceColumnList = dataBaseService.getAllColumn(vo.getSourceConnectType(), vo.getSourceConnect(),
                    vo.getSourceDatabase(), vo.getSourceTable());
            return MetaDataColumnMapper.INSTANCE.of(sourceColumnList);
        }
    }

    private List<ColumnVO.Column> targetList(DisMigrationBaseInfoVO vo,List<DisColumnInfoVO> columnList){
        if(baseInfoService.targetExists(vo)){
            return columnList.stream().map(e ->
                    ColumnVO.Column.builder().dataType(e.getTargetType())
                            .name(e.getTargetName()).build()).collect(Collectors.toList());
        }else {
            List targetColumnList = dataBaseService.getAllColumn(vo.getTargetConnectType(), vo.getTargetConnect(),
                    vo.getTargetDatabase(), vo.getTargetTable());
            return MetaDataColumnMapper.INSTANCE.of(targetColumnList);
        }
    }

    private Map<String, Object> queryByParams(DisParamsVO paramsVO) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        checkCondition(booleanBuilder, paramsVO);
        Map<String, Object> result = Maps.newHashMap();
        long count = jpaQueryFactory.select(qDisMigrationBaseInfo.count()).from(qDisMigrationBaseInfo).where(booleanBuilder).fetchOne();
        List<DisMigrationBaseInfo> baseInfoList = jpaQueryFactory
                .select(qDisMigrationBaseInfo)
                .from(qDisMigrationBaseInfo)
                .where(booleanBuilder)
                .orderBy(qDisMigrationBaseInfo.id.desc())
                .offset((long) (paramsVO.getPagination().getPage() - 1)
                        * paramsVO.getPagination().getSize())
                .limit(paramsVO.getPagination().getSize()).fetch();
        result.put("list", baseInfoList);
        result.put("total", count);
        return result;
    }
    private void createDataxJson(DataMigrationVO dataMigrationVO,Long baseInfoId) throws ApiException {
       DisMigrationBaseInfoVO baseInfo = dataMigrationVO.getBaseInfo();
        String dataxJson = dataSyncFactory.transJson(dataMigrationVO,
                IngestionType.getVal(baseInfo.getSourceConnectType()),
                IngestionType.getVal(baseInfo.getTargetConnectType()));

        Result result = dataxDolphinClient.createProcessDefinition(baseInfo.getJobName(),
                dataxJson);
        DolphinTaskDefinitionPropertiesBean data =  new Gson().fromJson(GsonUtil.toJsonString(result.getData())
                ,DolphinTaskDefinitionPropertiesBean.class);

        baseInfo.setTaskCode(data.getCode());
        baseInfo.setId(baseInfoId);
        baseInfoService.update(baseInfo);
    }

    public void checkCondition(BooleanBuilder booleanBuilder, DisParamsVO paramsVO) {
        if (Objects.nonNull(paramsVO.getJobName())) {
            booleanBuilder.and(qDisMigrationBaseInfo.jobName.contains(paramsVO.getJobName()));
        }
        if (Objects.nonNull(paramsVO.getDirId())&&!Objects.equals(paramsVO.getDirId(),"-1")) {
            booleanBuilder.and(qDisMigrationBaseInfo.dirId.contains(paramsVO.getDirId()));
        }
    }

}
