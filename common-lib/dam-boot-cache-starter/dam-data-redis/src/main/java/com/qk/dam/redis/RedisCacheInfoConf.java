package com.qk.dam.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author shenpj
 * @date 2022/1/18 7:24 下午
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = "spring.cache.conf", ignoreInvalidFields = true)
@Component
public class RedisCacheInfoConf {
    Integer duration;

    Integer initialCapacity;

    Integer maximumSize;

    Integer redisMinSecond;

    Integer redisMaxSecond;

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

    public Integer getRedisMinSecond() {
        return redisMinSecond;
    }

    public void setRedisMinSecond(Integer redisMinSecond) {
        this.redisMinSecond = redisMinSecond;
    }

    public Integer getRedisMaxSecond() {
        return redisMaxSecond;
    }

    public void setRedisMaxSecond(Integer redisMaxSecond) {
        this.redisMaxSecond = redisMaxSecond;
    }

    @Override
    public String toString() {
        return "CacheInfoConfVO{" +
                "duration=" + duration +
                ", initialCapacity=" + initialCapacity +
                ", maximumSize=" + maximumSize +
                ", redisMinSecond=" + redisMinSecond +
                ", redisMaxSecond=" + redisMaxSecond +
                '}';
    }
}
