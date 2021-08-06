package com.qk.dm.metadata.service;

import com.qk.dm.metadata.vo.MtdAtlasBaseDetailVO;
import com.qk.dm.metadata.vo.MtdAtlasBaseVO;
import com.qk.dm.metadata.vo.MtdAtlasEntityTypeVO;

import java.util.List;

public interface AtlasMetaDataService {
  /**
   * 查找元数据信息
   *
   * @param query
   * @param typeName
   * @param classification
   * @param limit
   * @param offse
   * @return
   */
  List<MtdAtlasBaseVO> searchList(
      String query, String typeName, String classification, int limit, int offse);

  /**
   * 获取元数据详情
   *
   * @param guid
   * @return
   */
  MtdAtlasBaseDetailVO getEntityByGuid(String guid);

  /**
   * 获取所有的基础类型
   * @return
   */
  List<MtdAtlasEntityTypeVO> getAllEntityType();
}
