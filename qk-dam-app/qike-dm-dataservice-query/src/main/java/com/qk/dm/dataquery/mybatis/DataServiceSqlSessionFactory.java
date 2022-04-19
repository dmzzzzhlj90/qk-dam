package com.qk.dm.dataquery.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhudaoming
 */
public class DataServiceSqlSessionFactory {
    private final Map<String, SqlSessionFactory> sqlSessionFactoryMap = new ConcurrentHashMap<>(256);

    public void registerEnvironment(String connectName, SqlSessionFactory sqlSessionFactory){
        sqlSessionFactoryMap.put(connectName,sqlSessionFactory);
    }

    public SqlSessionFactory getSqlSessionFactory(String connectName){
       return sqlSessionFactoryMap.get(connectName);
    }
}
