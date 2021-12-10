package com.qk.dm.reptile.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "qk_rpt_base_info")
public class RptBaseInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 网站名
     */
    @Column(name = "website_name")
    private String websiteName;

    /**
     * 连接
     */
    @Column(name = "website_url")
    private String websiteUrl;

    /**
     * 执行周期
     */
    @Column(name = "run_period")
    private String runPeriod;

    /**
     * 数据状态（0表示待配、1表示爬虫、2表示历史）
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 数据启动状态（0表示启动、1表示未启动）
     */
    @Column(name = "run_status")
    private Integer runStatus;

    /**
     * 配置人id
     */
    @Column(name = "config_id")
    private Long configId;

    /**
     * 配置人名称
     */
    @Column(name = "config_name")
    private String configName;

    /**
     * 最后运行时间
     */
    @Column(name = "gmt_function")
    private Date gmtFunction;

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
