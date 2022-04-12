package com.qk.dm.dataingestion.mapstruct.mapper;

import com.qk.dm.dataingestion.entity.DisSchedulerConfig;
import com.qk.dm.dataingestion.vo.DisSchedulerConfigVO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface DisSchedulerConfigMapper {
    DisSchedulerConfigMapper INSTANCE = Mappers.getMapper(DisSchedulerConfigMapper.class);

    DisSchedulerConfig of(DisSchedulerConfigVO disSchedulerConfigVO);

    List<DisSchedulerConfig> list(List<DisSchedulerConfigVO> disSchedulerConfigVOList);

    DisSchedulerConfigVO of(DisSchedulerConfig disSchedulerConfig);

    List<DisSchedulerConfigVO> listVO(List<DisSchedulerConfig> disSchedulerConfigList);
}
