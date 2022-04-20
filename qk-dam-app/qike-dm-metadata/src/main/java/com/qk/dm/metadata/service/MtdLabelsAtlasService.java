package com.qk.dm.metadata.service;

import com.qk.dm.metadata.vo.MtdLabelsAtlasBulkVO;
import com.qk.dm.metadata.vo.MtdLabelsAtlasVO;
import java.util.List;

public interface MtdLabelsAtlasService {

  void insert(MtdLabelsAtlasVO mtdLabelsAtlasVO);

  void update(MtdLabelsAtlasVO mtdLabelsAtlasVO);

  MtdLabelsAtlasVO getByGuid(String guid);

  void bulk(MtdLabelsAtlasBulkVO mtdLabelsVO);

  List<MtdLabelsAtlasVO> getByBulk(List<String> guids);
}
