package com.qk.dm.datamodel.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "qk_model_summary_idc")
@Where(clause = "del_flag = 0 ")
public class ModelSummaryIdc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 汇总表id
     */
    @Column(name = "summary_id", nullable = false)
    private Long summaryId;

    /**
     * 指标类型
     */
    @Column(name = "indicators_type", nullable = false)
    private Integer indicatorsType;

    /**
     * 指标名称
     */
    @Column(name = "indicators_name", nullable = false)
    private String indicatorsName;

    /**
     * 指标编码
     */
    @Column(name = "indicators_code", nullable = false)
    private String indicatorsCode;

    /**
     * 数据类型
     */
    @Column(name = "data_type", nullable = false)
    private Integer dataType;

    /**
     * 是否为空 0为空 1不为空
     */
    @Column(name = "its_null", nullable = false)
    private String itsNull;

    /**
     * 描述
     */
    @Column(name = "desc")
    private String desc;

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
     * 0未删除 1已删除
     */
    @Column(name = "del_flag", nullable = false)
    private Integer delFlag;

}
