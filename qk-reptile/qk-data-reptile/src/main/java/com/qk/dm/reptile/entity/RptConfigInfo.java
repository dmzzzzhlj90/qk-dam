package com.qk.dm.reptile.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "qk_rpt_config_info")
public class RptConfigInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 基础信息表id
     */
    @Column(name = "base_info_id", nullable = false)
    private Long baseInfoId;

    /**
     * 父id
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * raw参数
     */
    @Column(name = "raw")
    private String raw;

    /**
     * x-www-form-urlencoded参数
     */
    @Column(name = "form_urlencoded")
    private String formUrlencoded;

    /**
     * form-data参数
     */
    @Column(name = "form_data")
    private String formData;

    /**
     * cookies参数
     */
    @Column(name = "cookies")
    private String cookies;

    /**
     * headers参数
     */
    @Column(name = "headers")
    private String headers;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 维度目录id
     */
    @Column(name = "dimension_id")
    private Long dimensionId;

    /**
     * 维度目录名称
     */
    @Column(name = "dimension_name")
    private String dimensionName;

    /**
     * 维度目录编码
     */
    @Column(name = "dimension_code")
    private String dimensionCode;

    /**
     * 是否启动动态加载js(0表示未启动、1表示启动)
     */
    @Column(name = "startover_js")
    private Integer startoverJs;

    /**
     * 是否启动代理IP(0表示未启动、1表示启动)
     */
    @Column(name = "startover_ip")
    private Integer startoverIp;

    /**
     * 请求路径
     */
    @Column(name = "request_url")
    private String requestUrl;

    /**
     * 请求方式
     */
    @Column(name = "request_type")
    private String requestType;

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
     * 修改时间
     */
    @Column(name = "gmt_modified")
    @UpdateTimestamp
    private Date gmtModified;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    @CreationTimestamp
    private Date gmtCreate;

}
