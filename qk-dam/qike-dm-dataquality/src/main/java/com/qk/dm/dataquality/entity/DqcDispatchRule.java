package com.qk.dm.dataquality.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "qk_dqc_dispatch_rule")
public class DqcDispatchRule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 调度模版id
     */
    @Column(name = "dispatch_temp_id", nullable = false)
    private Long dispatchTempId;

    /**
     * 规则模版id
     */
    @Column(name = "rule_temp_id", nullable = false)
    private Long ruleTempId;

    /**
     * 规则类型 1-库级 2-表级 3-字段级
     */
    @Column(name = "rule_type", nullable = false)
    private Integer ruleType;

    /**
     * 适用引擎 1-hive, 2-mysql, 适用多个以逗号分隔
     */
    @Column(name = "suit_engine", nullable = false)
    private String suitEngine;

    /**
     * 数据连接地址
     */
    @Column(name = "database_url", nullable = false)
    private String databaseUrl;

    /**
     * 数据库名称
     */
    @Column(name = "databases", nullable = false)
    private String databases;

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
    @CreationTimestamp
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @Column(name = "gmt_modified")
    @UpdateTimestamp
    private Date gmtModified;

}
