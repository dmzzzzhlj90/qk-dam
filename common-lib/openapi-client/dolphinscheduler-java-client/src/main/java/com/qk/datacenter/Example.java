package com.qk.datacenter;

import com.qk.datacenter.client.ApiClient;
import com.qk.datacenter.client.ApiException;
import com.qk.datacenter.client.Configuration;
import com.qk.datacenter.api.DefaultApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setRequestInterceptor((r)->{
           r.header("token","2b29f18d15f3be6642814355f3dc9229");
        });
        DefaultApi apiInstance = new DefaultApi(defaultClient);
        Integer userId = 100; // Integer | 用户ID
        try {
            apiInstance.authorizeResourceTreeUsingGET(userId);
        } catch (ApiException e) {
            System.err.println("Exception when calling DefaultApi#authorizeResourceTreeUsingGET");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}