package com.qk.dm.datacollect.mapstruct;

import com.qk.dam.metadata.catacollect.pojo.MetadataConnectInfoVo;
import com.qk.dm.datacollect.vo.DctBaseInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface DctDataBaseMapper {
  DctDataBaseMapper INSTANCE = Mappers.getMapper(DctDataBaseMapper.class);

  void from(DctBaseInfoVO dctBaseInfoVO, @MappingTarget MetadataConnectInfoVo metadataConnectInfoVo);
}
