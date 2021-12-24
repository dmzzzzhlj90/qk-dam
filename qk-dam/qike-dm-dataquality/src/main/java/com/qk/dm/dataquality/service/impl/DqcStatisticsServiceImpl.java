package com.qk.dm.dataquality.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.qk.dm.dataquality.constant.DqcConstant;
import com.qk.dm.dataquality.constant.ruletemplate.DimensionTypeEnum;
import com.qk.dm.dataquality.constant.ruletemplate.TempTypeEnum;
import com.qk.dm.dataquality.constant.schedule.InstanceStateTypeEnum;
import com.qk.dm.dataquality.dolphinapi.dto.ProcessInstanceResultDTO;
import com.qk.dm.dataquality.dolphinapi.dto.ProcessInstanceSearchDTO;
import com.qk.dm.dataquality.dolphinapi.dto.ProcessTaskInstanceResultDTO;
import com.qk.dm.dataquality.dolphinapi.dto.ProcessTaskInstanceSearchDTO;
import com.qk.dm.dataquality.entity.*;
import com.qk.dm.dataquality.mapstruct.mapper.DqcProcessInstanceMapper;
import com.qk.dm.dataquality.repositories.DqcRuleDirRepository;
import com.qk.dm.dataquality.repositories.DqcRuleTemplateRepository;
import com.qk.dm.dataquality.repositories.DqcSchedulerBasicInfoRepository;
import com.qk.dm.dataquality.repositories.DqcSchedulerRulesRepository;
import com.qk.dm.dataquality.service.DqcStatisticsService;
import com.qk.dm.dataquality.vo.DqcProcessInstanceVO;
import com.qk.dm.dataquality.vo.TaskInstanceVO;
import com.qk.dm.dataquality.vo.statistics.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author shenpj
 * @date 2021/12/21 5:31 下午
 * @since 1.0.0
 */
@Service
public class DqcStatisticsServiceImpl implements DqcStatisticsService {
    private final DqcRuleTemplateRepository dqcRuleTemplateRepository;
    private final DqcSchedulerBasicInfoRepository dqcSchedulerBasicInfoRepository;
    private final DqcSchedulerRulesRepository dqcSchedulerRulesRepository;
    private final DolphinScheduler dolphinScheduler;
    private final DqcRuleDirRepository dqcRuleDirRepository;

    private final QDqcRuleTemplate qDqcRuleTemplate = QDqcRuleTemplate.dqcRuleTemplate;
    private final QDqcSchedulerBasicInfo qDqcSchedulerBasicInfo = QDqcSchedulerBasicInfo.dqcSchedulerBasicInfo;
    private final QDqcSchedulerRules qDqcSchedulerRules = QDqcSchedulerRules.dqcSchedulerRules;
    private final QDqcRuleDir qDqcRuleDir = QDqcRuleDir.dqcRuleDir;

    public DqcStatisticsServiceImpl(DqcRuleTemplateRepository dqcRuleTemplateRepository,
                                    DqcSchedulerBasicInfoRepository dqcSchedulerBasicInfoRepository,
                                    DqcSchedulerRulesRepository dqcSchedulerRulesRepository, DolphinScheduler dolphinScheduler, DqcRuleDirRepository dqcRuleDirRepository) {
        this.dqcRuleTemplateRepository = dqcRuleTemplateRepository;
        this.dqcSchedulerBasicInfoRepository = dqcSchedulerBasicInfoRepository;
        this.dqcSchedulerRulesRepository = dqcSchedulerRulesRepository;
        this.dolphinScheduler = dolphinScheduler;
        this.dqcRuleDirRepository = dqcRuleDirRepository;
    }

    @Override
    public DataSummaryVO statistics() {
        return DataSummaryVO.builder()
                //规则模版统计数据
                .ruleTemplate(ruleTemplateStatistics())
                //作业统计数据
                .jobInfo(basicInfoStatistics())
                //实例统计数据
                .instance(instanceStatistics())
                .successsState(instanceStateStatistics(InstanceStateTypeEnum.SUCCESS.getCode()))
                .failureState(instanceStateStatistics(InstanceStateTypeEnum.FAILURE.getCode()))
                .build();
    }

