package com.qk.dm.metadata.service;

import com.qk.dm.metadata.vo.MtdLabelsAtlasVO;

public interface MtdLabelsAtlasService {

  void insert(MtdLabelsAtlasVO mtdLabelsAtlasVO);

  void update(Long id, MtdLabelsAtlasVO mtdLabelsAtlasVO);

  MtdLabelsAtlasVO getByGuid(String guid);
}
