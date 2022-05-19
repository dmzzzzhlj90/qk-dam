package com.qk.dm.datacollect.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author shenpengjie
 */
@Component
@ConfigurationProperties("atlas.rest")
public class AtlasInfoConfig {
    private String address;
    private String basicAuth;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBasicAuth() {
        return basicAuth;
    }

    public void setBasicAuth(String basicAuth) {
        this.basicAuth = basicAuth;
    }
}
