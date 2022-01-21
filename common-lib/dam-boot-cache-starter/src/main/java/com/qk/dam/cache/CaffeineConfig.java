package com.qk.dam.cache;

import org.springframework.context.annotation.Configuration;

/**
 * @author shenpengjie
 */
@Configuration
public class CaffeineConfig {
    /**
     * maximumSize：设置缓存最大条目数，超过条目则触发回收
     * maximumWeight：设置缓存最大权重，设置权重是通过weigher方法， 需要注意的是权重也是限制缓存大小的参数，并不会影响缓存淘汰策略，也不能和maximumSize方法一起使用。
     * weakKeys：将key设置为弱引用，在GC时可以直接淘汰
     * weakValues：将value设置为弱引用，在GC时可以直接淘汰
     * softValues：将value设置为软引用，在内存溢出前可以直接淘汰
     * expireAfterWrite：写入后隔段时间过期
     * expireAfterAccess：访问后隔断时间过期
     * refreshAfterWrite：写入后隔断时间刷新
     * removalListener：缓存淘汰监听器，配置监听器后，每个条目淘汰时都会调用该监听器
     * writer：writer监听器其实提供了两个监听，一个是缓存写入或更新是的write，一个是缓存淘汰时的delete，每个条目淘汰时都会调用该监听器
     * @return
     */
    private final CacheInfoConf cacheInfoConf;

    public CaffeineConfig(CacheInfoConf cacheInfoConf) {
        this.cacheInfoConf = cacheInfoConf;
    }


}