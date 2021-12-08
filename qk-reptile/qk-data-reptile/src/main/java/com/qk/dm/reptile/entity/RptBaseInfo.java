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
     * from-data参数
     */
    @Column(name = "from_data")
    private String fromData;

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
     * 执行周期
     */
    @Column(name = "run_period")
    private String runPeriod;

    /**
     * 数据状态（0表示待配、1表示爬虫、2表示历史）
     */
    @Column(name = "status")
    private Integer status = 0;

    /**
     * 数据启动状态（0表示启动、1表示未启动）
     */
    @Column(name = "run_status")
    private Integer runStatus;

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
     * 配置人名称
     */
    @Column(name = "config_name")
    private String configName;

    /**
     * 配置人id
     */
    @Column(name = "config_id")
    private Long configId;

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
     * 连接
     */
    @Column(name = "conn")
    private String conn;

    /**
     * 网站名
     */
    @Column(name = "cn_name")
    private String cnName;

    /**
     * 最后运行时间
     */
    @Column(name = "gmt_function")
    private Date gmtFunction;

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

}
