package com.qk.dm.dataingestion.service;

import org.springframework.stereotype.Service;

/**
 * 原始层数据同步
 *
 * @author wjq
 * @date 2021/6/8
 * @since 1.0.0
 */
@Service
public interface PiciTaskDataFileSyncService {
  int syncPiciTaskFilesData(
      String dataDay, String frontTabNamePatter, String batchNum, String bucketName);
}
