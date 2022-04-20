package com.qk.dm.dataquery.mybatis;

import com.qk.dm.dataquery.domain.Mapper;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhudaoming
 */
public class MybatisMapperContainer {
    private final Map<String, Mapper> mapperMap = new ConcurrentHashMap<>(256);
    private final Map<String, String> apiIdDbNameMap = new ConcurrentHashMap<>(256);
    private static final String SEPARATOR = ":";


    public void addMapper(String namespace,Mapper mapper){
        mapperMap.put(namespace,mapper);
    }

    public void addApiIdDbNameMap( String apiId,String dbname){
        apiIdDbNameMap.put(apiId,dbname);
    }

    public String getDbName(String apiId){
        return apiIdDbNameMap.get(apiId);
    }

    public Mapper getMapper(String namespace){
       return mapperMap.get(namespace);
    }

    @Data
    @AllArgsConstructor
    private class MapperInner{
        private String namespace;
        private String dbname;
        private String apiId;
        private Mapper mapper;
    }
}
