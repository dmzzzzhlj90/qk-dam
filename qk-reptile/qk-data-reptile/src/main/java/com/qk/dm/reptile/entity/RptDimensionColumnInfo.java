package com.qk.dm.reptile.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "qk_rpt_dimension_column_info")
public class RptDimensionColumnInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 维度目录id
     */
    @Column(name = "dimension_id", nullable = false)
    private Long dimensionId;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 维度字段名称
     */
    @Column(name = "dimension_column_name", nullable = false)
    private String dimensionColumnName;

    /**
     * 修改时间
     */
    @Column(name = "gmt_modified")
    private Date gmtModified;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     * 创建人id
     */
    @Column(name = "create_userid")
    private Long createUserid;

    /**
     * 修改人id
     */
    @Column(name = "update_userid")
    private Long updateUserid;

    /**
     * 创建人姓名
     */
    @Column(name = "create_username")
    private String createUsername;

    /**
     * 修改人姓名
     */
    @Column(name = "update_username")
    private String updateUsername;

}
