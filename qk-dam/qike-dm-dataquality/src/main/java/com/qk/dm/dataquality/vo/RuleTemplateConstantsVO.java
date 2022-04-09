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
public class RuleTemplateConstantsVO {

    /**
     * 计算引擎类型
     */
    private Map<String, String> engineTypeEnum;

    /**
     * 数据质量类别
     */
    private Map<String,String> dimensionTypeEnum;

    /**
     * 发布状态
     */
    private Map<String,String> publishStateEnum;

    /**
     * 模版类型
     */
    private Map<String,String> tempTypeEnum;

    /**
     * 规则类型
     */
    private Map<String,String> ruleTypeEnum;
}