    /**
     * 规则模版统计数据
     *
     * @return
     */
    @Override
    public RuleTemplateVO ruleTemplateStatistics() {
        return RuleTemplateVO.builder()
                //规则模版总数
                .count(dqcRuleTemplateRepository.count())
                //规则模版内置模版数
                .systemCount(dqcRuleTemplateRepository.count(qDqcRuleTemplate.tempType.eq(TempTypeEnum.BUILT_IN_SYSTEM.getCode())))
                //规则模版自定义模版数
                .customCount(dqcRuleTemplateRepository.count(qDqcRuleTemplate.tempType.eq(TempTypeEnum.CUSTOM.getCode())))
                .build();
    }

    /**
     * 作业统计数据
     *
     * @return
     */
    @Override
    public JobInfoVO basicInfoStatistics() {
        //检测表数
        List<String> allByTables = dqcSchedulerRulesRepository.findAllTables();
        Set<String> tables = allByTables.stream().flatMap(item -> Objects.requireNonNull(DqcConstant.jsonStrToList(item)).stream()).collect(Collectors.toSet());
        //检测字段数
        List<String> allByFields = dqcSchedulerRulesRepository.findAllFields();
        Set<String> fields = allByFields.stream().flatMap(item -> Objects.requireNonNull(DqcConstant.jsonStrToList(item)).stream()).collect(Collectors.toSet());
        return JobInfoVO.builder()
                //作业数
                .count(dqcSchedulerBasicInfoRepository.count())
                .tableCount(tables.size())
                .fieldCount(fields.size())
                .build();
    }

    /**
     * 实例统计数据
     *
     * @return
     */
    @Override
    public InstanceVO instanceStatistics() {
        String failure = null;
        //实例数
        ProcessInstanceResultDTO processInstance = dolphinScheduler.instanceList(getInstanceSearchDTO(1, 1, failure));
        //执行成功数
        ProcessInstanceResultDTO processInstanceSuccess = dolphinScheduler.instanceList(getInstanceSearchDTO(1, 1, InstanceStateTypeEnum.SUCCESS.getCode()));
        //执行失败数
        ProcessInstanceResultDTO processInstanceFailure = dolphinScheduler.instanceList(getInstanceSearchDTO(1, 1, InstanceStateTypeEnum.FAILURE.getCode()));
        return InstanceVO.builder()
                .count(processInstance.getTotal())
                .successCount(processInstanceSuccess.getTotal())
                .failureCount(processInstanceFailure.getTotal())
                .build();
    }

    private ProcessInstanceSearchDTO getInstanceSearchDTO(int pageNo, int pageSize, String failure) {
        return ProcessInstanceSearchDTO.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .stateType(failure)
                .build();
    }

    /**
     * 实例状态统计
     * @param stateType
     * @return
     */
    public JobInfoVO instanceStateStatistics(String stateType) {
        //调度查询当日所有任务实例
        List<TaskInstanceVO> totalList = getDolphinTaskInstanceList(null,stateType);
        Set<Long> taskCodeSet = totalList.stream().map(TaskInstanceVO::getTaskCode).collect(Collectors.toSet());
        //检测表数
        List<String> allByTables = dqcSchedulerRulesRepository.findAllTablesByTaskCode(taskCodeSet);
        Set<String> tables = allByTables.stream().flatMap(item -> Objects.requireNonNull(DqcConstant.jsonStrToList(item)).stream()).collect(Collectors.toSet());
        //检测字段数
        List<String> allByFields = dqcSchedulerRulesRepository.findAllFieldsByTaskCode(taskCodeSet);
        Set<String> fields = allByFields.stream().flatMap(item -> Objects.requireNonNull(DqcConstant.jsonStrToList(item)).stream()).collect(Collectors.toSet());
        return JobInfoVO.builder()
                //作业数
                .count((long) totalList.size())
                .tableCount(tables.size())
                .fieldCount(fields.size())
                .build();
    }

