package com.qk.dm.metadata.mapstruct.mapper;

import com.qk.dm.metadata.entity.MtdClassifyAtlas;
import com.qk.dm.metadata.vo.MtdClassifyAtlasVO;
import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

/**
 * @author wangzp
 * @date 2021/7/31 11:47
 * @since 1.0.0
 */
@Mapper
public interface MtdClassifyAtlasMapper {
  MtdClassifyAtlasMapper INSTANCE = Mappers.getMapper(MtdClassifyAtlasMapper.class);

  MtdClassifyAtlas useMtdClassifyAtlas(MtdClassifyAtlasVO mtdClassifyAtlasVO);

  MtdClassifyAtlasVO useMtdClassifyAtlasVO(MtdClassifyAtlas mtdClassifyAtlas);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void useMtdClassifyAtlas(
      MtdClassifyAtlasVO mtdClassifyAtlasVO, @MappingTarget MtdClassifyAtlas mtdClassifyAtlas);

  List<MtdClassifyAtlasVO> useMtdClassifyAtlasListVO(List<MtdClassifyAtlas> mtdClassifyAtlass);
}
