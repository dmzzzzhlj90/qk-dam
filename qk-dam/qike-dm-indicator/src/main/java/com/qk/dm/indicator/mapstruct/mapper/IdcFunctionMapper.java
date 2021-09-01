package com.qk.dm.indicator.mapstruct.mapper;

import com.qk.dm.indicator.entity.IdcFunction;
import com.qk.dm.indicator.vo.IdcFunctionVO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
//        (nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
//                nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface IdcFunctionMapper {
    IdcFunctionMapper INSTANCE = Mappers.getMapper(IdcFunctionMapper.class);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void useIdcFunction(IdcFunctionVO idcFunctionVO, @MappingTarget IdcFunction idcFunction);
}
