package com.qk.dm.dataservice.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 数据服务_新建API_高级SQL_脚本方式
 */
@Data
@Entity
@Table(name = "qk_das_api_create_mybatis_sql_script")
public class DasApiCreateMybatisSqlScript implements Serializable {

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

    /**
     * 是否删除；0逻辑删除，1物理删除；
     */
    @Column(name = "del_flag", nullable = false)
    private Integer delFlag;

    /**
     * 是否开启缓存,0:不开启 1:开启一级缓存 2:开启二级缓存;
     */
    @Column(name = "cache_level", nullable = false)
    private Integer cacheLevel;

    /**
     * 是否开启分页功能
     */
    @Column(name = "page_flag", nullable = false)
    private Boolean pageFlag;

    /**
     * 每页显示数量
     */
    @Column(name = "page_size")
    private Integer pageSize;

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
     * 取数脚本
     */
    @Column(name = "sql_para")
    private String sqlPara;

}