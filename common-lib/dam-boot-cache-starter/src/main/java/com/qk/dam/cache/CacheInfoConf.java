package com.qk.dam.cache;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author shenpj
 * @date 2022/1/18 7:24 下午
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = "spring.cache.conf", ignoreInvalidFields = true)
@Component(value = "cacheInfoConfig")
public class CacheInfoConf {
    /**
     * 缓存过期时间 秒
     */
    Integer duration;

    /**
     * 初始的缓存空间大小
     */
    Integer initialCapacity;

    /**
     * 最大缓存空间大小
     */
    Integer maximumSize;

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getInitialCapacity() {
        return initialCapacity;
    }

    public void setInitialCapacity(Integer initialCapacity) {
        this.initialCapacity = initialCapacity;
    }

    public Integer getMaximumSize() {
        return maximumSize;
    }

    public void setMaximumSize(Integer maximumSize) {
        this.maximumSize = maximumSize;
    }

    @Override
    public String toString() {
        return "CacheInfoConfVO{" +
                "duration=" + duration +
                ", initialCapacity=" + initialCapacity +
                ", maximumSize=" + maximumSize +
                '}';
    }
}
