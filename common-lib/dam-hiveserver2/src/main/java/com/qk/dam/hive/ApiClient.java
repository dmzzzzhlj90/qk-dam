package com.qk.dam.hive;

import java.net.http.HttpClient;

/**
 * @author zhudaoming
 */
public class ApiClient {
    private HttpClient.Builder builder;
    public ApiClient() {
        this.builder = createDefaultHttpClientBuilder();
    }

    protected HttpClient.Builder createDefaultHttpClientBuilder() {
        return HttpClient.newBuilder();
    }
    public HttpClient getHttpClient() {
        return builder.build();
    }


}
