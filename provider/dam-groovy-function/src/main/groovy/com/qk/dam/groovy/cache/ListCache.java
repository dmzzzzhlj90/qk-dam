package com.qk.dam.groovy.cache;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.qk.dam.groovy.cache.base.BaseCache;

import java.util.Collection;

public class ListCache<T,E extends Collection<T>> extends BaseCache<E> {
    private final LoadingCache<String, E> lsCache;
    public ListCache(){
        lsCache  = CacheBuilder.newBuilder()
                .build(
                        new CacheLoader<String, E>() {
                            public  E load(String key) { // no checked exception
                                return null;
                            }
                        });
    }
    @Override
    public  void set(String key, E collection){
        lsCache.put(key,collection);
    }
    @Override
    public  E get(String key){
        return lsCache.getIfPresent(key);
    }
}
