package com.qk.dm.dataservice.config;

import com.qk.dm.dataservice.manager.ApiGatewayManager;
import com.qk.dm.dataservice.manager.PluginManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicesConfig {
    @Bean
    public ApiGatewayManager connectorManager() {
        return new ApiGatewayManager();
    }
    @Bean
    public DataserviceInitServices dataserviceInitServices(final ApiGatewayManager apiGatewayManager){
        return new DataserviceInitServices(new PluginManager(apiGatewayManager));
    }
}
