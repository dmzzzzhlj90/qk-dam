package com.qk.dm.datasource.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "qk_ds_datasource")
public class DsDatasource implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 数据源标识code编码
     */
    @Column(name = "data_source_code", nullable = false)
    private String dataSourceCode;

    /**
     * 连接名称
     */
    @Column(name = "data_source_name", nullable = false)
    private String dataSourceName;

    /**
     * 所属系统
     */
    @Column(name = "home_system")
    private String homeSystem;

    /**
     * 连接方式
     */
    @Column(name = "link_type")
    private String linkType;

    /**
     * 标签（名称用逗号隔开）
     */
    @Column(name = "tag_names")
    private String tagNames;

    /**
     * 标签id组合（ID用逗号隔开）
     */
    @Column(name = "tag_ids")
    private String tagIds;

    /**
     * 用途
     */
    @Column(name = "purpose")
    private String purpose;

    /**
     * 部署地
     */
    @Column(name = "deploy_place")
    private String deployPlace;

    /**
     * 状态设置状态值
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 创建时间（后期设置为必填值）
     */
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @Column(name = "gmt_modified")
    private Date gmtModified;

    /**
     * 创建人id（后期设置为必填）
     */
    @Column(name = "create_userid")
    private String createUserid;

    /**
     * 修改人id
     */
    @Column(name = "update_userid")
    private String updateUserid;

    /**
     * 删除标识(0-保留 1-删除，后期设置为非空，默认为0)
     */
    @Column(name = "del_flag")
    private Integer delFlag;

    /**
     * 多租户标识
     */
    @Column(name = "tenant_identification")
    private String tenantIdentification;

    /**
     * 目录归属id
     */
    @Column(name = "dic_id", nullable = false)
    private String dicId;

    /**
     * 数据 源连接值
     */
    @Column(name = "data_source_values")
    private String dataSourceValues;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

}