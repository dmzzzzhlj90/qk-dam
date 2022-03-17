package com.qk.dm.dataservice.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 数据服务_流控绑定
 */
@Data
@Entity
@Table(name = "qk_das_api_limit_bind_info")
public class DasApiLimitBindInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 流控策略ID
     */
    @Column(name = "limit_id", nullable = false)
    private String limitId;

    /**
     * API路由组id
     */
    @Column(name = "route_id", nullable = false)
    private String routeId;

    /**
     * API组路由名称
     */
    @Column(name = "api_group_route_name", nullable = false)
    private String apiGroupRouteName;

    /**
     * API组路由路径
     */
    @Column(name = "api_group_route_path", nullable = false)
    private String apiGroupRoutePath;

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