package com.qk.dm.datamodel.mapstruct.mapper;

import com.qk.dm.datamodel.entity.ModelSql;
import com.qk.dm.datamodel.params.dto.ModelSqlDTO;
import com.qk.dm.datamodel.params.vo.ModelSqlVO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ModelSqlMapper {
    ModelSqlMapper INSTANCE = Mappers.getMapper(ModelSqlMapper.class);

    ModelSql of(ModelSqlDTO modelSqlDTO);

    ModelSqlVO of(ModelSql modelSql);
    
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void from(ModelSqlDTO modelSqlDTO, @MappingTarget ModelSql modelSql);
}
