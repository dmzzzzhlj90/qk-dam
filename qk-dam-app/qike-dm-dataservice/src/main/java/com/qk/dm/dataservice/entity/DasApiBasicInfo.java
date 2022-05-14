package com.qk.dm.dataservice.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 数据服务_API基础信息
 */
@Data
@Entity
@Table(name = "qk_das_api_basic_info")
public class DasApiBasicInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * API_ID
     */
    @Column(name = "api_id", nullable = false)
    private String apiId;

    /**
     * 分类目录ID
     */
    @Column(name = "dir_id", nullable = false)
    private String dirId;

    /**
     * 分类目录
     */
    @Column(name = "dir_name", nullable = false)
    private String dirName;

    /**
     * API名称
     */
    @Column(name = "api_name", nullable = false)
    private String apiName;

    /**
     * 请求PATH
     */
    @Column(name = "api_path", nullable = false)
    private String apiPath;

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
     * API类型
     */
    @Column(name = "api_type", nullable = false)
    private String apiType;

    /**
     * 结果集数据格式类型: 0单条,1列表
     */
    @Column(name = "result_data_type", nullable = false)
    private Integer resultDataType;

    /**
     * 同步状态; 0: 新建未同步; 1: 同步失败; 2: 已同步数据服务网关;
     */
    @Column(name = "status", nullable = false)
    private String status;

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

    /**
     * 入参定义
     */
    @Column(name = "def_input_param")
    private String defInputParam;

}