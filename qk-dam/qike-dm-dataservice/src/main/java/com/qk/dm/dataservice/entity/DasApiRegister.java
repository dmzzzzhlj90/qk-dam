package com.qk.dm.dataservice.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "qk_das_api_register")
public class DasApiRegister implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * API基础信息ID
     */
    @Column(name = "api_id", nullable = false)
    private String apiId;

    /**
     * 请求协议
     */
    @Column(name = "protocol_type", nullable = false)
    private String protocolType;

    /**
     * 请求方式
     */
    @Column(name = "request_type", nullable = false)
    private String requestType;

    /**
     * 后端服务 HOST
     */
    @Column(name = "backend_host", nullable = false)
    private String backendHost;

    /**
     * 后端服务 PATH
     */
    @Column(name = "backend_path", nullable = false)
    private String backendPath;

    /**
     * 后端超时 (ms)
     */
    @Column(name = "backend_timeout", nullable = false)
    private String backendTimeout;

    /**
     * 后端服务参数
     */
    @Column(name = "backend_request_paras", nullable = false)
    private String backendRequestParas;

    /**
     * 常量参数
     */
    @Column(name = "backend_constants")
    private String backendConstants;

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
    private Integer delFlag = 0;
}
