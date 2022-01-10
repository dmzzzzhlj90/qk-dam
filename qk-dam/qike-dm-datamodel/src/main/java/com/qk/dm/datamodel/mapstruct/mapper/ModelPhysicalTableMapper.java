package com.qk.dm.datamodel.mapstruct.mapper;

import com.qk.dm.datamodel.entity.ModelPhysicalTable;
import com.qk.dm.datamodel.params.dto.ModelPhysicalTableDTO;
import com.qk.dm.datamodel.params.dto.ModelReverseBaseDTO;
import com.qk.dm.datamodel.params.vo.ModelPhysicalTableVO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ModelPhysicalTableMapper {
    ModelPhysicalTableMapper INSTANCE = Mappers.getMapper(ModelPhysicalTableMapper.class);

    ModelPhysicalTable of(ModelPhysicalTableDTO modelPhysicalTableDTO);

    ModelPhysicalTableVO of(ModelPhysicalTable modelPhysicalTable);

    List<ModelPhysicalTableVO> of(List<ModelPhysicalTable> modelPhysicalTableList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void from(ModelPhysicalTableDTO modelPhysicalTableDTO, @MappingTarget ModelPhysicalTable modelPhysicalTable);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void copy(ModelReverseBaseDTO modelReverseBaseDTO, @MappingTarget ModelPhysicalTable modelPhysicalTable);
}