    @Override
    public List<DimensionVO> dimensionStatistics() {
        //调度查询当日所有任务实例
        List<TaskInstanceVO> totalList = getDolphinTaskInstanceList(new Date(), null);
        //success TaskCode->count
        Map<Long, Long> successTaskCodeCountMap = totalList.stream()
                .filter(item -> item.getState().equals(InstanceStateTypeEnum.SUCCESS.getCode()))
                .collect(Collectors.groupingBy(TaskInstanceVO::getTaskCode, Collectors.counting()));
        //failure TaskCode->count
        Map<Long, Long> failureTaskCodeCountMap = totalList.stream()
                .filter(item -> item.getState().equals(InstanceStateTypeEnum.FAILURE.getCode()))
                .collect(Collectors.groupingBy(TaskInstanceVO::getTaskCode, Collectors.counting()));
        //获取dimensionType -> 流程实例中的taskCode
        Map<String, String> dimensionTypeAndtaskCode = getTaskCodeAndDimensionType(totalList);
        //处理封装
        return getDimensionList(successTaskCodeCountMap, failureTaskCodeCountMap, dimensionTypeAndtaskCode);
    }

    private List<DimensionVO> getDimensionList(Map<Long, Long> successTaskCodeCountMap, Map<Long, Long> failureTaskCodeCountMap, Map<String, String> dimensionTypeAndtaskCode) {
        List<DimensionVO> dimensionList = new ArrayList<>();
        for (Map.Entry<String, String> dimTypeMap : dimensionTypeAndtaskCode.entrySet()) {
            AtomicReference<Long> successNumber = new AtomicReference<>(0L);
            AtomicReference<Long> failureNumber = new AtomicReference<>(0L);
            Arrays.asList(dimTypeMap.getValue().split(",")).forEach(item -> {
                Long taskCode = Long.valueOf(item);
                if (successTaskCodeCountMap.get(taskCode) != null) {
                    successNumber.updateAndGet(v -> v + successTaskCodeCountMap.get(taskCode));
                }
                if (failureTaskCodeCountMap.get(taskCode) != null) {
                    failureNumber.updateAndGet(v -> v + failureTaskCodeCountMap.get(taskCode));
                }
            });

            DimensionVO dimensionVO;
            if (successNumber.get() > 0) {
                dimensionVO = new DimensionVO();
                dimensionVO.setDqcDimType(Objects.requireNonNull(DimensionTypeEnum.fromValue(dimTypeMap.getKey())).getName());
                dimensionVO.setExecCount(successNumber.get());
                dimensionVO.setDqcState(InstanceStateTypeEnum.SUCCESS.getValue());
                dimensionList.add(dimensionVO);
            }
            if (failureNumber.get() > 0) {
                dimensionVO = new DimensionVO();
                dimensionVO.setDqcDimType(Objects.requireNonNull(DimensionTypeEnum.fromValue(dimTypeMap.getKey())).getName());
                dimensionVO.setExecCount(failureNumber.get());
                dimensionVO.setDqcState(InstanceStateTypeEnum.FAILURE.getValue());
                dimensionList.add(dimensionVO);
            }
        }
        return dimensionList;
    }

    /**********************************************************************************/

    private Map<String, String> getTaskCodeAndDimensionType(List<TaskInstanceVO> totalList) {
        //获取所有taskCode->模版id
        Map<Long, Long> rulesMap = getTaskCodeAndRuleTempId(totalList);
        //获取所有的模版id->纬度
        Map<Long, String> dimensionTypeMap = getRuleTempIdAndDimensionType();
        //更改taskCode->模版id->纬度 为 纬度 -> taskCodeList
        return getTaskCodeAndDimensionType(rulesMap, dimensionTypeMap);
    }

