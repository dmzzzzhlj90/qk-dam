package com.qk.dm.dataingestion.mapstruct.mapper;


import com.qk.dm.dataingestion.entity.DisAttrView;
import com.qk.dm.dataingestion.vo.DisAttrViewVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface DisAttrViewMapper {
    DisAttrViewMapper INSTANCE = Mappers.getMapper(DisAttrViewMapper.class);

    @Mappings({
            @Mapping(source = "defaultValue", target = "initialValue")
    })
    DisAttrViewVO of(DisAttrView disAttrView);

    List<DisAttrViewVO> of(List<DisAttrView> disAttrViewList);
}
