package com.qk.dam.datasource.config;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author daomingzhu
 * @date 2021/06/02 多数据源配置类，支持cloud config 动态刷新
 */
@Configuration
public class DatasourceConfiguration {
  @Bean
  @ConfigurationProperties(prefix = "spring.datasource", ignoreInvalidFields = true)
  public DatasourceActive datasourceActive() {
    return new DatasourceActive();
  }

  @Bean
  @ConditionalOnProperty(prefix = "spring.datasource", name = "active", havingValue = "mysql")
  @ConfigurationProperties(prefix = "spring.datasource.mysql", ignoreInvalidFields = true)
  public DataSource mysqlDataSource() {
    return new HikariDataSource();
  }

  @Bean
  @ConditionalOnProperty(prefix = "spring.datasource", name = "active", havingValue = "oracle")
  @ConfigurationProperties(prefix = "spring.datasource.oracle", ignoreInvalidFields = true)
  public DataSource oracleSqlSource() {
    return new HikariDataSource();
  }

  @Bean
  @ConditionalOnProperty(prefix = "spring.datasource", name = "active", havingValue = "postgre")
  @ConfigurationProperties(prefix = "spring.datasource.postgresql", ignoreInvalidFields = true)
  public DataSource postgreSqlSource() {
    return new HikariDataSource();
  }
}
