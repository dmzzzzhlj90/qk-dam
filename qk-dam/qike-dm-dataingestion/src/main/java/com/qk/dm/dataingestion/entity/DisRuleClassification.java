package com.qk.dm.dataingestion.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 数据引入规则分类表
 */
@Data
@Entity
@Table(name = "qk_dis_rule_classification")
public class DisRuleClassification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 规则分类id
     */
    @Column(name = "dir_id", nullable = false)
    private String dirId;

    /**
     * 规则分类名称
     */
    @Column(name = "dir_name", nullable = false)
    private String dirName;

    @Column(name = "parent_id", nullable = false)
    private String parentId;

    @Column(name = "description")
    private String description;

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
    @Column(name = "del_flag")
    private Integer delFlag = 0;

}
