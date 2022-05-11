package com.qk.dm.dataingestion.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.datacenter.client.ApiException;
import com.qk.datacenter.model.ProcessDefinition;
import com.qk.datacenter.model.Result;
import com.qk.dm.dataingestion.datax.DataxDolphinClient;
import com.qk.dm.dataingestion.entity.DisMigrationBaseInfo;
import com.qk.dm.dataingestion.entity.QDisMigrationBaseInfo;
import com.qk.dm.dataingestion.enums.IngestionStatusType;
import com.qk.dm.dataingestion.enums.IngestionType;
import com.qk.dm.dataingestion.enums.SchedulerType;
import com.qk.dm.dataingestion.mapstruct.mapper.DisBaseInfoMapper;
import com.qk.dm.dataingestion.mapstruct.mapper.MetaDataColumnMapper;
import com.qk.dm.dataingestion.model.*;
import com.qk.dm.dataingestion.service.*;
import com.qk.dm.dataingestion.strategy.DataSyncFactory;
import com.qk.dm.dataingestion.util.CronUtil;
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

/**
 * 离线数据引入
 * @author wangzp
 * @date 2022/04/07 15:31
 * @since 1.0.0
 */
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
    private final DisDataxJsonService disDataxJsonService;

    @PostConstruct
    public void initFactory() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    public DataMigrationServiceImpl(EntityManager entityManager, DisBaseInfoService baseInfoService, DisColumnInfoService columnInfoService,
                                    DisSchedulerConfigService schedulerConfigService, DataSyncFactory dataSyncFactory, DataxDolphinClient dataxDolphinClient, DataBaseService dataBaseService, DisDataxJsonService disDataxJsonService) {
        this.entityManager = entityManager;
        this.baseInfoService = baseInfoService;
        this.columnInfoService = columnInfoService;
        this.schedulerConfigService = schedulerConfigService;
        this.dataSyncFactory = dataSyncFactory;
        this.dataxDolphinClient = dataxDolphinClient;
        this.dataBaseService = dataBaseService;
        this.disDataxJsonService = disDataxJsonService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(DataMigrationVO dataMigrationVO) throws ApiException {
        //添加基础信息
        DisMigrationBaseInfoVO baseInfo = dataMigrationVO.getBaseInfo();
        if(baseInfoService.exists(baseInfo)){
            throw new BizException("作业名称"+baseInfo.getJobName()+"已存在！！！");
        }
        //添加作业信息
        Long baseInfoId = addTask(dataMigrationVO);
        //创建流程 生成datax json
        createProcessDefinition(dataMigrationVO,baseInfoId);
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String ids) {
        List<Long> idList = Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());
        baseInfoService.delete(idList);
        columnInfoService.delete(idList);
        schedulerConfigService.delete(idList);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DataMigrationVO dataMigrationVO) throws ApiException {
        //修改作业信息
        Long taskCode = updateTask(dataMigrationVO);
        //修改流程定义
        updateProcessDefinition(dataMigrationVO,taskCode);
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
        String dataXJson = disDataxJsonService.findDataxJson(id);

        if (Objects.isNull(dataXJson)) {
            DisMigrationBaseInfoVO baseDetail = baseInfoService.detail(id);
            List<DisColumnInfoVO> columnList = columnInfoService.list(id);

            DataMigrationVO dataMigrationVO = DataMigrationVO.builder().baseInfo(baseDetail)
                    .columnList(ColumnVO.builder().sourceColumnList(sourceList(baseDetail, columnList))
                            .targetColumnList(targetList(baseDetail, columnList)).build())
                    .schedulerConfig(schedulerConfigService.detail(id)).build();

            dataXJson = dataSyncFactory.transJson(dataMigrationVO,
                    IngestionType.getVal(baseDetail.getSourceConnectType()),
                    IngestionType.getVal(baseDetail.getTargetConnectType()));
        }

        return Map.of("dataxJson", parseJson(dataXJson));

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


    @Override
    public ColumnVO getColumnList(DisMigrationBaseInfoVO vo) {
        List<DisColumnInfoVO> columnList = columnInfoService.list(vo.getId());

        return ColumnVO.builder().sourceColumnList(sourceList(vo,columnList))
                .targetColumnList(targetList(vo,columnList)).build();
    }

    @Override
    public void processRunning(String ids) {
        log.info("运行任务参数【{}】",ids);
        List<DisMigrationBaseInfoVO> baseList = baseInfoService.list(
                Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList()));
        if(!CollectionUtils.isEmpty(baseList)){
            baseList.forEach(e->dataxDolphinClient.runing(e.getTaskCode(),null));
        }
    }

    @Override
    public void updateDataxJson(DisJsonParamsVO disJsonParamsVO){
        disDataxJsonService.update(disJsonParamsVO.getId(),disJsonParamsVO.getDataxJson());
        DisMigrationBaseInfoVO baseInfo = baseInfoService.detail(disJsonParamsVO.getId());
        try {
            updateProcessDefinition(baseInfo.getJobName(),baseInfo.getTaskCode(),disJsonParamsVO.getDataxJson());
        } catch (ApiException e) {
            log.error("调用dolphinscheduler 出错【{}】",e.getMessage());
            e.printStackTrace();
        }
    }


    private List<DisMigrationBaseInfoVO> process(List<DisMigrationBaseInfoVO> list){
        list.forEach(e->{
            DisBaseInfoMapper.INSTANCE.of(processInstance(e.getTaskCode()),e);;
        });
        return list;
    }

    /**
     * 获取流程实例
     * @param processDefinitionCode
     * @return
     */
    private ProcessInstanceVO processInstance(Long processDefinitionCode){
        Result instance = dataxDolphinClient.getProcessInstance(processDefinitionCode);

            ProcessInstanceResultVO processInstanceResult =  new Gson().fromJson(
                    GsonUtil.toJsonString(instance.getData()),ProcessInstanceResultVO.class);
        ProcessInstanceVO processInstanceVO = CollectionUtils.firstElement(processInstanceResult.getTotalList());
        if(Objects.nonNull(processInstanceVO)){
            processInstanceVO.setStatus(
                    IngestionStatusType.getVal(processInstanceVO.getState()).getIngestionStatus().getCode());
         }
        return processInstanceVO;

    }

    /**
     * 添加作业任务信息
     * @param dataMigrationVO
     * @return
     */
    private Long addTask(DataMigrationVO dataMigrationVO){
        Long baseInfoId = baseInfoService.add(dataMigrationVO.getBaseInfo());
        //添加字段信息
        columnInfoService.batchAdd(columnMerge(dataMigrationVO,baseInfoId));
        //添加任务信息
        DisSchedulerConfigVO schedulerConfig = dataMigrationVO.getSchedulerConfig();
        schedulerConfig.setBaseInfoId(baseInfoId);
        schedulerConfigService.add(schedulerConfig);
        return baseInfoId;
    }

    /**
     * 修改作业信息
     * @param dataMigrationVO
     */
    public Long updateTask(DataMigrationVO dataMigrationVO){
        DisMigrationBaseInfoVO baseInfo = dataMigrationVO.getBaseInfo();
        if(Objects.isNull(baseInfo.getId())){
            throw new BizException("修改作业时id不能为空！！！");
        }
        //修改作业基础信息
        Long taskCode = baseInfoService.update(baseInfo);
        //修改作业字段信息
        columnInfoService.update(baseInfo.getId(),columnMerge(dataMigrationVO,baseInfo.getId()));
        //修改任务定时
        updateScheduler(taskCode,baseInfo.getId(),dataMigrationVO.getSchedulerConfig());

        return taskCode;
    }

    /**
     * 修改任务定时
     * @param processDefinitionCode
     * @param baseInfoId
     * @param schedulerConfig
     */
    private void updateScheduler(Long processDefinitionCode, Long baseInfoId,DisSchedulerConfigVO schedulerConfig){
        //判断是否是周期调度
        if(Objects.equals(schedulerConfig.getSchedulerType(), SchedulerType.SINGLE.getCode())){return;}
        DisSchedulerConfigVO disSchedulerConfig = schedulerConfigService.detail(baseInfoId);
        if(Objects.isNull(disSchedulerConfig.getSchedulerId())){
            //添加定时
            createScheduler(disSchedulerConfig,processDefinitionCode,baseInfoId);
        }else {
            //修改定时
            updateSchedule(disSchedulerConfig);
        }
        //修改任务配置信息
        schedulerConfigService.update(baseInfoId, schedulerConfig);
    }

    /**
     * 生成datax json,修改流程定义
     * @param dataMigrationVO
     * @param taskCode
     * @throws ApiException
     */
    public void updateProcessDefinition(DataMigrationVO dataMigrationVO,Long taskCode) throws ApiException {
        DisMigrationBaseInfoVO baseInfo = dataMigrationVO.getBaseInfo();
        //生成datax json
        String dataxJson = dataSyncFactory.transJson(dataMigrationVO,
                IngestionType.getVal(baseInfo.getSourceConnectType()),
                IngestionType.getVal(baseInfo.getTargetConnectType()));
        updateProcessDefinition(baseInfo.getJobName(),taskCode,dataxJson);
    }

    /**
     * 格式化json字符串
     * @param jsonString
     * @return
     */
    private String parseJson(String jsonString){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(JsonParser.parseString(jsonString));

    }

    /**
     * 修改流程定义（datax json数据）
     * @param jobName
     * @param taskCode
     * @param dataxJson
     */
    private void updateProcessDefinition(String jobName, Long taskCode,String dataxJson)throws ApiException{
        //下线
        dataxDolphinClient.dolphinProcessRelease(taskCode,
                ProcessDefinition.ReleaseStateEnum.OFFLINE);
        //修改流程定义
        dataxDolphinClient.updateProcessDefinition(jobName,
                taskCode,dataxJson,new DolphinTaskDefinitionPropertiesBean());
        //上线
        dataxDolphinClient.dolphinProcessRelease(taskCode,
                ProcessDefinition.ReleaseStateEnum.ONLINE);
    }

    /**
     * 生成datax json，创建流程定义、 上线
     * @param dataMigrationVO
     * @param baseInfoId
     * @throws ApiException
     */
    private void createProcessDefinition(DataMigrationVO dataMigrationVO,Long baseInfoId) throws ApiException {
        //生成dataxjson,创建流程定义
        Long processDefinitionCode = createDataxJson(dataMigrationVO, baseInfoId);
        //上线
        dataxDolphinClient.dolphinProcessRelease(dataMigrationVO.getBaseInfo().getTaskCode(),
                ProcessDefinition.ReleaseStateEnum.ONLINE);
        //创建定时
        createScheduler(dataMigrationVO.getSchedulerConfig(),processDefinitionCode,baseInfoId);
    }

    /**
     * 组装源字段列表
     * @param vo
     * @param columnList
     * @return
     */
    private List<ColumnVO.Column> sourceList(DisMigrationBaseInfoVO vo,List<DisColumnInfoVO> columnList){
       // 如果是编辑则根据连接类型、数据库、表条件查看数据库是否存在
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

    /**
     * 组装目标字段列表
     * @param vo
     * @param columnList
     * @return
     */
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
    private Long createDataxJson(DataMigrationVO dataMigrationVO,Long baseInfoId) throws ApiException {
       DisMigrationBaseInfoVO baseInfo = dataMigrationVO.getBaseInfo();
       //生成datax json
        String dataxJson = dataSyncFactory.transJson(dataMigrationVO,
                IngestionType.getVal(baseInfo.getSourceConnectType()),
                IngestionType.getVal(baseInfo.getTargetConnectType()));
        //创建流程定义
        Result result = dataxDolphinClient.createProcessDefinition(baseInfo.getJobName(),
                dataxJson);
        DolphinTaskDefinitionPropertiesBean data =  new Gson().fromJson(GsonUtil.toJsonString(result.getData())
                ,DolphinTaskDefinitionPropertiesBean.class);

        baseInfo.setTaskCode(data.getCode());
        baseInfo.setId(baseInfoId);
        baseInfoService.update(baseInfo);
        return data.getCode();
    }

   private void createScheduler(DisSchedulerConfigVO disSchedulerConfig,Long processDefinitionCode,
                          Long baseInfoId){
       //判断是否是周期调度
       if(Objects.equals(disSchedulerConfig.getSchedulerType(), SchedulerType.SINGLE.getCode())){return;}
        //判断是否是周期调度
        Integer scheduleId = createSchedule(disSchedulerConfig, processDefinitionCode);
        //将定时id保存至数据库
        schedulerConfigService.update(baseInfoId,scheduleId);
        //判断定时开关
        if(disSchedulerConfig.getTimeSwitch()){
            log.info("定时任务上线scheduleId【{}】",scheduleId);
            //dataxDolphinClient.onlineSchedule(scheduleId);
        }
    }
    //创建定时
    private Integer createSchedule(DisSchedulerConfigVO disSchedulerConfig,Long processDefinitionCode){
        log.info("创建定时任务开始，流程定义id:【{}】",processDefinitionCode);
        dataxDolphinClient.createSchedule(processDefinitionCode,disSchedulerConfig.getEffectiveTimeStart(),
                disSchedulerConfig.getEffectiveTimeEnd(), CronUtil.createCron(disSchedulerConfig));
        Result result = dataxDolphinClient.searchSchedule(processDefinitionCode);
        ScheduleResultVO scheduleResultVO =  new Gson().fromJson(
                GsonUtil.toJsonString(result.getData()),ScheduleResultVO.class);
        ScheduleVO scheduleVO = CollectionUtils.firstElement(scheduleResultVO.getTotalList());
        return  Objects.isNull(scheduleVO)?null:scheduleVO.getId();

    }
    //修改定时
    public void updateSchedule(DisSchedulerConfigVO disSchedulerConfig){
        if(Objects.equals(disSchedulerConfig.getSchedulerType(), SchedulerType.SINGLE.getCode())){return;}
        //判断定时开关
        if(disSchedulerConfig.getTimeSwitch()){
            log.info("定时任务下线scheduleId【{}】",disSchedulerConfig.getSchedulerId());
            //dataxDolphinClient.offlineSchedule(disSchedulerConfig.getSchedulerId());
        }
        dataxDolphinClient.updateSchedule(disSchedulerConfig.getSchedulerId(),disSchedulerConfig.getEffectiveTimeStart(),
                disSchedulerConfig.getEffectiveTimeEnd(),disSchedulerConfig.getCron());
        log.info("定时任务上线scheduleId【{}】",disSchedulerConfig.getSchedulerId());
        //dataxDolphinClient.onlineSchedule(disSchedulerConfig.getSchedulerId());


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
