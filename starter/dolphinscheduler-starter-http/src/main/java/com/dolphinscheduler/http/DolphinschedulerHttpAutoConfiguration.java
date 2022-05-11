package com.dolphinscheduler.http;

import com.dolphinscheduler.DolphinschedulerProperties;
import com.dolphinscheduler.client.DolphinschedulerManager;
import com.dolphinscheduler.http.client.DolphinHttpClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhudaoming
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnDolphinschedulerHttpEnabled
@EnableConfigurationProperties(DolphinTaskDefinitionPropertiesBean.class)
public class DolphinschedulerHttpAutoConfiguration {
    @Bean
    public DolphinHttpClient dolphinHttpClient(final DolphinTaskDefinitionPropertiesBean dolphinTaskDefinitionPropertiesBean,
                                               final DolphinschedulerProperties dolphinschedulerProperties) {
        DolphinschedulerManager dolphinschedulerManager = dolphinschedulerManager(dolphinschedulerProperties);
        return new DolphinHttpClient(dolphinTaskDefinitionPropertiesBean, dolphinschedulerManager);
    }


    public DolphinschedulerManager dolphinschedulerManager(DolphinschedulerProperties dolphinschedulerProperties) {
        return new DolphinschedulerManager(dolphinschedulerProperties.getDefaultBaseUrl(),
                dolphinschedulerProperties.getTokenKey(),
                dolphinschedulerProperties.getTokenValue());
    }
}
