package com.qk.dm.dataingestion.service.impl;

import com.google.common.collect.Maps;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataingestion.datax.DataxDolphinClient;
import com.qk.dm.dataingestion.entity.DisMigrationBaseInfo;
import com.qk.dm.dataingestion.entity.QDisMigrationBaseInfo;
import com.qk.dm.dataingestion.mapstruct.mapper.DisBaseInfoMapper;
import com.qk.dm.dataingestion.model.*;
import com.qk.dm.dataingestion.service.DataMigrationService;
import com.qk.dm.dataingestion.service.DisBaseInfoService;
import com.qk.dm.dataingestion.service.DisColumnInfoService;
import com.qk.dm.dataingestion.service.DisSchedulerConfigService;
import com.qk.dm.dataingestion.strategy.DataSyncFactory;
import com.qk.dm.dataingestion.vo.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

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

    @PostConstruct
    public void initFactory() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    public DataMigrationServiceImpl(EntityManager entityManager, DisBaseInfoService baseInfoService, DisColumnInfoService columnInfoService,
                                    DisSchedulerConfigService schedulerConfigService, DataSyncFactory dataSyncFactory, DataxDolphinClient dataxDolphinClient) {
        this.entityManager = entityManager;
        this.baseInfoService = baseInfoService;
        this.columnInfoService = columnInfoService;
        this.schedulerConfigService = schedulerConfigService;
        this.dataSyncFactory = dataSyncFactory;
        this.dataxDolphinClient = dataxDolphinClient;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(DataMigrationVO dataMigrationVO) {
        //添加基础信息
        Long baseInfoId = baseInfoService.add(dataMigrationVO.getBaseInfo());
        List<DisColumnInfoVO> columnList = dataMigrationVO.getColumnList();
        columnList.forEach(e->e.setBaseInfoId(baseInfoId));
        //添加字段信息
        columnInfoService.batchAdd(columnList);
        //添加任务信息
        DisSchedulerConfigVO schedulerConfig = dataMigrationVO.getSchedulerConfig();
        schedulerConfig.setBaseInfoId(baseInfoId);
        schedulerConfigService.add(schedulerConfig);

        System.out.println(dataSyncFactory.transJson(dataMigrationVO,IngestionType.MYSQL,IngestionType.HIVE));


    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String ids) {
        List<Long> idList = Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());
        baseInfoService.delete(idList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DataMigrationVO dataMigrationVO) {
        //修改作业基础信息
        baseInfoService.update(dataMigrationVO.getBaseInfo());
        //修改作业字段信息
        columnInfoService.update(dataMigrationVO.getBaseInfo().getId(),dataMigrationVO.getColumnList());
        //修改任务配置信息
        schedulerConfigService.update(dataMigrationVO.getSchedulerConfig());
    }

    @Override
    public DataMigrationVO detail(Long id) {

       return DataMigrationVO.builder().baseInfo(baseInfoService.detail(id))
        .columnList(columnInfoService.list(id))
        .schedulerConfig(schedulerConfigService.detail(id)).build();
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
        List<DisMigrationBaseInfoVO > voList = DisBaseInfoMapper.INSTANCE.listVO(list);
        return new PageResultVO<>(
                (long) map.get("total"),
                paramsVO.getPagination().getPage(),
                paramsVO.getPagination().getSize(),
                voList);
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

    public void checkCondition(BooleanBuilder booleanBuilder, DisParamsVO paramsVO) {
        if (Objects.nonNull(paramsVO.getJobName())) {
            booleanBuilder.and(qDisMigrationBaseInfo.jobName.contains(paramsVO.getJobName()));
        }
    }


}