    private Map<String, String> getTaskCodeAndDimensionType(Map<Long, Long> rulesMap, Map<Long, String> dimensionTypeMap) {
        Map<String, String> rulesDimensionTypeMap = new HashMap<>(8);
        for (Map.Entry<Long, Long> map : rulesMap.entrySet()) {
            rulesDimensionTypeMap.put(dimensionTypeMap.get(map.getValue()), rulesDimensionTypeMap.get(dimensionTypeMap.get(map.getValue())) != null
                    ? rulesDimensionTypeMap.get(dimensionTypeMap.get(map.getValue())) + "," + map.getKey() : map.getKey().toString());
        }
        return rulesDimensionTypeMap;
    }

    private Map<Long, String> getRuleTempIdAndDimensionType() {
        List<DqcRuleTemplate> templateList = dqcRuleTemplateRepository.findAll();
        return templateList.stream().collect(Collectors.toMap(DqcRuleTemplate::getId, DqcRuleTemplate::getDimensionType));
    }

    private Map<Long, Long> getTaskCodeAndRuleTempId(List<TaskInstanceVO> totalList) {
        Set<Long> taskCodeSet = totalList.stream().map(TaskInstanceVO::getTaskCode).collect(Collectors.toSet());
        List<DqcSchedulerRules> schedulerRulesList = (List<DqcSchedulerRules>) dqcSchedulerRulesRepository.findAll(qDqcSchedulerRules.taskCode.in(taskCodeSet));
        return schedulerRulesList.stream().collect(Collectors.toMap(DqcSchedulerRules::getTaskCode, DqcSchedulerRules::getRuleTempId));
    }

    /**************************本日任务实例列表开始********************************************************/

    private List<TaskInstanceVO> getDolphinTaskInstanceList(Date date, String stateType) {
        ProcessTaskInstanceSearchDTO taskInstanceSearchDTO = getProcessTaskInstanceSearchDTO(1, 10, date, stateType);
        List<TaskInstanceVO> totalList = new ArrayList<>();
        //查询所有的任务实例
        getTaskInstanceList(taskInstanceSearchDTO, totalList);
        return totalList;
    }

    private void getTaskInstanceList(ProcessTaskInstanceSearchDTO taskInstanceSearchDTO, List<TaskInstanceVO> totalList) {
        ProcessTaskInstanceResultDTO taskInstanceResultDTO = dolphinScheduler.taskInstanceList(taskInstanceSearchDTO);
        totalList.addAll(DqcProcessInstanceMapper.INSTANCE.taskInstanceVO(taskInstanceResultDTO.getTotalList()));
        if (taskInstanceResultDTO.getTotalPage() > taskInstanceResultDTO.getCurrentPage()) {
            taskInstanceSearchDTO.setPageNo(taskInstanceSearchDTO.getPageNo() + 1);
            getTaskInstanceList(taskInstanceSearchDTO, totalList);
        }
    }

    private ProcessTaskInstanceSearchDTO getProcessTaskInstanceSearchDTO(int pageNo, int pageSize, Date date, String stateType) {
        return ProcessTaskInstanceSearchDTO.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .startDate(date != null ? DateUtil.formatDateTime(DateUtil.beginOfDay(date)) : null)
                .endDate(date != null ? DateUtil.formatDateTime(DateUtil.endOfDay(date)) : null)
                .stateType(stateType)
                .build();
    }

    /**************************本日任务实例列表结束********************************************************/

    @Override
    public List<RuleDirVO> dirStatistics() {
        //查询当日所有实例
        List<DqcProcessInstanceVO> totalList = getInstanceList();
        //从所有实例中结合本地数据查询各dir运行次数 dir->count
        Map<String, Long> dirCount = getDirCount(totalList);
        //获取所有分类目录 dirId->dirName
        Map<String, String> dqcRuleDirMap = getDqcRuleDirMap();
        //当天执行总数量,直接缩小100倍算出百分比
        Integer count = totalList.size() / 100;
        List<RuleDirVO> ruleDirList = new ArrayList<>();
        //计算百分比
        dirCount.entrySet().stream().peek(item ->
                ruleDirList.add(
                        RuleDirVO.builder()
                                .type(dqcRuleDirMap.get(item.getKey()))
                                .value(NumberUtil.div(item.getValue(), count, 2))
                                .build()
                )
        ).collect(Collectors.toList());
        return ruleDirList;
    }

