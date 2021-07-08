package com.qk.dm.dataingestion.service.impl;

import com.qk.dam.sqlloader.repo.PiciTaskLogAgg;
import com.qk.dm.dataingestion.service.PiciTaskLogService;
import org.springframework.stereotype.Service;

/**
 * @author wjq
 * @date 2021/7/6 10:55
 * @since 1.0.0
 */
@Service
public class PiciTaskLogServiceImpl implements PiciTaskLogService {

  @Override
  public Integer modifyIsHiveByTableNameAndPici(String tableName, String pici) {
    return PiciTaskLogAgg.qkLogPiciModifyIsHive(tableName, pici);
  }
}
