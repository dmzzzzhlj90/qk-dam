package com.qk.dm.dataservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "apisix", ignoreInvalidFields = true)
@Component(value = "apiSixConnectInfo")
public class ApiSixConnectInfo {
    public static final String GATEWAY_TYPE_API_SIX = "ApiSix";
    // API_SIX请求参数前缀_请求参数
    public static final String API_SIX_REQUEST_PARAMETER_ARG_PREFIX = "arg_";
    // API_SIX请求参数前缀_HTTP 请求头
    public static final String API_SIX_REQUEST_PARAMETER_HTTP_PREFIX = "http_";
    // API_SIX请求参数前缀_Cookie
    public static final String API_SIX_REQUEST_PARAMETER_COOKIE_PREFIX = "cookie_";
    // 注册API参数操作符号默认设置为:"=="
    public static final String API_REGISTER_PARAM_SYMBOL = "==";

    private String adminRouteUrl;

    private String adminConsumerUrl;

    private String adminUpstreamUrl;

    private String adminServerUrl;

    private String headKeyValue;

    private String apiStatus;

    private String apiVersion;

    private String upstreamPassHost;

    private String upstreamType;

    private String upstreamWeight;

    private Integer upstreamConnectTimeOut;

}
