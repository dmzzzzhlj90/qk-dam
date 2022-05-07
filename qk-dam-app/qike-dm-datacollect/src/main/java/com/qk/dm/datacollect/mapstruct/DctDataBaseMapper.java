package com.qk.dm.datacollect.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface DctDataBaseMapper {
  DctDataBaseMapper INSTANCE = Mappers.getMapper(DctDataBaseMapper.class);
}
