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
     * 通知状态
     */
    private Map<Integer, String> notifyStateEnum;

    /**
     * 提示级别
     */
    private Map<Integer, String> notifyLevelEnum;

    /**
     * 计算引擎类型
     */
    private Map<String, String> calculateEngineTypeEnum;

    /**
     * 规则类型
     */
    private Map<String, String> ruleTypeEnum;

    /**
     * 调度方式
     */
    private Map<String, String> schedulerTypeEnum;

}
