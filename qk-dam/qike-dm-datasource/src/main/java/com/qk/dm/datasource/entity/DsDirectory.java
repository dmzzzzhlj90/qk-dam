package com.qk.dm.datasource.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "qk_ds_directory")
public class DsDirectory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 应用系统名称
     */
    @Column(name = "sys_name", nullable = false)
    private String sysName;

    /**
     * 应用系统名称简称
     */
    @Column(name = "sys_short_name", nullable = false)
    private String sysShortName;

    /**
     * 区域
     */
    @Column(name = "area")
    private String area;

    /**
     * IT部门
     */
    @Column(name = "it_department")
    private String itDepartment;

    /**
     * 业务部门
     */
    @Column(name = "busi_department")
    private String busiDepartment;

    /**
     * 重要性(1-高 2-中 3-低)
     */
    @Column(name = "importance")
    private Integer importance;

    /**
     * 负责人
     */
    @Column(name = "leader")
    private String leader;

    /**
     * 部署地
     */
    @Column(name = "deploy_place")
    private String deployPlace;

    /**
     * 创建时间（后期设置为非空）
     */
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @Column(name = "gmt_modified")
    private Date gmtModified;

    /**
     * 创建人id（后期设置为非空）
     */
    @Column(name = "create_userid")
    private Integer createUserid;

    /**
     * 修改人
     */
    @Column(name = "update_userid")
    private Integer updateUserid;

    /**
     * 删除标识(0-保留 1-删除，后期设置为非空默认为0)
     */
    @Column(name = "del_flag")
    private Integer delFlag;

    /**
     * 多租户标识
     */
    @Column(name = "version_consumer")
    private String versionConsumer;

    /**
     * 描述
     */
    @Column(name = "sys_desc")
    private String sysDesc;

    /**
     * 标签名称组合(ETL,PII)名称用逗号隔开
     */
    @Column(name = "tag_names")
    private String tagNames;

    /**
     * 标签ID组合(1,2,3)ID逗号隔开
     */
    @Column(name = "tag_ids")
    private String tagIds;

}
