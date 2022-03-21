package com.dolphinscheduler;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhudaoming
 */
@Component
@ConfigurationProperties("dolphinscheduler.client")
public class DolphinschedulerProperties {
    private String defaultBaseUrl;

    private Boolean enabled;

    private String tokenKey;

    private String tokenValue;

    public String getDefaultBaseUrl() {
        return defaultBaseUrl;
    }

    public void setDefaultBaseUrl(String defaultBaseUrl) {
        this.defaultBaseUrl = defaultBaseUrl;
    }

    public String getTokenKey() {
        return tokenKey;
    }

    public void setTokenKey(String tokenKey) {
        this.tokenKey = tokenKey;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
