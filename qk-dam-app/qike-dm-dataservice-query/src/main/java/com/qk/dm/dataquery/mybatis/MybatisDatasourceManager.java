package com.qk.dm.dataquery.mybatis;

import com.qk.dam.datasource.entity.ConnectBasicInfo;
import com.qk.dam.datasource.enums.ConnTypeEnum;
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
public class MybatisDatasourceManager {

    private final Map<String, DataSource> datasource = new ConcurrentHashMap<>(256);
    private final Map<String, HikariDataSourceFactory> hikariDataSourceFactoryMap = new ConcurrentHashMap<>(256);


    public void regDatasource(ConnTypeEnum connType, String dataSourceName, ConnectBasicInfo connectBasicInfo) {
        HikariConfig hc = new HikariConfig();
        hc.setJdbcUrl("jdbc:" + connType.getName() + "://"
                + connectBasicInfo.getServer() + ":"
                + connectBasicInfo.getPort()
//                + "/"
//                + "hd_court"
        );
        hc.setDriverClassName(connectBasicInfo.getDriverInfo());
        hc.setUsername(connectBasicInfo.getUserName());
        hc.setPassword(connectBasicInfo.getPassword());

        HikariDataSourceFactory hikariDataSourceFactory = HikariDataSourceFactory.builder().config(hc)
                // hikariConfig 默认参数配置
                .dataSourceProperties(new Properties()).build();

        hikariDataSourceFactoryMap.put(dataSourceName, hikariDataSourceFactory);
        datasource.put(dataSourceName, hikariDataSourceFactory.dataSourceInstance());
    }

    public void addDb(String connectName, String dbname) {
        HikariDataSourceFactory hikariDataSourceFactory = hikariDataSourceFactoryMap.get(connectName);
        HikariConfig hikariConfig = hikariDataSourceFactory.getHikariConfig();
        hikariConfig.setJdbcUrl(hikariConfig.getJdbcUrl() + "/" + dbname);
    }

    public DataSource dataSource(String connectName) {
        return datasource.get(connectName);
    }

    public void bindDatasource(BiConsumer<String, DataSource> biConsumer) {
        datasource.forEach(biConsumer);
    }

}
