package com.qk.dm.datastandards.test.caffeine.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.qk.dm.datastandards.entity.DsdCodeInfoExt;
import com.qk.dm.datastandards.entity.QDsdCodeInfoExt;
import com.qk.dm.datastandards.repositories.DsdCodeInfoExtRepository;
import com.qk.dm.datastandards.test.caffeine.service.TestCaffeineCacheService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author wjq
 * @date 2021/10/22 15:39
 * @since 1.0.0
 */
@Service
@CacheConfig(cacheNames = "caffeineCacheManager")
public class TestCaffeineCacheServiceImpl implements TestCaffeineCacheService {

  private final QDsdCodeInfoExt qDsdCodeInfoExt = QDsdCodeInfoExt.dsdCodeInfoExt;

  private final DsdCodeInfoExtRepository dsdCodeInfoExtRepository;
  private final Cache<String, Object> caffeineCache;

  @Autowired
  public TestCaffeineCacheServiceImpl(
      DsdCodeInfoExtRepository dsdCodeInfoExtRepository, Cache<String, Object> caffeineCache) {
    this.dsdCodeInfoExtRepository = dsdCodeInfoExtRepository;
    this.caffeineCache = caffeineCache;
  }

  @Override
  @Cacheable(key = "#dsdCodeInfoExt.id")
  public DsdCodeInfoExt queryData(Long id) {
    // 先从缓存读取
    //        caffeineCache.getIfPresent(id);
    //        DsdCodeInfoExt dsdCodeInfoExt = (DsdCodeInfoExt)
    // caffeineCache.asMap().get(String.valueOf(id));
    //        if (dsdCodeInfoExt != null) {
    //            System.out.println("缓存中获取到数据!");
    //            return dsdCodeInfoExt;
    //        }

    DsdCodeInfoExt codeInfoExt = null;
    Optional<DsdCodeInfoExt> codeInfoExtOptional = dsdCodeInfoExtRepository.findById(id);
    if (codeInfoExtOptional.isPresent()) {
      codeInfoExt = codeInfoExtOptional.get();
      //            caffeineCache.put(String.valueOf(codeInfoExt.getId()), codeInfoExt);
      //            System.out.println("新的缓存数据成功!");
    }
    return codeInfoExt;
  }

  @CachePut(key = "#dsdCodeInfoExt.id")
  @Override
  public void addData(DsdCodeInfoExt dsdCodeInfoExt) {
    dsdCodeInfoExtRepository.saveAndFlush(dsdCodeInfoExt);
    // 加入缓存
    //        caffeineCache.put(String.valueOf(dsdCodeInfoExt.getId()), dsdCodeInfoExt);
    //        System.out.println("数据缓存成功!");
  }

  @CachePut(key = "#dsdCodeInfoExt.id")
  @Override
  public void updateData(DsdCodeInfoExt dsdCodeInfoExt) {
    dsdCodeInfoExtRepository.saveAndFlush(dsdCodeInfoExt);
  }

  @CacheEvict(key = "#id")
  @Override
  public void deleteData(Long id) {
    dsdCodeInfoExtRepository.deleteById(id);
    // 从缓存中删除
    //        caffeineCache.asMap().remove(String.valueOf(id));
    //        System.out.println("从缓存中删除!");
  }
}
