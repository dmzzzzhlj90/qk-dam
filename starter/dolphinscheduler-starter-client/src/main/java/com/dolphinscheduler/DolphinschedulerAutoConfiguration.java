package com.dolphinscheduler;

import com.dolphinscheduler.client.DolphinschedulerManager;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhudaoming
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnDolphinschedulerEnabled
@EnableConfigurationProperties(DolphinschedulerProperties.class)
public class DolphinschedulerAutoConfiguration {
    @Bean
    public DolphinschedulerManager dolphinschedulerManager(final DolphinschedulerProperties dolphinschedulerProperties) {
        return new DolphinschedulerManager(dolphinschedulerProperties.getDefaultBaseUrl(),
                dolphinschedulerProperties.getTokenKey(),
                dolphinschedulerProperties.getTokenValue());
    }
}
