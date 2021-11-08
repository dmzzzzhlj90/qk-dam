package com.qk.dm.dataquality.vo;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
public class DqcDispatchRuleVo {

    /**
     * 调度模版id
     */
    @NotNull(message = "调度模版id不能为空！")
    private Long dispatchTempId;

    /**
     * 规则模版id
     */
    @NotNull(message = "规则模版id不能为空！")
    private Long ruleTempId;

    /**
     * 规则类型 1-库级 2-表级 3-字段级
     */
    @NotNull(message = "规则类型不能为空！")
    private Integer ruleType;

    /**
     * 适用引擎 1-hive, 2-mysql, 适用多个以逗号分隔
     */
    @NotBlank(message = "模板名称不能为空！")
    private String suitEngine;

    /**
     * 数据连接地址
     */
    @NotBlank(message = "数据连接地址不能为空！")
    private String databaseUrl;

    /**
     * 数据库名称
     */
    @NotBlank(message = "数据库名称不能为空！")
    private String databases;

    /**
     * 表名称
     */
    private String tables;

    /**
     * 字段名称
     */
    private String fields;

    /**
     * 扫描范围 0-全表 1-条件
     */
    @NotNull(message = "扫描范围不能为空！")
    private Integer scanType;

    /**
     * 扫描条件SQL
     */
    private String scanSql;

    /**
     * 告警表达式
     */
    @NotBlank(message = "告警表达式不能为空！")
    private String warnExpression;

}
