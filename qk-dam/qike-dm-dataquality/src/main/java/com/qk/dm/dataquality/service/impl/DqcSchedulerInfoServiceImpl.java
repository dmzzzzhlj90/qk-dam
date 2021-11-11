package com.qk.dm.dataquality.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataquality.entity.*;
import com.qk.dm.dataquality.mapstruct.mapper.DqcSchedulerBasicInfoMapper;
import com.qk.dm.dataquality.mapstruct.mapper.DqcSchedulerConfigMapper;
import com.qk.dm.dataquality.mapstruct.mapper.DqcSchedulerRulesMapper;
import com.qk.dm.dataquality.repositories.DqcSchedulerConfigRepository;
import com.qk.dm.dataquality.repositories.DqcSchedulerRulesRepository;
import com.qk.dm.dataquality.service.DqcSchedulerInfoService;
import com.qk.dm.dataquality.vo.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 数据质量_规则调度入口
 *
 * @author wjq
 * @date 2021/11/10
 * @since 1.0.0
 */
@Service
public class DqcSchedulerInfoServiceImpl implements DqcSchedulerInfoService {

    private final DqcSchedulerRulesRepository dqcSchedulerRulesRepository;
    private final DqcSchedulerConfigRepository dqcSchedulerConfigRepository;


    private final EntityManager entityManager;
    private JPAQueryFactory jpaQueryFactory;

    @Autowired
    public DqcSchedulerInfoServiceImpl(DqcSchedulerRulesRepository dqcSchedulerRulesRepository,
                                       DqcSchedulerConfigRepository dqcSchedulerConfigRepository,
                                       EntityManager entityManager) {
        this.dqcSchedulerRulesRepository = dqcSchedulerRulesRepository;
        this.dqcSchedulerConfigRepository = dqcSchedulerConfigRepository;
        this.entityManager = entityManager;
    }

    @PostConstruct
    public void initFactory() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public PageResultVO<DqcSchedulerInfoVO> searchPageList(DqcSchedulerInfoParamsVO schedulerInfoParamsVO) {
        List<DqcSchedulerInfoVO> dqcSchedulerInfoVOList = new ArrayList<>();
        long total = 0;
        //基础信息查询
        Map<String, Object> basicInfoMap = null;
        try {
            basicInfoMap = queryDqcSchedulerByParams(schedulerInfoParamsVO);
            List<DqcSchedulerBasicInfo> dqcSchedulerBasicInfoList = (List<DqcSchedulerBasicInfo>) basicInfoMap.get("list");
            total = (long) basicInfoMap.get("total");
            List<Long> taskIds = dqcSchedulerBasicInfoList.stream().map(DqcSchedulerBasicInfo::getTaskId).collect(Collectors.toList());

            //规则信息查询
            Map<Long, List<DqcSchedulerRulesVO>> schedulerRulesMap = getSchedulerRulesMap(taskIds);
            //配置信息查询
            Map<Long, List<DqcSchedulerConfigVO>> schedulerConfigMap = getSchedulerConfigMap(taskIds);

            //封装统一调度信息
            schedulerInfoBuiler(dqcSchedulerInfoVOList, dqcSchedulerBasicInfoList, schedulerRulesMap, schedulerConfigMap);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("查询失败!!!");
        }
        return new PageResultVO<>(
                total,
                schedulerInfoParamsVO.getPagination().getPage(),
                schedulerInfoParamsVO.getPagination().getSize(),
                dqcSchedulerInfoVOList);
    }

    @Override
    public void delete(Long valueOf) {

    }

    @Override
    public void deleteBulk(String ids) {

    }

    public Map<String, Object> queryDqcSchedulerByParams(DqcSchedulerInfoParamsVO schedulerInfoParamsVO) {
        QDqcSchedulerBasicInfo qDqcSchedulerBasicInfo = QDqcSchedulerBasicInfo.dqcSchedulerBasicInfo;
        Map<String, Object> result = new HashMap<>();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        checkCondition(booleanBuilder, qDqcSchedulerBasicInfo, schedulerInfoParamsVO);
        long count =
                jpaQueryFactory
                        .select(qDqcSchedulerBasicInfo.count())
                        .from(qDqcSchedulerBasicInfo)
                        .where(booleanBuilder)
                        .fetchOne();
        List<DqcSchedulerBasicInfo> dsdBasicInfoList =
                jpaQueryFactory
                        .select(qDqcSchedulerBasicInfo)
                        .from(qDqcSchedulerBasicInfo)
                        .where(booleanBuilder)
                        .orderBy(qDqcSchedulerBasicInfo.gmtModified.asc())
                        .offset((schedulerInfoParamsVO.getPagination().getPage() - 1) * schedulerInfoParamsVO.getPagination().getSize())
                        .limit(schedulerInfoParamsVO.getPagination().getSize())
                        .fetch();

        result.put("list", dsdBasicInfoList);
        result.put("total", count);
        return result;
    }

