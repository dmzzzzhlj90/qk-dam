package com.qk.dm.reptile.config;

import com.qk.dm.reptile.client.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakDataSourceConfig {

  @Bean
  @ConfigurationProperties(prefix = "keycloak-data-source", ignoreInvalidFields = true)
 public DataSource dataSource(){
     return new DataSource();
 }

}
