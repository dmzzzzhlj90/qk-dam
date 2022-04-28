package com.dolphinscheduler.http;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhudaoming
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnDolphinschedulerHttpEnabled
@EnableConfigurationProperties(DolphinschedulerHttpProperties.class)
public class DolphinschedulerHttpAutoConfiguration {
    private final DolphinschedulerHttpProperties dolphinHttpProperties;

    public DolphinschedulerHttpAutoConfiguration(DolphinschedulerHttpProperties dolphinHttpProperties) {
        this.dolphinHttpProperties = dolphinHttpProperties;
    }
}
