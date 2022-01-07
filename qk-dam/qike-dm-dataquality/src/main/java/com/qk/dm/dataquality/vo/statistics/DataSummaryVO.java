package com.qk.dm.dataquality.vo.statistics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shenpj
 * @date 2021/12/23 11:45 上午
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataSummaryVO {
    /**
     * 规则模板数
     */
    RuleTemplateVO ruleTemplate;
    /**
     * 作业数
     */
    JobInfoVO jobInfo;
    /**
     * 实例数
     */
    InstanceVO instance;
    /**
     * 成功数
     */
    JobInfoVO successs;
    /**
     * 失败数
     */
    JobInfoVO failure;
    /**
     * 警告数
     */
    JobInfoVO warn;
}
