package com.qk.dm.dataingestion.service.impl;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.sqlloader.repo.PiciTaskLogAgg;
import com.qk.dm.dataingestion.service.PiciTaskLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author wjq
 * @date 2021/7/6 10:55
 * @since 1.0.0
 */
@Service
public class PiciTaskLogServiceImpl implements PiciTaskLogService {
  private static final Log LOG = LogFactory.get("更新日志表is_hive状态!");

  @Override
  public Integer modifyIsHiveByTableNameAndPici(String tableName, String pici) throws BizException {
    if (StringUtils.isEmpty(tableName) || StringUtils.isEmpty(pici)) {
      LOG.info("请核对表名称为:【{}】,批次为:【{}】,参数有误!!!", tableName, pici);
      throw new BizException("请核对表名称为:" + tableName + ",批次为:" + pici + ",参数有误!!!");
    }
    return PiciTaskLogAgg.qkLogPiciModifyIsHive(tableName, pici);
  }
}
