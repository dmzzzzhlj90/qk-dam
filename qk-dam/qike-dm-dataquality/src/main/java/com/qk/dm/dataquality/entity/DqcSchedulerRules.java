package com.qk.dm.dataquality.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "qk_dqc_scheduler_rules")
public class DqcSchedulerRules implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 作业id
     */
    @Column(name = "job_id", nullable = false)
    private String jobId;

    /**
     * 规则模版id
     */
    @Column(name = "rule_temp_id", nullable = false)
    private Long ruleTempId;

    /**
     * 规则类型 "RULE_TYPE_FIELD", "字段级别规则","RULE_TYPE_TABLE", "表级别规则","RULE_TYPE_DB", "库级别规则";
     */
    @Column(name = "rule_type", nullable = false)
    private String ruleType;

    /**
     * 适用引擎 1-hive, 2-mysql, 适用多个以逗号分隔
     */
    @Column(name = "engine_type", nullable = false)
    private String engineType;

    /**
     * 数据连接地址
     */
    @Column(name = "database_url", nullable = false)
    private String databaseUrl;

    /**
     * 数据库名称
     */
    @Column(name = "database_name", nullable = false)
    private String databaseName;

    /**
     * 表名称
     */
    @Column(name = "table_name")
    private String tableName;

    /**
     * 字段名称
     */
    @Column(name = "field_name")
    private String fieldName;

    /**
     * 扫描范围 0-全表 1-条件
     */
    @Column(name = "scan_type", nullable = false)
    private Integer scanType;

    /**
     * 扫描条件SQL
     */
    @Column(name = "scan_sql")
    private String scanSql;

    /**
     * 告警表达式
     */
    @Column(name = "warn_expression", nullable = false)
    private String warnExpression;

    /**
     * 创建人
     */
    @Column(name = "create_userid", nullable = false)
    private Long createUserid;

    /**
     * 修改人
     */
    @Column(name = "update_userid")
    private Long updateUserid;

    /**
     * 删除标识(0-保留 1-删除)
     */
    @Column(name = "del_flag", nullable = false)
    private Integer delFlag;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create", nullable = false)
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @Column(name = "gmt_modified")
    private Date gmtModified;

}