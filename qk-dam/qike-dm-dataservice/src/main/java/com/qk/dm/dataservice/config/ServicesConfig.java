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
  public PluginManager pluginManager(final ApiGatewayManager apiGatewayManager) {
    return new PluginManager(apiGatewayManager);
  }
}