    public void checkCondition(BooleanBuilder booleanBuilder,
                               QDqcSchedulerBasicInfo qDqcSchedulerBasicInfo,
                               DqcSchedulerInfoParamsVO schedulerInfoParamsVO) {

        if (!StringUtils.isEmpty(String.valueOf(schedulerInfoParamsVO.getDirId()))) {
            booleanBuilder.and(qDqcSchedulerBasicInfo.dirId.eq(schedulerInfoParamsVO.getDirId()));
        }

        if (!StringUtils.isEmpty(String.valueOf(schedulerInfoParamsVO.getTaskId()))) {
            booleanBuilder.and(qDqcSchedulerBasicInfo.taskId.eq(schedulerInfoParamsVO.getTaskId()));
        }

        if (!StringUtils.isEmpty(schedulerInfoParamsVO.getBeginDay())
                && !StringUtils.isEmpty(schedulerInfoParamsVO.getEndDay())) {
            StringTemplate dateExpr =
                    Expressions.stringTemplate("DATE_FORMAT({0},'%Y-%m-%d %H:%i:%S')", qDqcSchedulerBasicInfo.gmtModified);
            booleanBuilder.and(
                    dateExpr.between(schedulerInfoParamsVO.getBeginDay(), schedulerInfoParamsVO.getEndDay()));
        }
    }

    private Map<Long, List<DqcSchedulerConfigVO>> getSchedulerConfigMap(List<Long> taskIds) {
        Iterable<DqcSchedulerConfig> dqcSchedulerConfigIterable = dqcSchedulerConfigRepository.findAll(QDqcSchedulerConfig.dqcSchedulerConfig.taskId.in(taskIds));
        List<DqcSchedulerConfigVO> schedulerConfigVOList = new ArrayList<>();
        for (DqcSchedulerConfig dqcSchedulerConfig : dqcSchedulerConfigIterable) {
            DqcSchedulerConfigVO dqcSchedulerConfigVO = DqcSchedulerConfigMapper.INSTANCE.userDqcSchedulerConfigVO(dqcSchedulerConfig);
            schedulerConfigVOList.add(dqcSchedulerConfigVO);
        }
        Map<Long, List<DqcSchedulerConfigVO>> schedulerConfigMap = schedulerConfigVOList.stream().collect(Collectors.groupingBy(DqcSchedulerConfigVO::getTaskId));
        return schedulerConfigMap;
    }

    private Map<Long, List<DqcSchedulerRulesVO>> getSchedulerRulesMap(List<Long> taskIds) {
        Iterable<DqcSchedulerRules> dqcSchedulerRulesIterable = dqcSchedulerRulesRepository.findAll(QDqcSchedulerRules.dqcSchedulerRules.taskId.in(taskIds));
        List<DqcSchedulerRulesVO> schedulerRulesVOList = new ArrayList<>();
        for (DqcSchedulerRules dqcSchedulerRules : dqcSchedulerRulesIterable) {
            DqcSchedulerRulesVO dqcSchedulerRulesVO = DqcSchedulerRulesMapper.INSTANCE.userDqcSchedulerRulesVO(dqcSchedulerRules);
            schedulerRulesVOList.add(dqcSchedulerRulesVO);
        }
        Map<Long, List<DqcSchedulerRulesVO>> schedulerRulesMap = schedulerRulesVOList.stream().collect(Collectors.groupingBy(DqcSchedulerRulesVO::getTaskId));
        return schedulerRulesMap;
    }

    private void schedulerInfoBuiler(List<DqcSchedulerInfoVO> dqcSchedulerInfoVOList, List<DqcSchedulerBasicInfo> dqcSchedulerBasicInfoList, Map<Long, List<DqcSchedulerRulesVO>> schedulerRulesMap, Map<Long, List<DqcSchedulerConfigVO>> schedulerConfigMap) {
        for (DqcSchedulerBasicInfo dqcSchedulerBasicInfo : dqcSchedulerBasicInfoList) {
            DqcSchedulerInfoVO.DqcSchedulerInfoVOBuilder dqcSchedulerInfoVOBuilder = DqcSchedulerInfoVO.builder();
            Long taskId = dqcSchedulerBasicInfo.getTaskId();
            DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO = DqcSchedulerBasicInfoMapper.INSTANCE.userDqcSchedulerBasicInfoVO(dqcSchedulerBasicInfo);
            List<DqcSchedulerRulesVO> schedulerRulesVOList = schedulerRulesMap.get(taskId);
            List<DqcSchedulerConfigVO> dqcSchedulerConfigVOList = schedulerConfigMap.get(taskId);

            dqcSchedulerInfoVOBuilder.dqcSchedulerBasicInfoVO(dqcSchedulerBasicInfoVO)
                    .dqcSchedulerRulesVOList(schedulerRulesVOList)
                    .dqcSchedulerConfigVO(dqcSchedulerConfigVOList.get(0));

            dqcSchedulerInfoVOList.add(dqcSchedulerInfoVOBuilder.build());
        }
    }

}
