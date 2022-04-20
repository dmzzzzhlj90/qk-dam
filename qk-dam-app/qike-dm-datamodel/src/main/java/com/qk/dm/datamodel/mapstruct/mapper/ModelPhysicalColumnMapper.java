package com.qk.dm.datamodel.mapstruct.mapper;

import com.qk.dm.datamodel.entity.ModelPhysicalColumn;
import com.qk.dm.datamodel.params.dto.ModelPhysicalColumnDTO;
import com.qk.dm.datamodel.params.vo.ModelPhysicalColumnVO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ModelPhysicalColumnMapper {

    ModelPhysicalColumnMapper INSTANCE = Mappers.getMapper(ModelPhysicalColumnMapper.class);

    List<ModelPhysicalColumn> use(List<ModelPhysicalColumnDTO> modelPhysicalColumnDTOList);

    ModelPhysicalColumnVO of(ModelPhysicalColumn modelPhysicalColumn);

    List<ModelPhysicalColumnVO> of(List<ModelPhysicalColumn> modelPhysicalColumnList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void from(ModelPhysicalColumnDTO modelPhysicalColumnDTO, @MappingTarget ModelPhysicalColumn modelPhysicalColumn);
}
