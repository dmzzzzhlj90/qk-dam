package com.qk.dm.metadata.service;

import com.qk.dm.metadata.vo.MtdApiVO;
import com.qk.dm.metadata.vo.MtdAtlasEntityTypeVO;

import java.util.List;

/**
 * @author wangzp
 * @date 2021/8/23 15:22
 * @since 1.0.0
 */
public interface MtdApiService {
  /**
   * 获取所有的类型
   *
   * @return
   */
  List<MtdAtlasEntityTypeVO> getAllEntityType();

  /**
   * 获取元数据信息
   *
   * @param typeName
   * @param dbName
   * @param tableName
   * @return
   */
  MtdApiVO mtdDetail(String typeName, String dbName, String tableName);
}
