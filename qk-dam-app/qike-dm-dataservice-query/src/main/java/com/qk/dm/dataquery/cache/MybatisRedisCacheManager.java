package com.qk.dm.dataquery.cache;

import org.apache.ibatis.cache.Cache;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.ReadWriteLock;

/**
 * @author zhudaoming
 */
@Component
public class MybatisRedisCacheManager implements Cache {

    @Override
    public String getId() {
        return null;
    }

    @Override
    public void putObject(Object key, Object value) {

    }

    @Override
    public Object getObject(Object key) {
        return null;
    }

    @Override
    public Object removeObject(Object key) {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return Cache.super.getReadWriteLock();
    }
}
