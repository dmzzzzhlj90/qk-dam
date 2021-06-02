package com.qk.datasource.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

/**
 * @author daomingzhu
 * @date 2021/06/02
 * 多数据源配置类，支持cloud config 动态刷新
 */
@Configuration
public class DatasourceConfiguration {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource",ignoreInvalidFields=true)
    public DatasourceActive datasourceActive(){
        return new DatasourceActive();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.mysql",ignoreInvalidFields=true)
    public DataSource mysqlDataSource(){
        return new HikariDataSource();
    }
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.oracle",ignoreInvalidFields=true)
    public DataSource oracleSqlSource(){
        return new HikariDataSource();
    }
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.postgresql",ignoreInvalidFields=true)
    public DataSource postgreSqlSource(){
        return new HikariDataSource();
    }

}
