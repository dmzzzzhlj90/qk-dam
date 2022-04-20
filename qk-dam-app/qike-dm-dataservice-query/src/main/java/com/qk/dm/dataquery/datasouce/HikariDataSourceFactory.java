package com.qk.dm.dataquery.datasouce;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.util.DriverDataSource;
import lombok.Data;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author zhudaoming
 */
public class HikariDataSourceFactory{
    private final HikariConfig config;
    HikariDataSourceFactory(){
        throw new IllegalStateException("Utility class");
    }

    private HikariDataSourceFactory(final HikariConfig config){
        this.config=config;
    }

    public static HikariDataSourceFactory.Builder builder() {
        return new Builder();
    }

    public DataSource dataSourceInstance() {
        final HikariDataSource hikariDataSource = new HikariDataSource(config);
//        hikariDataSource.setDataSource(new DriverDataSource(
//                config.getJdbcUrl(),
//                config.getDriverClassName(),
//                config.getDataSourceProperties(),
//                config.getUsername(),
//                config.getPassword()
//
//        ));
        return hikariDataSource;
    }
    public HikariConfig getHikariConfig(){
        return config;
    }
    public static class Builder{
        private HikariConfig config;

        public Builder config(HikariConfig config) {
            this.config = config;
            return this;
        }
        public Builder dataSourceProperties(Properties dataSourceProperties) {
            this.config.setDataSourceProperties(dataSourceProperties);
            return this;
        }

        public HikariDataSourceFactory build(){
           return new HikariDataSourceFactory(config);
        }
    }
}
