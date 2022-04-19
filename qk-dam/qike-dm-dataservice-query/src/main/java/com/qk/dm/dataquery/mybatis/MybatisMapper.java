package com.qk.dm.dataquery.mybatis;

import com.google.common.base.Joiner;
import com.qk.dm.dataquery.domain.Mapper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhudaoming
 */
public class MybatisMapper {
    private final Map<String, Mapper> mapperMap = new ConcurrentHashMap<>(256);
    private static final String SEPARATOR = ":";

    public void addMapper(String namespace,String id, Mapper mapper){
        mapperMap.put(Joiner.on(SEPARATOR).join(namespace,id),mapper);
    }

    public Mapper getMapper(String namespace,String id){
       return mapperMap.get(Joiner.on(SEPARATOR).join(namespace,id));
    }
}
