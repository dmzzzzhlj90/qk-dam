package com.qk.dm.dataservice.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.util.Date;

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
    private String limitId;

    /**
     * API路由组id
     */
    @NotBlank(message = "API路由组id不能为空！")
    private String routeId;

    /**
     * API组路由名称
     */
    @NotBlank(message = "API组路由名称不能为空！")
    private String apiGroupRouteName;

    /**
     * API组路由路径
     */
    @NotBlank(message = "API组路由路径不能为空！")
    private String apiGroupRoutePath;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtModified;

    /**
     * 是否删除；0逻辑删除，1物理删除；
     */
    private Integer delFlag;

}
