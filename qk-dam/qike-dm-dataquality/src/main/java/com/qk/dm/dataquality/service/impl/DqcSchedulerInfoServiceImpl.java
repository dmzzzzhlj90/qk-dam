package com.qk.dm.dataquality.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataquality.constant.*;
import com.qk.dm.dataquality.dolphinapi.config.DolphinSchedulerInfoConfig;
import com.qk.dm.dataquality.dolphinapi.service.ProcessDefinitionApiService;
import com.qk.dm.dataquality.entity.*;
import com.qk.dm.dataquality.mapstruct.mapper.DqcSchedulerBasicInfoMapper;
import com.qk.dm.dataquality.mapstruct.mapper.DqcSchedulerConfigMapper;
import com.qk.dm.dataquality.mapstruct.mapper.DqcSchedulerRulesMapper;
import com.qk.dm.dataquality.repositories.DqcSchedulerBasicInfoRepository;
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
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.*;
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
    private final static Logger LOG = LoggerFactory.getLogger(DqcSchedulerInfoServiceImpl.class);

    private final DqcSchedulerBasicInfoRepository dqcSchedulerBasicInfoRepository;
    private final DqcSchedulerRulesRepository dqcSchedulerRulesRepository;
    private final DqcSchedulerConfigRepository dqcSchedulerConfigRepository;

    private final DqcSchedulerBasicInfoService dqcSchedulerBasicInfoService;
    private final DqcSchedulerRulesService dqcSchedulerRulesService;
    private final DqcSchedulerConfigService dqcSchedulerConfigService;
    private final ProcessDefinitionApiService processDefinitionApiService;
    private final DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig;

    private final EntityManager entityManager;
    private JPAQueryFactory jpaQueryFactory;

    @Autowired
    public DqcSchedulerInfoServiceImpl(DqcSchedulerBasicInfoRepository dqcSchedulerBasicInfoRepository,
                                       DqcSchedulerRulesRepository dqcSchedulerRulesRepository,
                                       DqcSchedulerConfigRepository dqcSchedulerConfigRepository,
                                       DqcSchedulerBasicInfoService dqcSchedulerBasicInfoService,
                                       DqcSchedulerRulesService dqcSchedulerRulesService,
                                       DqcSchedulerConfigService dqcSchedulerConfigService,
                                       ProcessDefinitionApiService processDefinitionApiService,
                                       DolphinSchedulerInfoConfig dolphinSchedulerInfoConfig,
                                       EntityManager entityManager) {
        this.dqcSchedulerBasicInfoRepository = dqcSchedulerBasicInfoRepository;
        this.dqcSchedulerRulesRepository = dqcSchedulerRulesRepository;
        this.dqcSchedulerConfigRepository = dqcSchedulerConfigRepository;
        this.dqcSchedulerBasicInfoService = dqcSchedulerBasicInfoService;
        this.dqcSchedulerRulesService = dqcSchedulerRulesService;
        this.dqcSchedulerConfigService = dqcSchedulerConfigService;
        this.processDefinitionApiService = processDefinitionApiService;
        this.dolphinSchedulerInfoConfig = dolphinSchedulerInfoConfig;
        this.entityManager = entityManager;
    }

    @PostConstruct
    public void initFactory() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public PageResultVO<DqcSchedulerBasicInfoVO> searchPageList(DqcSchedulerInfoParamsVO schedulerInfoParamsVO) {
        List<DqcSchedulerBasicInfoVO> dqcSchedulerInfoVOList = new ArrayList<>();
        long total;
        //基础信息查询
        Map<String, Object> basicInfoMap = null;
        try {
            basicInfoMap = queryDqcSchedulerByParams(schedulerInfoParamsVO);
            List<DqcSchedulerBasicInfo> dqcSchedulerBasicInfoList = (List<DqcSchedulerBasicInfo>) basicInfoMap.get("list");
            total = (long) basicInfoMap.get("total");
            List<String> jobIds = dqcSchedulerBasicInfoList.stream().map(DqcSchedulerBasicInfo::getJobId).collect(Collectors.toList());

            //规则信息查询
            Map<String, List<DqcSchedulerRulesVO>> schedulerRulesMap = getSchedulerRulesMap(jobIds);
            //配置信息查询
            Map<String, List<DqcSchedulerConfigVO>> schedulerConfigMap = getSchedulerConfigMap(jobIds);
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
    @Transactional(rollbackFor = Exception.class)
    public void insert(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO) {
        String jobId = UUID.randomUUID().toString().replaceAll("-", "");
        dqcSchedulerBasicInfoVO.setJobId(jobId);
        LOG.info("新生成的JobId: 【{}】 ", jobId);
        //首次添加先设置为0,生成流程实例后获取真正的ID进行更新操作!
        dqcSchedulerBasicInfoVO.setProcessDefinitionCode(0L);
        //基础信息
        dqcSchedulerBasicInfoService.insert(dqcSchedulerBasicInfoVO);
        LOG.info("调度规则,基础信息保存成功!");
        //规则信息
        dqcSchedulerBasicInfoVO.setDqcSchedulerRulesVOList(
                dqcSchedulerRulesService.insertBulk(dqcSchedulerBasicInfoVO.getDqcSchedulerRulesVOList(), jobId));
        LOG.info("调度规则,规则信息保存成功!");
        //调度配置信息
        dqcSchedulerConfigService.insert(dqcSchedulerBasicInfoVO.getDqcSchedulerConfigVO(), jobId);
        LOG.info("调度配置信息保存成功!");
        //创建流程实例ID
        Long processDefinitionCode = processDefinitionApiService.saveAndFlush(dqcSchedulerBasicInfoVO);
        LOG.info("创建流程实例成功!");
        //存储流程实例ID
        updateProcessDefinitionIdByJobId(processDefinitionCode, jobId);
        LOG.info("存储流程实例ID成功!");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO) {
        //基础信息
        dqcSchedulerBasicInfoService.update(dqcSchedulerBasicInfoVO);
        LOG.info("调度规则,基础信息编辑成功!");
        //TODO 规则信息
        dqcSchedulerBasicInfoVO.setDqcSchedulerRulesVOList(
                dqcSchedulerRulesService.updateBulk(dqcSchedulerBasicInfoVO.getDqcSchedulerRulesVOList(), dqcSchedulerBasicInfoVO.getJobId()));
        LOG.info("调度规则,规则信息编辑成功!");
        //调度配置信息
        dqcSchedulerConfigService.update(dqcSchedulerBasicInfoVO.getDqcSchedulerConfigVO());
        LOG.info("调度配置信息编辑成功!");
        //更新流程实例
        processDefinitionApiService.saveAndFlush(dqcSchedulerBasicInfoVO);
        LOG.info("更新流程实例成功!");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOne(String id) {
        //删除基础信息
        DqcSchedulerBasicInfo schedulerBasicInfo = dqcSchedulerBasicInfoService.getInfoById(Long.valueOf(id));
        dqcSchedulerBasicInfoService.deleteOne(Long.valueOf(id));
        //删除规则信息
        dqcSchedulerRulesService.deleteByJobId(schedulerBasicInfo.getJobId());
        //删除调度配置信息
        dqcSchedulerConfigService.deleteByJobId(schedulerBasicInfo.getJobId());
        //删除工作流信息
        processDefinitionApiService.delete(schedulerBasicInfo.getProcessDefinitionCode(), dolphinSchedulerInfoConfig.getProjectCode());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBulk(String ids) {
        //删除基础信息
        List<DqcSchedulerBasicInfo> basicInfoServiceInfoList = dqcSchedulerBasicInfoService.getInfoList(ids);
        dqcSchedulerBasicInfoService.deleteBulk(basicInfoServiceInfoList);
        List<String> jobIds = basicInfoServiceInfoList.stream().map(DqcSchedulerBasicInfo::getJobId).collect(Collectors.toList());
        List<Long> processDefinitionIdList = basicInfoServiceInfoList.stream().map(DqcSchedulerBasicInfo::getProcessDefinitionCode).collect(Collectors.toList());
        //删除规则信息
        dqcSchedulerRulesService.deleteBulkByJobIds(jobIds);
        //删除调度配置信息
        dqcSchedulerConfigService.deleteBulkByJobIds(jobIds);
        //删除工作流信息
        processDefinitionApiService.deleteBulk(processDefinitionIdList, dolphinSchedulerInfoConfig.getProjectCode());
    }

    @Override
    public SchedulerRuleConstantsVO getSchedulerRuLeConstants() {
        SchedulerRuleConstantsVO.SchedulerRuleConstantsVOBuilder constantsVOBuilder = SchedulerRuleConstantsVO.builder();

        constantsVOBuilder
                .notifyLevelEnum(NotifyLevelEnum.getAllValue())
                .notifyStateEnum(NotifyStateEnum.getAllValue())
                .notifyTypeEnum(NotifyTypeEnum.getAllValue())
                .engineTypeEnum(EngineTypeEnum.getAllValue())
                .ruleTypeEnum(RuleTypeEnum.getAllValue())
                .ScanTypeEnum(ScanTypeEnum.getAllValue())
                .schedulerTypeEnum(SchedulerTypeEnum.getAllValue())
                .schedulerStateEnum(SchedulerStateEnum.getAllValue())
                .SchedulerCycleEnum(SchedulerCycleEnum.getAllValue());
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
//                        .orderBy(qDqcSchedulerBasicInfo.schedulerState.desc())
//                        .orderBy(qDqcSchedulerBasicInfo.jobName.desc())
                        .orderBy(qDqcSchedulerBasicInfo.gmtModified.desc())
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

        if (!ObjectUtils.isEmpty(schedulerInfoParamsVO.getJobName())) {
            booleanBuilder.and(qDqcSchedulerBasicInfo.jobName.contains(schedulerInfoParamsVO.getJobName()));
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
        Iterable<DqcSchedulerRules> dqcSchedulerRulesIterable = dqcSchedulerRulesRepository.findAll(QDqcSchedulerRules.dqcSchedulerRules.jobId.in(taskIds), QDqcSchedulerRules.dqcSchedulerRules.gmtModified.desc());
        List<DqcSchedulerRulesVO> schedulerRulesVOList = new ArrayList<>();
        for (DqcSchedulerRules dqcSchedulerRules : dqcSchedulerRulesIterable) {
            DqcSchedulerRulesVO dqcSchedulerRulesVO = DqcSchedulerRulesMapper.INSTANCE.userDqcSchedulerRulesVO(dqcSchedulerRules);
            dqcSchedulerRulesVO.setTableList(DqcConstant.jsonStrToList(dqcSchedulerRules.getTables()));
            dqcSchedulerRulesVO.setFieldList(DqcConstant.jsonStrToList(dqcSchedulerRules.getFields()));
            schedulerRulesVOList.add(dqcSchedulerRulesVO);
        }
        return schedulerRulesVOList.stream().collect(Collectors.groupingBy(DqcSchedulerRulesVO::getJobId));
    }

    private void buildSchedulerInfo(List<DqcSchedulerBasicInfoVO> dqcSchedulerInfoVOList,
                                    List<DqcSchedulerBasicInfo> dqcSchedulerBasicInfoList,
                                    Map<String, List<DqcSchedulerRulesVO>> schedulerRulesMap,
                                    Map<String, List<DqcSchedulerConfigVO>> schedulerConfigMap) {
        if (dqcSchedulerBasicInfoList != null && dqcSchedulerBasicInfoList.size() > 0) {
            for (DqcSchedulerBasicInfo dqcSchedulerBasicInfo : dqcSchedulerBasicInfoList) {
                String taskId = dqcSchedulerBasicInfo.getJobId();
                DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO = DqcSchedulerBasicInfoMapper.INSTANCE.userDqcSchedulerBasicInfoVO(dqcSchedulerBasicInfo);
                //todo 转换类型
//                dqcSchedulerBasicInfoVO.setNotifyThemeIdList(DqcConstant.changeTypeToList(dqcSchedulerBasicInfo.getNotifyThemeId()));
                List<DqcSchedulerRulesVO> schedulerRulesVOList = schedulerRulesMap.get(taskId);
                List<DqcSchedulerConfigVO> dqcSchedulerConfigVOList = schedulerConfigMap.get(taskId);

                if (null != schedulerRulesVOList && schedulerRulesVOList.size() > 0) {
                    dqcSchedulerBasicInfoVO.setDqcSchedulerRulesVOList(schedulerRulesVOList);
                }

                if (null != dqcSchedulerConfigVOList && dqcSchedulerConfigVOList.size() > 0) {
                    dqcSchedulerBasicInfoVO.setDqcSchedulerConfigVO(dqcSchedulerConfigVOList.get(0));
                }
                dqcSchedulerInfoVOList.add(dqcSchedulerBasicInfoVO);
            }
        }
    }

    private void updateProcessDefinitionIdByJobId(Long processDefinitionCode, String jobId) {
        dqcSchedulerBasicInfoRepository.updateProcessDefinitionIdByJobId(processDefinitionCode, jobId);
    }

}
