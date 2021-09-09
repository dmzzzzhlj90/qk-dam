package com.qk.dm.dataservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DasApiRouteVO  {

    /**
     * 主键ID
     */
    @NotBlank(message = "API目录ID不能为空！")
    private Long id;

    /**
     * API路由匹配规则ID
     */
//    @NotBlank(message = "API路由匹配规则ID！")
    private String apiRouteId;

    /**
     * API路由匹配路径
     */
    @NotBlank(message = "API路由匹配路径！")
    private String apiRoutePath;

    /**
     * 描述
     */
    private String description;

    /**
     * 修改时间
     */
    private Date gmtModified;


}
