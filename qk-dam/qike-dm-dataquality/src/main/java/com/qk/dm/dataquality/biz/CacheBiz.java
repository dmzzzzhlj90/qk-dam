package com.qk.dm.dataquality.biz;

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
public class CacheBiz {
    private final TaskInstanceBiz taskInstanceBiz;
    private final RuleTemplateBiz ruleTemplateBiz;
    private final JobInfoBiz jobInfoBiz;
    private final InstanceBiz instanceBiz;
    private final WarnBiz warnBiz;

    public CacheBiz(TaskInstanceBiz taskInstanceBiz, RuleTemplateBiz ruleTemplateBiz, JobInfoBiz jobInfoBiz, InstanceBiz instanceBiz, WarnBiz warnBiz) {
        this.taskInstanceBiz = taskInstanceBiz;
        this.ruleTemplateBiz = ruleTemplateBiz;
        this.jobInfoBiz = jobInfoBiz;
        this.instanceBiz = instanceBiz;
        this.warnBiz = warnBiz;
    }

    private DataSummaryVO getDataSummary() {
        //实例状态统计
        return DataSummaryVO.builder()
                //规则模版统计数据
                .ruleTemplate(ruleTemplateBiz.ruleTemplateStatistics())
                //作业统计数据
                .jobInfo(jobInfoBiz.basicInfoStatistics())
                //实例统计数据
                .instance(instanceBiz.instanceStatistics())
                //实例状态统计
                .successs(taskInstanceBiz.taskStateTypeStatistics(InstanceStateTypeEnum.SUCCESS.getCode()))
                //实例状态统计
                .failure(taskInstanceBiz.taskStateTypeStatistics(InstanceStateTypeEnum.FAILURE.getCode()))
                //告警统计
                .warn(warnBiz.warnStatistics())
                .build();
    }

    @Cacheable(cacheManager = "dynamicTtlCacheManager", cacheNames = "statistics", key = "#root.methodName", unless = "#result == null")
    public String summary() {
        return GsonUtil.toJsonString(getDataSummary());
    }

    @Cacheable(cacheManager = "dynamicTtlCacheManager", cacheNames = "statistics", key = "#root.methodName", unless = "#result == '[]'")
    public String dimension() {
        return GsonUtil.toJsonString(taskInstanceBiz.dimensionStatistics(new Date()));
    }

    @Cacheable(cacheManager = "dynamicTtlCacheManager", cacheNames = "statistics", key = "#root.methodName", unless = "#result == '[]'")
    public String dir() {
        return GsonUtil.toJsonString(instanceBiz.dirStatistics(new Date()));
    }

    public List<DqcProcessInstanceVO> instanceList() {
        return instanceBiz.getDolphinInstanceList();
    }
}
