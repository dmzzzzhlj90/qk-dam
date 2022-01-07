package com.qk.dm.datamodel.mapstruct.mapper;

import com.qk.dm.datamodel.entity.ModelFactTable;
import com.qk.dm.datamodel.params.dto.ModelFactTableDTO;
import com.qk.dm.datamodel.params.vo.ModelFactTableVO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ModelFactTableMapper {

    ModelFactTableMapper INSTANCE = Mappers.getMapper(ModelFactTableMapper.class);

    ModelFactTable of(ModelFactTableDTO modelFactTableDTO);

    ModelFactTableVO of(ModelFactTable modelFactTable);

    List<ModelFactTableVO> of(List<ModelFactTable> modelFactTableList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void from(ModelFactTableDTO modelFactTableDTO, @MappingTarget ModelFactTable modelFactTable);
}
