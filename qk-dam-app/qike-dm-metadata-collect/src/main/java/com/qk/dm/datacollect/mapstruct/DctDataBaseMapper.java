package com.qk.dm.datacollect.mapstruct;

import com.qk.dam.catacollect.vo.MetadataConnectInfoVo;
import com.qk.dm.datacollect.vo.DctSchedulerRulesVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface DctDataBaseMapper {
  DctDataBaseMapper INSTANCE = Mappers.getMapper(DctDataBaseMapper.class);

  void schedulerRulesToConnect(DctSchedulerRulesVO dctSchedulerRulesVO, @MappingTarget MetadataConnectInfoVo metadataConnectInfoVo);
}
