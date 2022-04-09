package com.dolphinscheduler.client;

import com.qk.datacenter.api.DefaultApi;
import com.qk.datacenter.client.ApiClient;

/**
 * @author zhudaoming
 */
public class DolphinschedulerManager {
    private final String defaultBaseUrl;
    private final String tokenKey;
    private final String tokenValue;

    public DolphinschedulerManager(String defaultBaseUrl, String tokenKey, String tokenValue) {

        this.defaultBaseUrl = defaultBaseUrl;
        this.tokenKey = tokenKey;
        this.tokenValue = tokenValue;
    }


    public DefaultApi defaultApi() {
        //创建工作流实例
        ApiClient defaultClient = com.qk.datacenter.client.Configuration.getDefaultApiClient();
        defaultClient.setDefaultBaseUri(defaultBaseUrl);
        defaultClient.setRequestInterceptor(r -> r.header(tokenKey, tokenValue));
        return new DefaultApi(defaultClient);
    }
}
