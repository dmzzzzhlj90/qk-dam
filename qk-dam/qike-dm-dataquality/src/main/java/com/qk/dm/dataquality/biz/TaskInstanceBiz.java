package com.qk.dm.dataquality.biz;

import cn.hutool.core.date.DateUtil;
import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dm.dataquality.constant.ruletemplate.DimensionTypeEnum;
import com.qk.dm.dataquality.constant.schedule.InstanceStateTypeEnum;
import com.qk.dm.dataquality.entity.DqcRuleTemplate;
import com.qk.dm.dataquality.entity.DqcSchedulerResult;
import com.qk.dm.dataquality.entity.DqcSchedulerRules;
import com.qk.dm.dataquality.service.DqcRuleTemplateService;
import com.qk.dm.dataquality.service.DqcSchedulerRulesService;
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
public class TaskInstanceBiz {
    private final DqcSchedulerRulesService dqcSchedulerRulesService;
    private final DqcRuleTemplateService dqcRuleTemplateService;
    private final RedisBiz redisBiz;
    private final WarnBiz warnBiz;

    public TaskInstanceBiz(DqcSchedulerRulesService dqcSchedulerRulesService,
                           DqcRuleTemplateService dqcRuleTemplateService,
                           RedisBiz redisBiz,
                           WarnBiz warnBiz) {
        this.dqcSchedulerRulesService = dqcSchedulerRulesService;
        this.dqcRuleTemplateService = dqcRuleTemplateService;
        this.redisBiz = redisBiz;
        this.warnBiz = warnBiz;
    }

    /**
     * 根据条件查询所有任务实例
     * @return
     */
    public List<TaskInstanceVO> getDolphinTaskInstanceList() {
        return GsonUtil.fromJsonString(redisBiz.redisTaskInstanceList(null, null), new TypeToken<List<TaskInstanceVO>>() {}.getType());
    }

    /************************数据总揽*****************************/

    private List<TaskInstanceVO> getTaskInstanceListByStateType(String stateType) {
        //在所有任务实例中筛选
        return getDolphinTaskInstanceList().stream().filter(it -> stateType.equals(it.getState())).collect(Collectors.toList());
    }

    public JobInfoVO taskStateTypeStatistics(String stateType) {
        //根据状态查询所有任务实例
        List<TaskInstanceVO> list = getTaskInstanceListByStateType(stateType);
        Set<Long> taskCodeSet = list.stream().map(TaskInstanceVO::getTaskCode).collect(Collectors.toSet());
        return JobInfoVO.builder()
                .count(list.size())
                .tableCount(dqcSchedulerRulesService.getTableSet(taskCodeSet))
                .fieldCount(dqcSchedulerRulesService.getFieldSet(taskCodeSet))
                .build();
    }

    /************************任务纬度统计*****************************/

    private List<TaskInstanceVO> taskInstanceListByDate(Date date){
        List<TaskInstanceVO> list = getDolphinTaskInstanceList();
        Date startTime = DateUtil.beginOfDay(date);
        Date endTime = DateUtil.endOfDay(date);
        return list.stream().filter(it -> it.getStartTime().compareTo(startTime) >= 0 && it.getEndTime().compareTo(endTime) <= 0).collect(Collectors.toList());
    }

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

    private Map<Long, Long> getTaskCodeCountMap(Date date){
        return warnBiz.warnResultList(date)
                .stream()
                .collect(Collectors.groupingBy(DqcSchedulerResult::getTaskCode, Collectors.counting()));
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

    public List<DimensionVO> dimensionStatistics(Date date) {
        //调度查询当日所有任务实例
        List<TaskInstanceVO> totalList = taskInstanceListByDate(date);
        //success TaskCode->count
        Map<Long, Long> successTaskCodeCountMap = getTaskCodeCountMap(totalList, InstanceStateTypeEnum.SUCCESS);
        //failure TaskCode->count
        Map<Long, Long> failureTaskCodeCountMap = getTaskCodeCountMap(totalList, InstanceStateTypeEnum.FAILURE);
        //warn TaskCode->count
        Map<Long, Long> warnTaskCodeCountMap = getTaskCodeCountMap(date);
        //获取 dimensionType->taskCodeList
        Map<String, String> dimensionTypeAndtaskCode = getTaskCodeAndDimensionType(totalList);
        //dimensionTypeAndtaskCode 分化成功数量 dimensionType->successCount
        Map<String, Long> dimensionTypeAndCountBySuccess = getDimensionTypeAndCount(successTaskCodeCountMap, dimensionTypeAndtaskCode);
        //dimensionTypeAndtaskCode 分化失败数量 dimensionType->failureCount
        Map<String, Long> dimensionTypeAndCountByFailure = getDimensionTypeAndCount(failureTaskCodeCountMap, dimensionTypeAndtaskCode);
        //dimensionTypeAndtaskCode 分化警告数量 dimensionType->WarnCount
        Map<String, Long> dimensionTypeAndCountByWarn = getDimensionTypeAndCount(warnTaskCodeCountMap, dimensionTypeAndtaskCode);
        //处理封装
        List<DimensionVO> dimensionList = new ArrayList<>(16);
        packagingDimensionList(dimensionTypeAndCountBySuccess, dimensionList, InstanceStateTypeEnum.SUCCESS);
        packagingDimensionList(dimensionTypeAndCountByFailure, dimensionList, InstanceStateTypeEnum.FAILURE);
        packagingDimensionList(dimensionTypeAndCountByWarn, dimensionList, InstanceStateTypeEnum.WARN);
        return dimensionList;
    }

}
