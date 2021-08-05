package com.qk.dm.metadata.service;

import com.qk.dm.metadata.vo.AtlasBaseMainDataDetailVO;
import com.qk.dm.metadata.vo.AtlasBaseMainDataVO;
import java.util.List;

public interface AtlasMetaDataService {
  /**
   * 查找元数据信息
   *
   * @param query
   * @param typeName
   * @param excludeDeletedEntities
   * @param limit
   * @param offse
   * @return
   */
  List<AtlasBaseMainDataVO> searchList(
      String query, String typeName, boolean excludeDeletedEntities, int limit, int offse);

  /**
   * 获取元数据详情
   *
   * @param guid
   * @return
   */
  AtlasBaseMainDataDetailVO getEntityByGuid(String guid);
}
