package com.qk.dm.dataquality.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
     * 规则id
     */
    @Column(name = "rule_id", nullable = false)
    private String ruleId;

    /**
     * 规则名称
     */
    @Column(name = "rule_name", nullable = false)
    private String ruleName;

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
     * 规则类型 "RULE_TYPE_FIELD":"字段级别规则","RULE_TYPE_TABLE":"表级别规则","RULE_TYPE_DB":"库级别规则";
     */
    @Column(name = "rule_type", nullable = false)
    private String ruleType;

    /**
     * 适用引擎 MYSQL HIVE ORACLE
     */
    @Column(name = "engine_type", nullable = false)
    private String engineType;

    /**
     * 数据连接
     */
    @Column(name = "data_source_name", nullable = false)
    private String dataSourceName;

    /**
     * 数据库名称
     */
    @Column(name = "database_name", nullable = false)
    private String databaseName;

    /**
     * 表名称
     */
    @Column(name = "tables")
    private String tables;

    /**
     * 字段名称
     */
    @Column(name = "fields")
    private String fields;

    /**
     * 扫描范围 "FULL_TABLE":"全表", "CONDITION":"条件";
     */
    @Column(name = "scan_type", nullable = false)
    private String scanType;

    /**
     * 告警表达式
     */
    @Column(name = "warn_expression", nullable = false)
    private String warnExpression;

    /**
     * 创建人
     */
    @Column(name = "create_userid", nullable = false)
    private String createUserid;

    /**
     * 修改人
     */
    @Column(name = "update_userid")
    private String updateUserid;

    /**
     * 删除标识(0-保留 1-删除)
     */
    @Column(name = "del_flag", nullable = false)
    private Integer delFlag;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create", nullable = false)
    @CreationTimestamp
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @Column(name = "gmt_modified")
    @UpdateTimestamp
    private Date gmtModified;

    /**
     * 扫描条件SQL
     */
    @Column(name = "scan_sql")
    private String scanSql;

    /**
     * 执行sql
     */
    @Column(name = "execute_sql")
    private String executeSql;

}