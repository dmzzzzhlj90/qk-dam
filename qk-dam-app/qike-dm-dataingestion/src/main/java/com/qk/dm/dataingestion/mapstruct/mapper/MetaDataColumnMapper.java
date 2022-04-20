package com.qk.dm.dataingestion.mapstruct.mapper;

import com.qk.dam.metedata.entity.MtdAttributes;
import com.qk.dm.dataingestion.vo.ColumnVO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface MetaDataColumnMapper {
    MetaDataColumnMapper INSTANCE = Mappers.getMapper(MetaDataColumnMapper.class);

    ColumnVO.Column of(MtdAttributes o);

    List<ColumnVO.Column> of(List<MtdAttributes> list);
}