package com.qk.dm.dataservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * 数据服务_API应用管理VO
 *
 * @author wjq
 * @date 2022/3/21
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DasApiAppManageInfoVO {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 应用ID
     */
    private String appId;

    /**
     * 路由ID
     */
    private String routeId;

    /**
     * API组路由路径
     */
    private String apiGroupRoutePath;

    /**
     * API类型
     */
    private String apiType;

    /**
     * 上游UPSTREAM_ID
     */
    private String upstreamId;

    /**
     * 服务SERVICE_ID
     */
    private String serviceId;

    /**
     * 描述
     */
    private String description;

    /**
     * 修改人
     */
    private String updateUserid;

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
