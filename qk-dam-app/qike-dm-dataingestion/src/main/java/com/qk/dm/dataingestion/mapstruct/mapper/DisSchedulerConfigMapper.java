package com.qk.dm.dataingestion.mapstruct.mapper;

import com.qk.dm.dataingestion.entity.DisSchedulerConfig;
import com.qk.dm.dataingestion.vo.DisSchedulerConfigVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface DisSchedulerConfigMapper {
    DisSchedulerConfigMapper INSTANCE = Mappers.getMapper(DisSchedulerConfigMapper.class);
    @Mappings({
            @Mapping(target = "gmtCreate",ignore = true),
            @Mapping(target = "gmtModified",ignore = true),
            @Mapping(target = "delFlag",ignore = true),
    })
    DisSchedulerConfig of(DisSchedulerConfigVO disSchedulerConfigVO);

    List<DisSchedulerConfig> list(List<DisSchedulerConfigVO> disSchedulerConfigVOList);

    DisSchedulerConfigVO of(DisSchedulerConfig disSchedulerConfig);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void of(DisSchedulerConfigVO disSchedulerConfigVO, @MappingTarget DisSchedulerConfig disSchedulerConfig);
}
