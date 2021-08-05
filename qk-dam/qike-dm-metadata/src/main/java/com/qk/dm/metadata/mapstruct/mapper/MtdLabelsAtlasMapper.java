package com.qk.dm.metadata.mapstruct.mapper;

import com.qk.dm.metadata.entity.MtdLabelsAtlas;
import com.qk.dm.metadata.vo.MtdLabelsAtlasVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MtdLabelsAtlasMapper {
  MtdLabelsAtlasMapper INSTANCE = Mappers.getMapper(MtdLabelsAtlasMapper.class);

  MtdLabelsAtlas useMtdLabelsAtlas(MtdLabelsAtlasVO mtdLabelsAtlasVO);

  MtdLabelsAtlasVO useMtdLabelsAtlasVO(MtdLabelsAtlas mtdLabelsAtlas);
}
