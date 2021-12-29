package com.qk.dm.dataquality.vo.statistics.handler;

import cn.hutool.core.date.DateUtil;
import com.qk.dm.dataquality.constant.ruletemplate.DimensionTypeEnum;
import com.qk.dm.dataquality.constant.schedule.InstanceStateTypeEnum;
import com.qk.dm.dataquality.dolphinapi.dto.ProcessTaskInstanceResultDTO;
import com.qk.dm.dataquality.dolphinapi.dto.ProcessTaskInstanceSearchDTO;
import com.qk.dm.dataquality.entity.DqcRuleTemplate;
import com.qk.dm.dataquality.entity.DqcSchedulerRules;
import com.qk.dm.dataquality.mapstruct.mapper.DqcProcessInstanceMapper;
import com.qk.dm.dataquality.service.DqcRuleTemplateService;
import com.qk.dm.dataquality.service.DqcSchedulerRulesService;
import com.qk.dm.dataquality.service.impl.DolphinScheduler;
import com.qk.dm.dataquality.vo.TaskInstanceVO;
import com.qk.dm.dataquality.vo.statistics.DimensionVO;
import com.qk.dm.dataquality.vo.statistics.JobInfoVO;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author shenpj
 * @date 2021/12/24 10:37 上午
 * @since 1.0.0
 */
@Component
public class TaskInstanceHandler {
    private static final int pageNo = 1;
    private static final int pageSize = 10;

    private final DolphinScheduler dolphinScheduler;
    private final DqcSchedulerRulesService dqcSchedulerRulesService;
    private final DqcRuleTemplateService dqcRuleTemplateService;

    public TaskInstanceHandler(DolphinScheduler dolphinScheduler,
                               DqcSchedulerRulesService dqcSchedulerRulesService,
                               DqcRuleTemplateService dqcRuleTemplateService) {
        this.dolphinScheduler = dolphinScheduler;
        this.dqcSchedulerRulesService = dqcSchedulerRulesService;
        this.dqcRuleTemplateService = dqcRuleTemplateService;
    }

    private ProcessTaskInstanceSearchDTO getProcessTaskInstanceSearchDTO(Date date, String stateType) {
        return ProcessTaskInstanceSearchDTO.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .startDate(date != null ? DateUtil.formatDateTime(DateUtil.beginOfDay(date)) : null)
                .endDate(date != null ? DateUtil.formatDateTime(DateUtil.endOfDay(date)) : null)
                .stateType(stateType)
                .build();
    }

    private ProcessTaskInstanceResultDTO getTaskInstanceResult(ProcessTaskInstanceSearchDTO taskInstanceSearchDTO) {
        return dolphinScheduler.taskInstanceList(taskInstanceSearchDTO);
    }

    private void getTaskInstanceList(ProcessTaskInstanceSearchDTO taskInstanceSearchDTO, List<TaskInstanceVO> totalList) {
        ProcessTaskInstanceResultDTO taskInstanceResultDTO = getTaskInstanceResult(taskInstanceSearchDTO);
        totalList.addAll(DqcProcessInstanceMapper.INSTANCE.taskInstanceVO(taskInstanceResultDTO.getTotalList()));
        if (taskInstanceResultDTO.getTotalPage() > taskInstanceResultDTO.getCurrentPage()) {
            taskInstanceSearchDTO.setPageNo(taskInstanceSearchDTO.getPageNo() + 1);
            getTaskInstanceList(taskInstanceSearchDTO, totalList);
        }
    }

    private List<TaskInstanceVO> getDolphinTaskInstanceList(Date date, String stateType) {
        ProcessTaskInstanceSearchDTO taskInstanceSearchDTO = getProcessTaskInstanceSearchDTO(date, stateType);
        List<TaskInstanceVO> totalList = new ArrayList<>();
        //查询所有的任务实例
        getTaskInstanceList(taskInstanceSearchDTO, totalList);
        return totalList;
    }

    /************************数据总揽*****************************/

    private Set<Long> getTaskCodeSet(List<TaskInstanceVO> totalList) {
        return totalList.stream().map(TaskInstanceVO::getTaskCode).collect(Collectors.toSet());
    }

    public JobInfoVO instanceStateStatistics(String stateType) {
        //调度查询当日所有任务实例
        List<TaskInstanceVO> totalList = getDolphinTaskInstanceList(null, stateType);
        Set<Long> taskCodeSet = getTaskCodeSet(totalList);
        return JobInfoVO.builder()
                //作业数
                .count(totalList.size())
                .tableCount(dqcSchedulerRulesService.getTableSet(taskCodeSet))
                .fieldCount(dqcSchedulerRulesService.getFieldSet(taskCodeSet))
                .build();
    }

    /************************任务纬度统计*****************************/

    private Map<Long, Long> getTaskCodeAndRuleTempId(List<TaskInstanceVO> totalList) {
        Set<Long> taskCodeSet = totalList.stream().map(TaskInstanceVO::getTaskCode).collect(Collectors.toSet());
        return dqcSchedulerRulesService.getSchedulerRulesListByTaskCode(taskCodeSet)
                .stream()
                .collect(Collectors.toMap(DqcSchedulerRules::getTaskCode, DqcSchedulerRules::getRuleTempId));
    }

