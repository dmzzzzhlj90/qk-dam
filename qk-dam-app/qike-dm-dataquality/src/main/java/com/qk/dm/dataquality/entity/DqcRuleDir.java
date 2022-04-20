package com.qk.dm.dataquality.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "qk_dqc_rule_dir")
public class DqcRuleDir implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 规则分类目录ID
     */
    @Column(name = "rule_dir_id", nullable = false)
    private String ruleDirId;

    /**
     * 规则分类目录名称
     */
    @Column(name = "rule_dir_name", nullable = false)
    private String ruleDirName;

    /**
     * 父级id
     */
    @Column(name = "parent_id", nullable = false)
    private String parentId;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @Column(name = "gmt_modified")
    private Date gmtModified;

    /**
     * 是否删除；0逻辑删除，1物理删除；
     */
    @Column(name = "del_flag", nullable = false)
    private Integer delFlag;

}