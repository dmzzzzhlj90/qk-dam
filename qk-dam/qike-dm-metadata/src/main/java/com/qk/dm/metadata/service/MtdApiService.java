package com.qk.dm.metadata.service;

import com.qk.dam.metedata.entity.MtdApi;
import com.qk.dam.metedata.entity.MtdAtlasEntityType;
import com.qk.dam.metedata.entity.MtdTables;
import java.util.List;
import java.util.Map;

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
  List<MtdAtlasEntityType> getAllEntityType();

  /**
   * 获取元数据详情
   *
   * @param typeName
   * @param dbName
   * @param tableName
   * @param server
   * @return
   */
  MtdApi mtdDetail(String typeName, String dbName, String tableName, String server);


  /**
   * 通过元数据获取表是否存在和表中是否存在数据
   * @param typeName
   * @param dbName
   * @param tableName
   * @param server
   * @return
   */
  Integer getExistData(String typeName, String dbName, String tableName, String server);
}
