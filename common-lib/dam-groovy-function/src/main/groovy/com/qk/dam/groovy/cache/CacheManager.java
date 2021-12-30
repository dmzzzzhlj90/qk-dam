package com.qk.dam.groovy.cache;

import com.google.common.collect.Maps;
import com.qk.dam.groovy.cache.base.BaseCache;

import java.lang.reflect.Constructor;
import java.util.Map;

/**
 * 缓存管理器-缓存策略
 *
 * @author wjq
 * @date 2021/12/23
 * @since 1.0.0
 */
public final class CacheManager<E> {
    private final static Map<String,Object> MAP_INSTANCE = Maps.newConcurrentMap();
    public static <T extends BaseCache> T configCacheType(Class<T> cls){
        try {
            Object rt = MAP_INSTANCE.get(cls.getName());
            if (MAP_INSTANCE.get(cls.getName())!=null){
                return (T) rt;
            }
            T t = new ReflectiveFactory<T>(cls).newInstance();
            MAP_INSTANCE.put(cls.getName(),t);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    static class ReflectiveFactory<T extends BaseCache> {

        private final Constructor<? extends T> constructor;

        public ReflectiveFactory(Class<? extends T> clazz) {
            try {
                this.constructor = clazz.getConstructor();
            } catch (NoSuchMethodException e) {
                throw new IllegalArgumentException("Class does not have a public non-arg constructor", e);
            }
        }

        T newInstance() throws Exception {
            try {
                return constructor.newInstance();
            } catch (Throwable t) {
                throw new Exception("Unable to create Channel from class " + constructor.getDeclaringClass(), t);
            }
        }

    }
}
