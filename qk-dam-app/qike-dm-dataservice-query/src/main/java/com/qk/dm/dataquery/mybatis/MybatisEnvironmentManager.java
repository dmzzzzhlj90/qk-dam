package com.qk.dm.dataquery.mybatis;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

/**
 * @author zhudaoming
 */
public class MybatisEnvironmentManager {
    /**
     * 事务管理器
     */
    private final Map<String, JdbcTransactionFactory> jdbcTransactionFactoryMap = new ConcurrentHashMap<>(256);

    private final Map<String,Environment> envIdDatasource =  new ConcurrentHashMap<>(256);

    public void registerEnvironment(String connectName,Environment environment){
        envIdDatasource.put(connectName,environment);
    }
    public void registerJdbcTransactionFactory(String connectName,JdbcTransactionFactory jdbcTransactionFactory){
        jdbcTransactionFactoryMap.put(connectName,jdbcTransactionFactory);
    }
    public void bindEnvironment(BiConsumer<String, Environment> biConsumer){
        envIdDatasource.forEach(biConsumer);
    }



}
