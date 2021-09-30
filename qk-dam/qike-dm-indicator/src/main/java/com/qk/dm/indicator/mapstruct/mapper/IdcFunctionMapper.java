package com.qk.dm.indicator.mapstruct.mapper;

import com.qk.dm.indicator.entity.IdcFunction;
import com.qk.dm.indicator.params.dto.IdcFunctionDTO;
import com.qk.dm.indicator.params.vo.IdcFunctionVO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author shenpj
 * @date 2021/9/1 2:48 下午
 * @since 1.0.0
 */
@Mapper
//        (nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
//                nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface IdcFunctionMapper {
    IdcFunctionMapper INSTANCE = Mappers.getMapper(IdcFunctionMapper.class);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void useIdcFunction(IdcFunctionDTO idcFunctionDTO, @MappingTarget IdcFunction idcFunction);

    IdcFunction useIdcFunction(IdcFunctionDTO idcFunctionDTO);

    IdcFunctionVO useIdcFunctionVO(IdcFunction idcFunction);

    List<IdcFunctionVO> useIdcFunctionVO(List<IdcFunction> idcFunction);
}
