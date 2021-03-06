package com.qk.sankuai.config;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LeafDataSourceConf {
  @Bean
  @ConfigurationProperties(prefix = "spring.leaf.datasource", ignoreInvalidFields = true)
  public DataSource leafDataSource() {
    return new HikariDataSource();
  }

  @Bean
  @ConfigurationProperties(prefix = "spring.leaf", ignoreInvalidFields = true)
  public LeafConf leafConf() {
    return new LeafConf();
  }
}
