package com.qk.dm.dataquality.dolphinapi.config;

import com.qk.datacenter.api.DefaultApi;
import com.qk.datacenter.client.ApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 调度引擎Dolphin Scheduler ApiClientConfig配置
 *
 * @author wjq
 * @date 2021/11/17
 * @since 1.0.0
 */
@Configuration
public class ApiClientConfig {

    @Autowired
    private DolphinClientConnectInfo dolphinClientConnectInfo;

    @Bean
    public DefaultApi defaultApi() {
        //创建工作流实例
        ApiClient defaultClient = com.qk.datacenter.client.Configuration.getDefaultApiClient();
        defaultClient.setDefaultBaseUri(dolphinClientConnectInfo.getDefaultBaseUrl());
        defaultClient.setRequestInterceptor((r) -> {
            r.header(dolphinClientConnectInfo.getTokenKey(), dolphinClientConnectInfo.getTokenValue());
        });
        DefaultApi apiInstance = new DefaultApi(defaultClient);
        return apiInstance;
    }
}
