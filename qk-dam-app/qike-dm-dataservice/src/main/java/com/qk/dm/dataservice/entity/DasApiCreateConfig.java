package com.qk.dm.dataservice.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 数据服务_新建API_配置方式
 */
@Data
@Entity
@Table(name = "qk_das_api_create_config")
public class DasApiCreateConfig implements Serializable {

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
     * 取数据方式
     */
    @Column(name = "access_method", nullable = false)
    private String accessMethod;

    /**
     * 数据源连接类型
     */
    @Column(name = "connect_type", nullable = false)
    private String connectType;

    /**
     * 数据源标识code编码
     */
    @Column(name = "data_source_code", nullable = false)
    private String dataSourceCode;

    /**
     * 数据源连接名称
     */
    @Column(name = "data_source_name", nullable = false)
    private String dataSourceName;

    /**
     * 数据库
     */
    @Column(name = "data_base_name", nullable = false)
    private String dataBaseName;

    /**
     * 数据表
     */
    @Column(name = "table_name", nullable = false)
    private String tableName;

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
    private Integer delFlag;

    /**
     * 请求参数
     */
    @Column(name = "api_request_paras")
    private String apiRequestParas;

    /**
     * 响应参数
     */
    @Column(name = "api_response_paras")
    private String apiResponseParas;

    /**
     * 排序参数
     */
    @Column(name = "api_order_paras")
    private String apiOrderParas;

}