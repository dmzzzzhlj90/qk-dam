package com.qk.dm.dataquality.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author wjq
 * @date 2021/11/17
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchedulerRuleConstantsVO {

    /**
     * 提示级别
     */
    private Map<String, String> notifyLevelEnum;

    /**
     * 通知状态
     */
    private Map<String, String> notifyStateEnum;

    /**
     * 通知类型
     */
    private Map<String, String> notifyTypeEnum;

    /**
     * 计算引擎类型
     */
    private Map<String, String> engineTypeEnum;

    /**
     * 规则类型
     */
    private Map<String, String> ruleTypeEnum;

    /**
     * 调度方式
     */
    private Map<String, String> schedulerTypeEnum;

    /**
     * 调度状态
     */
    private Map<String, String> schedulerStateEnum;

    /**
     * 调度周期
     */
    private Map<String, String> SchedulerCycleEnum;
}
