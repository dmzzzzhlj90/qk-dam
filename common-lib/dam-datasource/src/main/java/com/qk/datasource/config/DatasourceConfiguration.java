package com.qk.datasource.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfiguration {

    @Bean
    @Primary
    public DataSource mysqlDataSource(){
        return new HikariDataSource();
    }
    @Bean
    public DataSource postgreSqlSource(){
        return new HikariDataSource();
    }
}
