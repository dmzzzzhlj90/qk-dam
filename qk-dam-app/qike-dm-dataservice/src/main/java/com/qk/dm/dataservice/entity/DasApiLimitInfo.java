package com.qk.dm.dataservice.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 数据服务_服务流控管理
 */
@Data
@Entity
@Table(name = "qk_das_api_limit_info")
public class DasApiLimitInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 策略名称
     */
    @Column(name = "limit_name", nullable = false)
    private String limitName;

    /**
     * 时长
     */
    @Column(name = "limit_time", nullable = false)
    private Integer limitTime;

    /**
     * 时长单位,秒,分钟,小时,天;
     */
    @Column(name = "limit_time_unit", nullable = false)
    private String limitTimeUnit;

    /**
     * API流量限制(次)
     */
    @Column(name = "api_limit_count", nullable = false)
    private Integer apiLimitCount;

    /**
     * 用户流量限制(次)
     */
    @Column(name = "user_limit_count", nullable = false)
    private Integer userLimitCount;

    /**
     * 应用流量限制(次)
     */
    @Column(name = "app_limit_count", nullable = false)
    private Integer appLimitCount;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 创建人
     */
    @Column(name = "create_userid", nullable = false)
    private String createUserid;

    /**
     * 修改人
     */
    @Column(name = "update_userid")
    private String updateUserid;

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