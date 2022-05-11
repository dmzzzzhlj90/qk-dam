package com.dolphinscheduler.shell;

import com.dolphinscheduler.DolphinschedulerProperties;
import com.dolphinscheduler.client.DolphinschedulerManager;
import com.dolphinscheduler.shell.client.DolphinShellClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhudaoming
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnDolphinschedulerShellEnabled
@EnableConfigurationProperties(DolphinTaskDefinitionPropertiesBean.class)
public class DolphinschedulerShellAutoConfiguration {
    @Bean
    public DolphinShellClient dolphinHttpClient(final DolphinTaskDefinitionPropertiesBean dolphinTaskDefinitionPropertiesBean,
                                                final DolphinschedulerProperties dolphinschedulerProperties) {
        DolphinschedulerManager dolphinschedulerManager = dolphinschedulerManager(dolphinschedulerProperties);
        return new DolphinShellClient(dolphinTaskDefinitionPropertiesBean, dolphinschedulerManager);
    }


    public DolphinschedulerManager dolphinschedulerManager(DolphinschedulerProperties dolphinschedulerProperties) {
        return new DolphinschedulerManager(dolphinschedulerProperties.getDefaultBaseUrl(),
                dolphinschedulerProperties.getTokenKey(),
                dolphinschedulerProperties.getTokenValue());
    }
}
