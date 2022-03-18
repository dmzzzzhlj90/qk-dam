package com.qk.dm.dataservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据服务_流控绑定VO
 *
 * @author wjq
 * @date 2022/3/16
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DasApiLimitBindInfoVO {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 流控策略ID
     */
    private Long limitId;

    /**
     * API路由组id
     */
    private String routeId;

    /**
     * API组路由名称
     */
    private String apiGroupRouteName;

    /**
     * API组路由路径
     */
    private String apiGroupRoutePath;

    /**
     * 描述
     */
    private String description;


    /**
     * 是否删除；0逻辑删除，1物理删除；
     */
    private Integer delFlag;

}
