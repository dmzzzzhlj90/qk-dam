package com.qk.dm.datastandards.test.caffeine.service;

import com.qk.dm.datastandards.entity.DsdCodeInfoExt;
import org.springframework.stereotype.Service;

/**
 * 测试CaffeineCache缓存机制
 *
 * @author wjq
 * @date 20211022
 * @since 1.0.0
 */
@Service
public interface TestCaffeineCacheService {

  DsdCodeInfoExt queryData(Long id);

  void addData(DsdCodeInfoExt dsdCodeInfoExt);

  void updateData(DsdCodeInfoExt dsdCodeInfoExt);

  void deleteData(Long id);
}
