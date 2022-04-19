package com.qk.dm.metadata.mapstruct.mapper;

import com.qk.dm.metadata.vo.MtdLineageDetailVO;
import java.util.List;
import org.apache.atlas.model.instance.AtlasEntityHeader;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MtdLineageMapper {
  MtdLineageMapper INSTANCE = Mappers.getMapper(MtdLineageMapper.class);

  List<MtdLineageDetailVO> userMtdLineageDetailVO(List<AtlasEntityHeader> atlasEntityHeaderList);
}
