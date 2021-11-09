package com.qk.dm.dataquality.vo;

import lombok.Data;

@Data
public class DqcDispatchRuleListVo {

    private Long id;
    /**
     * 调度模版id
     */
    private Long dispatchTempId;

    /**
     * 模板名称
     */
    private String tempName;

    /**
     * 规则类型 1-库级 2-表级 3-字段级
     */
    private String ruleType;

    /**
     * 适用引擎 1-hive, 2-mysql, 适用多个以逗号分隔
     */
    private String suitEngine;

    /**
     * 数据库名称
     */
    private String databases;

    /**
     * 告警表达式
     */
    private String warnExpression;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 修改时间
     */
    private String gmtModified;

}
