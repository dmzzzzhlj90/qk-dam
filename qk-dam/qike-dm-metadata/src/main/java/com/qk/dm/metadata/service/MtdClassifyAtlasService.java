package com.qk.dm.metadata.service;

import com.qk.dm.metadata.vo.MtdClassifyAtlasVO;

public interface MtdClassifyAtlasService {
  void insert(MtdClassifyAtlasVO mtdClassifyAtlasVO);

  void update(MtdClassifyAtlasVO mtdClassifyAtlasVO);

  MtdClassifyAtlasVO getByGuid(String guid);
}
