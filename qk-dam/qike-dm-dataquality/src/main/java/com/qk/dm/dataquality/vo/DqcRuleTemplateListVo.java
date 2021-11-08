package com.qk.dm.dataquality.vo;

import lombok.Data;

@Data
public class DqcRuleTemplateListVo {

    private Long id;

    /**
     * 模板名称
     */
    private String tempName;

    /**
     * 模板类型1-系统内置 2-自定义
     */

    private String tempType;

    /**
     * 质量维度
     */
    private String dimension;

    /**
     * 适用引擎 1-hive, 2-mysql, 适用多个以逗号分隔
     */
    private String suitEngine;

    /**
     * 结果定义
     */
    private String tempResult;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 修改时间
     */
    private String gmtModified;
}
