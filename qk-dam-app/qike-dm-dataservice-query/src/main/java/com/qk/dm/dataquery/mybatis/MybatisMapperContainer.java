package com.qk.dm.dataquery.mybatis;

import com.qk.dm.dataquery.domain.Mapper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhudaoming
 */
public class MybatisMapperContainer {
    private final Map<String, Mapper> mapperMap = new ConcurrentHashMap<>(256);
    private static final String SEPARATOR = ":";


    public void addMapper(String namespace, Mapper mapper){
        mapperMap.put(namespace,mapper);
    }

    public Mapper getMapper(String namespace){
       return mapperMap.get(namespace);
    }

}
