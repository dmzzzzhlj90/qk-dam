package com.qk.dm.metadata.mapstruct.mapper;

import com.qk.dm.metadata.entity.MtdLabelsAtlas;
import com.qk.dm.metadata.vo.MtdLabelsAtlasVO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MtdLabelsAtlasMapper {
  MtdLabelsAtlasMapper INSTANCE = Mappers.getMapper(MtdLabelsAtlasMapper.class);

  MtdLabelsAtlas useMtdLabelsAtlas(MtdLabelsAtlasVO mtdLabelsAtlasVO);

  MtdLabelsAtlasVO useMtdLabelsAtlasVO(MtdLabelsAtlas mtdLabelsAtlas);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void useMtdLabelsAtlas(MtdLabelsAtlasVO mtdLabelsAtlasVO, @MappingTarget MtdLabelsAtlas mtdLabelsAtlas);

  List<MtdLabelsAtlasVO> useMtdLabelsAtlasListVO(List<MtdLabelsAtlas> mtdLabelsAtlass);
}