    private Map<Long, String> getRuleTempIdAndDimensionType(Map<Long, Long> rulesMap) {
        Set<Long> ruleTempId = new HashSet<>(rulesMap.values());
        return dqcRuleTemplateService.getTemplateListByRuleTemId(ruleTempId)
                .stream()
                .collect(Collectors.toMap(DqcRuleTemplate::getId, DqcRuleTemplate::getDimensionType));
    }

    private Map<String, String> getTaskCodeAndDimensionType(Map<Long, Long> codeAndRulesMap, Map<Long, String> ruleAndDimensionTypeMap) {
        Map<String, String> rulesDimensionTypeMap = new HashMap<>(16);
        for (Map.Entry<Long, Long> codeAndRule : codeAndRulesMap.entrySet()) {
            String ruleTempId = ruleAndDimensionTypeMap.get(codeAndRule.getValue());
            rulesDimensionTypeMap.put(ruleTempId, rulesDimensionTypeMap.get(ruleTempId) != null
                    ? rulesDimensionTypeMap.get(ruleTempId) + "," + codeAndRule.getKey() : codeAndRule.getKey().toString());
        }
        return rulesDimensionTypeMap;
    }

    private Map<String, String> getTaskCodeAndDimensionType(List<TaskInstanceVO> totalList) {
        //获取所有taskCode->模版id
        Map<Long, Long> codeAndRulesMap = getTaskCodeAndRuleTempId(totalList);
        //获取所有的模版id->纬度
        Map<Long, String> ruleAndDimensionTypeMap = getRuleTempIdAndDimensionType(codeAndRulesMap);
        //更改taskCode->模版id->纬度 为 纬度 -> taskCodeList
        return getTaskCodeAndDimensionType(codeAndRulesMap, ruleAndDimensionTypeMap);
    }

    private Map<Long, Long> getTaskCodeCountMap(List<TaskInstanceVO> totalList, InstanceStateTypeEnum success) {
        return totalList.stream()
                .filter(item -> item.getState().equals(success.getCode()))
                .collect(Collectors.groupingBy(TaskInstanceVO::getTaskCode, Collectors.counting()));
    }

    private Map<String, Long> getDimensionTypeAndCount(Map<Long, Long> taskCodeCountMap, Map<String, String> dimensionTypeAndtaskCode) {
        return dimensionTypeAndtaskCode
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey, item -> {
                            AtomicReference<Long> number = new AtomicReference<>(0L);
                            Arrays.asList(item.getValue().split(",")).forEach(it -> {
                                Long taskCode = Long.valueOf(it);
                                if (taskCodeCountMap.get(taskCode) != null) {
                                    number.updateAndGet(v -> v + taskCodeCountMap.get(taskCode));
                                }
                            });
                            return number.get();
                        })
                );
    }

    private void packagingDimensionList(Map<String, Long> dimensionTypeAndCountByFailure, List<DimensionVO> dimensionList, InstanceStateTypeEnum failure) {
        DimensionVO dimensionVO;
        for (Map.Entry<String, Long> dimTypeMap : dimensionTypeAndCountByFailure.entrySet()) {
            if (dimTypeMap.getValue() > 0) {
                dimensionVO = new DimensionVO();
                dimensionVO.setDqcDimType(Objects.requireNonNull(DimensionTypeEnum.fromValue(dimTypeMap.getKey())).getName());
                dimensionVO.setExecCount(dimTypeMap.getValue());
                dimensionVO.setDqcState(failure.getValue());
                dimensionList.add(dimensionVO);
            }
        }
    }

    private List<DimensionVO> getDimensionList(Map<String, Long> dimensionTypeAndCountBySuccess, Map<String, Long> dimensionTypeAndCountByFailure) {
        List<DimensionVO> dimensionList = new ArrayList<>(16);
        packagingDimensionList(dimensionTypeAndCountBySuccess, dimensionList, InstanceStateTypeEnum.SUCCESS);
        packagingDimensionList(dimensionTypeAndCountByFailure, dimensionList, InstanceStateTypeEnum.FAILURE);
        return dimensionList;
    }

    public List<DimensionVO> dimensionStatistics(Date date) {
        //调度查询当日所有任务实例
        List<TaskInstanceVO> totalList = getDolphinTaskInstanceList(date, null);
        //success TaskCode->count
        Map<Long, Long> successTaskCodeCountMap = getTaskCodeCountMap(totalList, InstanceStateTypeEnum.SUCCESS);
        //failure TaskCode->count
        Map<Long, Long> failureTaskCodeCountMap = getTaskCodeCountMap(totalList, InstanceStateTypeEnum.FAILURE);
        //获取 dimensionType->taskCodeList
        Map<String, String> dimensionTypeAndtaskCode = getTaskCodeAndDimensionType(totalList);
        //dimensionTypeAndtaskCode 分化成功数量 dimensionType->successCount
        Map<String, Long> dimensionTypeAndCountBySuccess = getDimensionTypeAndCount(successTaskCodeCountMap, dimensionTypeAndtaskCode);
        //dimensionTypeAndtaskCode 分化失败数量 dimensionType->failureCount
        Map<String, Long> dimensionTypeAndCountByFailure = getDimensionTypeAndCount(failureTaskCodeCountMap, dimensionTypeAndtaskCode);
        //处理封装
        return getDimensionList(dimensionTypeAndCountBySuccess, dimensionTypeAndCountByFailure);
    }
}
