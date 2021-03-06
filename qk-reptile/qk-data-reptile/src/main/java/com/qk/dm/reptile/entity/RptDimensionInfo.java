package com.qk.dm.reptile.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "qk_rpt_dimension_info")
public class RptDimensionInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 维度目录名称
     */
    @Column(name = "dimension_name", nullable = false)
    private String dimensionName;

    /**
     * 目录父类id
     */
    @Column(name = "fid")
    private Long fid;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 修改时间
     */
    @UpdateTimestamp
    @Column(name = "gmt_modified")
    private Date gmtModified;

    /**
     * 创建时间
     */
    @CreationTimestamp
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

    /**
     * 目录编码
     */
    @Column(name = "dimension_code")
    private String dimensionCode;

}
