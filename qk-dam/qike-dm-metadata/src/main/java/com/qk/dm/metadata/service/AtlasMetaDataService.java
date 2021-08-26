package com.qk.dm.metadata.service;

import com.qk.dm.metadata.vo.MtdAtlasBaseDetailVO;
import com.qk.dm.metadata.vo.MtdAtlasBaseSearchVO;
import com.qk.dm.metadata.vo.MtdAtlasBaseVO;
import com.qk.dam.metedata.entity.MtdAtlasEntityType;
import com.qk.dm.metadata.vo.MtdAtlasParamsVO;

import java.util.List;
import java.util.Map;

/**
 * @author wangzp
 * @date 2021/8/03 10:05
 * @since 1.0.0
 */
public interface AtlasMetaDataService {
  /**
   * 查找元数据信息
   *
   * @param mtdAtlasParamsVO
   * @return
   */
  List<MtdAtlasBaseVO> searchList(MtdAtlasParamsVO mtdAtlasParamsVO);

  /**
   * 查找元数据信息-高级条件
   * @param mtdAtlasBaseSearchVO
   * @param excludeDeletedEntities
   * @return
   */
  List<MtdAtlasBaseVO> searchList(
      MtdAtlasBaseSearchVO mtdAtlasBaseSearchVO, Boolean excludeDeletedEntities);

  /**
   * 获取元数据详情
   *
   * @param guid
   * @return
   */
  MtdAtlasBaseDetailVO getEntityByGuid(String guid);

  /**
   * 获取所有的基础类型
   *
   * @return
   */
  Map<String, List<MtdAtlasEntityType>> getAllEntityType();

  /**
   * 根据guid删除元数据信息
   *
   * @param guids
   */
  void deleteEntitiesByGuids(String guids);
}
