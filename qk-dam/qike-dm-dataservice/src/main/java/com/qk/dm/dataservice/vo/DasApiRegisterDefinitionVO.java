package com.qk.dm.dataservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 注册API_接口定义_对应实体对象VO
 *
 * @author wjq
 * @date 20210907
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DasApiRegisterDefinitionVO {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * API基础信息ID
     */
    private String apiId;

    /**
     * API路由RouteId
     */
    private String apiRouteId;

    /**
     * 请求协议
     */
    @NotBlank(message = "请求协议不能为空！")
    private String protocolType;

    /**
     * 请求方式
     */
    @NotBlank(message = "请求方式不能为空！")
    private String requestType;

    /**
     * 后端服务 HOST
     */
    @NotBlank(message = "后端服务 HOST不能为空！")
    private String backendHost;

    /**
     * 后端服务 PATH
     */
    @NotBlank(message = "后端服务 PATH不能为空！")
    private String backendPath;

    /**
     * 后端超时 (ms)
     */
    @NotBlank(message = "后端超时 (ms)不能为空！")
    private String backendTimeout;

    /**
     * 后端服务参数
     */
    private List<DasApiRegisterBackendParaVO> apiRegisterBackendParaVOS;

    /**
     * 常量参数
     */
    private List<DasApiRegisterConstantParaVO> apiRegisterConstantParaVOS;

    /**
     * 描述
     */
    private String description;
}
