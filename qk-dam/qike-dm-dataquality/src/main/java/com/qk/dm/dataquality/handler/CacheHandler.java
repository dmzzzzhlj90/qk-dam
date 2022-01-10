package com.qk.dm.dataquality.handler;

import com.qk.dam.commons.util.GsonUtil;
import com.qk.dm.dataquality.constant.schedule.InstanceStateTypeEnum;
import com.qk.dm.dataquality.vo.DqcProcessInstanceVO;
import com.qk.dm.dataquality.vo.statistics.DataSummaryVO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author shenpj
 * @date 2021/12/29 12:22 下午
 * @since 1.0.0
 */
@Component
public class CacheHandler {
    private final TaskInstanceHandler taskInstanceHandler;
    private final RuleTemplateHandler ruleTemplateHandler;
    private final JobInfoHandler jobInfoHandler;
    private final InstanceHandler instanceHandler;
    private final WarnHandler warnHandler;

    public CacheHandler(TaskInstanceHandler taskInstanceHandler, RuleTemplateHandler ruleTemplateHandler, JobInfoHandler jobInfoHandler, InstanceHandler instanceHandler, WarnHandler warnHandler) {
        this.taskInstanceHandler = taskInstanceHandler;
        this.ruleTemplateHandler = ruleTemplateHandler;
        this.jobInfoHandler = jobInfoHandler;
        this.instanceHandler = instanceHandler;
        this.warnHandler = warnHandler;
    }

    private DataSummaryVO getDataSummary() {
        //实例状态统计
        return DataSummaryVO.builder()
                //规则模版统计数据
                .ruleTemplate(ruleTemplateHandler.ruleTemplateStatistics())
                //作业统计数据
                .jobInfo(jobInfoHandler.basicInfoStatistics())
                //实例统计数据
                .instance(instanceHandler.instanceStatistics())
                //实例状态统计
                .successs(taskInstanceHandler.taskStateTypeStatistics(InstanceStateTypeEnum.SUCCESS.getCode()))
                //实例状态统计
                .failure(taskInstanceHandler.taskStateTypeStatistics(InstanceStateTypeEnum.FAILURE.getCode()))
                //告警统计
                .warn(warnHandler.warnStatistics())
                .build();
    }

    @Cacheable(cacheManager = "dynamicTtlCacheManager", cacheNames = "statistics", key = "#root.methodName", unless = "#result == null")
    public String summary() {
        return GsonUtil.toJsonString(getDataSummary());
    }

    @Cacheable(cacheManager = "dynamicTtlCacheManager", cacheNames = "statistics", key = "#root.methodName", unless = "#result == '[]'")
    public String dimension() {
        return GsonUtil.toJsonString(taskInstanceHandler.dimensionStatistics(new Date()));
    }

    @Cacheable(cacheManager = "dynamicTtlCacheManager", cacheNames = "statistics", key = "#root.methodName", unless = "#result == '[]'")
    public String dir() {
        return GsonUtil.toJsonString(instanceHandler.dirStatistics(new Date()));
    }

    public List<DqcProcessInstanceVO> instanceList() {
        return instanceHandler.getDolphinInstanceList();
    }
}
