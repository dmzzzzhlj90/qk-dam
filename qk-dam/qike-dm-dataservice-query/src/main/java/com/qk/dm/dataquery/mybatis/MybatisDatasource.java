package com.qk.dm.dataquery.mybatis;

import com.qk.dm.dataquery.datasouce.HikariDataSourceFactory;
import com.zaxxer.hikari.HikariConfig;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

/**
 * @author zhudaoming
 */
public class MybatisDatasource {

    private final Map<String,DataSource> datasource = new ConcurrentHashMap<>(256);


    public void regDatasource(String jdbcUrl,String driverClassName,String username,String password) {
        HikariConfig hc = new HikariConfig();
        hc.setJdbcUrl(jdbcUrl);
        hc.setDriverClassName(driverClassName);
        hc.setUsername(username);
        hc.setPassword(password);

        HikariDataSourceFactory hikariDataSourceFactory = HikariDataSourceFactory.builder()
                .config(hc)
                // hikariConfig 默认参数配置
                .dataSourceProperties(new Properties())
                .build();

        datasource.put("ss",hikariDataSourceFactory.dataSourceInstance());
    }

    public DataSource dataSource(String connectName) {
        return datasource.get(connectName);
    }

    public void bindDatasource(BiConsumer<String,DataSource> biConsumer){
        datasource.forEach(biConsumer);
    }

}
