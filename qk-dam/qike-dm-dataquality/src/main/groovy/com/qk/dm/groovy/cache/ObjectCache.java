package com.qk.dm.groovy.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author daomingzhu
 */
public class ObjectCache  {
    private static CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();
    private static final Cache<Object, Object> objCache;
    static {
        objCache  = cacheBuilder.build();
    }
    public void config(Consumer<CacheBuilder<Object, Object>> consumer){
        consumer.accept(cacheBuilder);
    }

    public static <E> E getIfNull(String key, Supplier<E> function) {
        E ef = (E) objCache.getIfPresent(key);
        if (Objects.isNull(ef)){
            E e = function.get();
            objCache.put(key,e);
            return e;
        }else{
            return ef;
        }
    }
}
