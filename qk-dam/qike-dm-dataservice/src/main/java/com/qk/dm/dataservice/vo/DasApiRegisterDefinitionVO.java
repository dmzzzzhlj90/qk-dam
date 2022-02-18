package com.qk.dm.dataservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private String protocolType;

    /**
     * 请求方式
     */
    private String requestType;

    /**
     * 后端服务 HOST
     */
    private String backendHost;

    /**
     * 后端服务 PATH
     */
    private String backendPath;

    /**
     * 后端超时 (ms)
     */
    private String backendTimeout;

    /**
     * 后端服务参数
     */
    private List<DasApiRegisterBackendParaVO> dasApiRegisterBackendParaVO;

    /**
     * 常量参数
     */
    private List<DasApiRegisterConstantParaVO> dasApiRegisterConstantParaVO;

    /**
     * 描述
     */
    private String description;
}
