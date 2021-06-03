package com.qk.dm.datastandards.cache;

import com.qk.sankuai.inf.IDGen;
import com.qk.sankuai.inf.leaf.common.Result;
import com.qk.sankuai.inf.leaf.segment.SegmentIDGenImpl;
import com.qk.sankuai.service.SegmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
public class Example {
    private final RedisTemplate redisTemplate;

    public Example(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    SegmentService segmentService;

    @PostConstruct
    public void init() {
//        缓存的例子
//        redisTemplate.opsForHash().putAll("apps:testapp", Map.of("names","zdm"));
//        Object o = redisTemplate.opsForHash().get("apps:testapp","names");
//        Map<Object, Object> entries = redisTemplate.opsForHash().entries("apps:testapp");
//        System.out.println(o);
//        System.out.println(entries);
//        分布式id  号段模式
//        IDGen idGen = segmentService.getIdGen();
//        for (int i = 0; i < 100; i++) {
//            Result r = idGen.get("leaf-segment-test");
//            System.out.println("tettttt:"+r);
//        }
    }
}