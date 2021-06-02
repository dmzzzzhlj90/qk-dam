package com.qk.dm.datastandards.cache;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

//@Service
//public class Example {
//    private final RedisTemplate redisTemplate;
//    public Example(RedisTemplate redisTemplate) {
//        this.redisTemplate = redisTemplate;
//    }
//
//    @PostConstruct
//    public void init(){
//        redisTemplate.opsForHash().putAll("apps:testapp", Map.of("names","zdm"));
//        Object o = redisTemplate.opsForHash().get("apps:testapp","names");
//        Map<Object, Object> entries = redisTemplate.opsForHash().entries("apps:testapp");
//        System.out.println(o);
//        System.out.println(entries);
//
//
//
//
//    }
//}