package groovy.com.qk.dam.groovy.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.qk.dam.groovy.cache.base.BaseCache;

import java.util.Map;

public class MapCache<V,E extends Map<String,V>> extends BaseCache<E> {
    private final LoadingCache<String, E> mapCache;
    public MapCache(){
        mapCache  = CacheBuilder.newBuilder()
                .build(
                        new CacheLoader<String, E>() {
                            public  E load(String key) { // no checked exception
                                return null;
                            }
                        });
    }

    @Override
    public void set(String key, E e) {
        mapCache.put(key,e);
    }

    @Override
    public E get(String key) {
        return mapCache.getIfPresent(key);
    }
}
