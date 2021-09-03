package com.qk.dm.metadata.service;

import com.qk.dm.metadata.vo.MtdClassifyAtlasVO;

import java.util.List;

public interface MtdClassifyAtlasService {
  void insert(MtdClassifyAtlasVO mtdClassifyAtlasVO);

  void update(MtdClassifyAtlasVO mtdClassifyAtlasVO);

  MtdClassifyAtlasVO getByGuid(String guid);

  List<MtdClassifyAtlasVO> getByBulk(List<String> guids);
}
