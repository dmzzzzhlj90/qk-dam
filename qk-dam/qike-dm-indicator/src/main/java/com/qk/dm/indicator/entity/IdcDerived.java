package com.qk.dm.indicator.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "qk_idc_derived")
public class IdcDerived implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 指标状态  0草稿 1已上线 2已下线
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 衍生指标名称
     */
    @Column(name = "derived_indicator_name", nullable = false)
    private String derivedIndicatorName;

    /**
     * 衍生指标编码
     */
    @Column(name = "derived_indicator_code", nullable = false)
    private String derivedIndicatorCode;

    /**
     * 数据表
     */
    @Column(name = "data_sheet", nullable = false)
    private String dataSheet;

    /**
     * 所属主题
     */
    @Column(name = "theme_code", nullable = false)
    private String themeCode;

    /**
     * 原子指标
     */
    @Column(name = "atom_indicator_code")
    private String atomIndicatorCode;

    /**
     * 时间限定
     */
    @Column(name = "time_limit")
    private String timeLimit;

    /**
     * 关联的字段
     */
    @Column(name = "associated_fields")
    private String associatedFields;

    /**
     * 通用限定
     */
    @Column(name = "general_limit")
    private String generalLimit;

    /**
     * 指标状态  0草稿 1已上线 2已下线
     */
    @Column(name = "indicator_status")
    private Integer indicatorStatus;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    @CreationTimestamp
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @Column(name = "gmt_modified")
    @UpdateTimestamp
    private Date gmtModified;

    /**
     * 删除标识 0未删除 1已删除
     */
    @Column(name = "del_flag")
    private Integer delFlag = 0;

    /**
     * sql语句
     */
    @Column(name = "sql_sentence")
    private String sqlSentence;

}
