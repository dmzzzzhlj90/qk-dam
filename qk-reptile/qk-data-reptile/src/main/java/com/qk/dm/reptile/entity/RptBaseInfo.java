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
    private Integer runStatus = 1;

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
    /**
     * 爬虫接口返回，用于查看日志
     */
    @Column(name = "job_id")
    private String jobId;
    /**
     * 分配日期
     */
    @Column(name = "distribution_date")
    private Date distributionDate;
    /**
     * 交付日期
     */
    @Column(name = "delivery_date")
    private Date deliveryDate;
    /**
     * 二期站点类型
     */
    @Column(name = "second_site_type")
    private String secondSiteType;
    /**
     * 列表页地址
     */
    @Column(name = "list_page_address")
    private String listPageAddress;
    /**
     * 不同类型混合
     */
    @Column(name = "different_type_mixed")
    private String differentTypeMixed;
    /**
     * 信息发布级别
     */
    @Column(name = "info_release_level")
    private String infoReleaseLevel;
    /**
     * 省编码
     */
    @Column(name = "province_code")
    private String provinceCode;
    /**
     * 市编码
     */
    @Column(name = "city_code")
    private String cityCode;
    /**
     * 区编码
     */
    @Column(name = "area_code")
    private String areaCode;

    /**
     * 站点官网（修正）
     */
    @Column(name = "website_name_correction")
    private String websiteNameCorrection;

    /**
     * 站点官网（修正）
     */
    @Column(name = "website_url_correction")
    private String websiteUrlCorrection;

    /**
     * 区域编码
     */
    @Column(name = "region_code")
    private String regionCode;

    /**
     * 执行人
     */
    @Column(name = "executor")
    private String executor;

    /**
     * 执行人id
     */
    @Column(name = "executor_id")
    private Long executorId;

}
