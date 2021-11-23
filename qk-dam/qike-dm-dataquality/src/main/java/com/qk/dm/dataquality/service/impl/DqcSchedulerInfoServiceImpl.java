package com.qk.dm.dataquality.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataquality.constant.*;
import com.qk.dm.dataquality.entity.*;
import com.qk.dm.dataquality.mapstruct.mapper.DqcSchedulerBasicInfoMapper;
import com.qk.dm.dataquality.mapstruct.mapper.DqcSchedulerConfigMapper;
import com.qk.dm.dataquality.mapstruct.mapper.DqcSchedulerRulesMapper;
import com.qk.dm.dataquality.repositories.DqcSchedulerConfigRepository;
import com.qk.dm.dataquality.repositories.DqcSchedulerRulesRepository;
import com.qk.dm.dataquality.service.DqcSchedulerBasicInfoService;
import com.qk.dm.dataquality.service.DqcSchedulerConfigService;
import com.qk.dm.dataquality.service.DqcSchedulerInfoService;
import com.qk.dm.dataquality.service.DqcSchedulerRulesService;
import com.qk.dm.dataquality.vo.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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

    private final DqcSchedulerBasicInfoService dqcSchedulerBasicInfoService;
    private final DqcSchedulerRulesService dqcSchedulerRulesService;
    private final DqcSchedulerConfigService dqcSchedulerConfigService;


    private final EntityManager entityManager;
    private JPAQueryFactory jpaQueryFactory;

    @Autowired
    public DqcSchedulerInfoServiceImpl(DqcSchedulerRulesRepository dqcSchedulerRulesRepository,
                                       DqcSchedulerConfigRepository dqcSchedulerConfigRepository,
                                       DqcSchedulerBasicInfoService dqcSchedulerBasicInfoService,
                                       DqcSchedulerRulesService dqcSchedulerRulesService,
                                       DqcSchedulerConfigService dqcSchedulerConfigService,
                                       EntityManager entityManager) {
        this.dqcSchedulerRulesRepository = dqcSchedulerRulesRepository;
        this.dqcSchedulerConfigRepository = dqcSchedulerConfigRepository;
        this.dqcSchedulerBasicInfoService = dqcSchedulerBasicInfoService;
        this.dqcSchedulerRulesService = dqcSchedulerRulesService;
        this.dqcSchedulerConfigService = dqcSchedulerConfigService;
        this.entityManager = entityManager;
    }

    @PostConstruct
    public void initFactory() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public PageResultVO<DqcSchedulerInfoVO> searchPageList(DqcSchedulerInfoParamsVO schedulerInfoParamsVO) {
        List<DqcSchedulerInfoVO> dqcSchedulerInfoVOList = new ArrayList<>();
        long total;
        //基础信息查询
        Map<String, Object> basicInfoMap = null;
        try {
            basicInfoMap = queryDqcSchedulerByParams(schedulerInfoParamsVO);
            List<DqcSchedulerBasicInfo> dqcSchedulerBasicInfoList = (List<DqcSchedulerBasicInfo>) basicInfoMap.get("list");
            total = (long) basicInfoMap.get("total");
            List<String> taskIds = dqcSchedulerBasicInfoList.stream().map(DqcSchedulerBasicInfo::getJobId).collect(Collectors.toList());

            //规则信息查询
            Map<String, List<DqcSchedulerRulesVO>> schedulerRulesMap = getSchedulerRulesMap(taskIds);
            //配置信息查询
            Map<String, List<DqcSchedulerConfigVO>> schedulerConfigMap = getSchedulerConfigMap(taskIds);

            //封装统一调度信息
            buildSchedulerInfo(dqcSchedulerInfoVOList, dqcSchedulerBasicInfoList, schedulerRulesMap, schedulerConfigMap);
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
    public void insert(DqcSchedulerInfoVO dqcSchedulerInfoVO) {
        //基础信息
        dqcSchedulerBasicInfoService.insert(dqcSchedulerInfoVO.getDqcSchedulerBasicInfoVO());
        //规则信息
        dqcSchedulerRulesService.insertBulk(dqcSchedulerInfoVO.getDqcSchedulerRulesVOList());
        //调度配置信息
        dqcSchedulerConfigService.insert(dqcSchedulerInfoVO.getDqcSchedulerConfigVO());
        //TODO
    }

    @Override
    public void update(DqcSchedulerInfoVO dqcSchedulerInfoVO) {
        //基础信息
        dqcSchedulerBasicInfoService.update(dqcSchedulerInfoVO.getDqcSchedulerBasicInfoVO());
        //规则信息
        dqcSchedulerRulesService.updateBulk(dqcSchedulerInfoVO.getDqcSchedulerRulesVOList());
        //调度配置信息
        dqcSchedulerConfigService.update(dqcSchedulerInfoVO.getDqcSchedulerConfigVO());
        //TODO
    }

    @Override
    public void deleteOne(Long valueOf) {

    }

    @Override
    public void deleteBulk(String ids) {

    }

    @Override
    public SchedulerRuleConstantsVO getSchedulerRuLeConstants() {
        SchedulerRuleConstantsVO.SchedulerRuleConstantsVOBuilder constantsVOBuilder = SchedulerRuleConstantsVO.builder();

        constantsVOBuilder
                .notifyStateEnum(NotifyStateEnum.getAllValue())
                .notifyLevelEnum(NotifyLevelEnum.getAllValue())
                .calculateEngineTypeEnum(CalculateEngineTypeEnum.getAllValue())
                .ruleTypeEnum(RuleTypeEnum.getAllValue())
                .schedulerTypeEnum(SchedulerTypeEnum.getAllValue());
        return constantsVOBuilder.build();
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

        if (!ObjectUtils.isEmpty(schedulerInfoParamsVO.getDirId())) {
            booleanBuilder.and(qDqcSchedulerBasicInfo.dirId.eq(schedulerInfoParamsVO.getDirId()));
        }
        if (!ObjectUtils.isEmpty(schedulerInfoParamsVO.getJobId())) {
            booleanBuilder.and(qDqcSchedulerBasicInfo.jobId.eq(schedulerInfoParamsVO.getJobId()));
        }
        if (!StringUtils.isEmpty(schedulerInfoParamsVO.getBeginDay())
                && !StringUtils.isEmpty(schedulerInfoParamsVO.getEndDay())) {
            StringTemplate dateExpr =
                    Expressions.stringTemplate("DATE_FORMAT({0},'%Y-%m-%d %H:%i:%S')", qDqcSchedulerBasicInfo.gmtModified);
            booleanBuilder.and(
                    dateExpr.between(schedulerInfoParamsVO.getBeginDay(), schedulerInfoParamsVO.getEndDay()));
        }
    }

    private Map<String, List<DqcSchedulerConfigVO>> getSchedulerConfigMap(List<String> taskIds) {
        Iterable<DqcSchedulerConfig> dqcSchedulerConfigIterable = dqcSchedulerConfigRepository.findAll(QDqcSchedulerConfig.dqcSchedulerConfig.jobId.in(taskIds));
        List<DqcSchedulerConfigVO> schedulerConfigVOList = new ArrayList<>();
        for (DqcSchedulerConfig dqcSchedulerConfig : dqcSchedulerConfigIterable) {
            DqcSchedulerConfigVO dqcSchedulerConfigVO = DqcSchedulerConfigMapper.INSTANCE.userDqcSchedulerConfigVO(dqcSchedulerConfig);
            schedulerConfigVOList.add(dqcSchedulerConfigVO);
        }
        return schedulerConfigVOList.stream().collect(Collectors.groupingBy(DqcSchedulerConfigVO::getJobId));
    }

    private Map<String, List<DqcSchedulerRulesVO>> getSchedulerRulesMap(List<String> taskIds) {
        Iterable<DqcSchedulerRules> dqcSchedulerRulesIterable = dqcSchedulerRulesRepository.findAll(QDqcSchedulerRules.dqcSchedulerRules.jobId.in(taskIds));
        List<DqcSchedulerRulesVO> schedulerRulesVOList = new ArrayList<>();
        for (DqcSchedulerRules dqcSchedulerRules : dqcSchedulerRulesIterable) {
            DqcSchedulerRulesVO dqcSchedulerRulesVO = DqcSchedulerRulesMapper.INSTANCE.userDqcSchedulerRulesVO(dqcSchedulerRules);
            schedulerRulesVOList.add(dqcSchedulerRulesVO);
        }
        return schedulerRulesVOList.stream().collect(Collectors.groupingBy(DqcSchedulerRulesVO::getJobId));
    }

    private void buildSchedulerInfo(List<DqcSchedulerInfoVO> dqcSchedulerInfoVOList,
                                    List<DqcSchedulerBasicInfo> dqcSchedulerBasicInfoList,
                                    Map<String, List<DqcSchedulerRulesVO>> schedulerRulesMap,
                                    Map<String, List<DqcSchedulerConfigVO>> schedulerConfigMap) {
        if (dqcSchedulerBasicInfoList != null && dqcSchedulerBasicInfoList.size() > 0) {
            for (DqcSchedulerBasicInfo dqcSchedulerBasicInfo : dqcSchedulerBasicInfoList) {
                DqcSchedulerInfoVO.DqcSchedulerInfoVOBuilder schedulerInfoVOBuilder = DqcSchedulerInfoVO.builder();
                String taskId = dqcSchedulerBasicInfo.getJobId();
                DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO = DqcSchedulerBasicInfoMapper.INSTANCE.userDqcSchedulerBasicInfoVO(dqcSchedulerBasicInfo);
                List<DqcSchedulerRulesVO> schedulerRulesVOList = schedulerRulesMap.get(taskId);
                List<DqcSchedulerConfigVO> dqcSchedulerConfigVOList = schedulerConfigMap.get(taskId);

                schedulerInfoVOBuilder.dqcSchedulerBasicInfoVO(dqcSchedulerBasicInfoVO)
                        .dqcSchedulerRulesVOList(schedulerRulesVOList)
                        .dqcSchedulerConfigVO(dqcSchedulerConfigVOList.get(0));

                dqcSchedulerInfoVOList.add(schedulerInfoVOBuilder.build());
            }
        }
    }

}