    private Map<String, String> getDqcRuleDirMap() {
        List<DqcRuleDir> dqcRuleDirs = dqcRuleDirRepository.findAll();
        Map<String, String> dqcRuleDirMap = dqcRuleDirs.stream().collect(Collectors.toMap(DqcRuleDir::getRuleDirId, DqcRuleDir::getRuleDirName));
        return dqcRuleDirMap;
    }

    private Map<String, Long> getDirCount(List<DqcProcessInstanceVO> totalList) {
        //获取code->dir
        Map<Long, String> codeDirMap = getCodeDirMap(totalList);
        //各code运行次数 code->count
        Map<Long, Long> codeCount = totalList.stream().collect(Collectors.groupingBy(DqcProcessInstanceVO::getProcessDefinitionCode, Collectors.counting()));
        //各dir运行次数 dir->count
        return getDirCount(codeDirMap, codeCount);
    }

    private Map<String, Long> getDirCount(Map<Long, String> codeDirMap, Map<Long, Long> codeCount) {
        Map<String, Long> dirCount = new HashMap<>();
        for (Map.Entry<Long, String> map : codeDirMap.entrySet()) {
            dirCount.put(map.getValue(), dirCount.get(map.getValue()) != null ? dirCount.get(map.getValue()) + codeCount.get(map.getKey()) : codeCount.get(map.getKey()));
        }
        return dirCount;
    }

    private Map<Long, String> getCodeDirMap(List<DqcProcessInstanceVO> totalList) {
        //拿到所有的code
        Set<Long> codeSet = totalList.stream().map(DqcProcessInstanceVO::getProcessDefinitionCode).collect(Collectors.toSet());
        //查询到所有的任务定义
        List<DqcSchedulerBasicInfo> basicInfoList = (List<DqcSchedulerBasicInfo>) dqcSchedulerBasicInfoRepository.findAll(qDqcSchedulerBasicInfo.processDefinitionCode.in(codeSet));
        //获取code->dir
        return basicInfoList.stream().collect(Collectors.toMap(DqcSchedulerBasicInfo::getProcessDefinitionCode, DqcSchedulerBasicInfo::getDirId));
    }

    /**************************本日流程实例列表开始********************************************************/

    private List<DqcProcessInstanceVO> getInstanceList() {
        List<DqcProcessInstanceVO> totalList = new ArrayList<>();
        ProcessInstanceSearchDTO instanceSearchDTO = getInstanceSearchDTO(1, 10, new Date());
        //查询出今天所有实例
        getInstanceList(instanceSearchDTO, totalList);
        return totalList;
    }

    private void getInstanceList(ProcessInstanceSearchDTO instanceSearchDTO, List<DqcProcessInstanceVO> totalList) {
        ProcessInstanceResultDTO instanceResultDTO = dolphinScheduler.instanceList(instanceSearchDTO);
        totalList.addAll(DqcProcessInstanceMapper.INSTANCE.userDqcProcessInstanceVO(instanceResultDTO.getTotalList()));
        if (instanceResultDTO.getTotalPage() > instanceResultDTO.getCurrentPage()) {
            instanceSearchDTO.setPageNo(instanceSearchDTO.getPageNo() + 1);
            getInstanceList(instanceSearchDTO, totalList);
        }
    }

    private ProcessInstanceSearchDTO getInstanceSearchDTO(int pageNo, int pageSize, Date date) {
        return ProcessInstanceSearchDTO.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .startDate(DateUtil.formatDateTime(DateUtil.beginOfDay(date)))
                .endDate(DateUtil.formatDateTime(DateUtil.endOfDay(date)))
                .build();
    }


}
