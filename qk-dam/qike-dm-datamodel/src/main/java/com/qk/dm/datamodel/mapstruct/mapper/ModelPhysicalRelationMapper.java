package com.qk.dm.datamodel.mapstruct.mapper;

import com.qk.dm.datamodel.entity.ModelPhysicalRelation;
import com.qk.dm.datamodel.params.dto.ModelPhysicalRelationDTO;
import com.qk.dm.datamodel.params.vo.ModelPhysicalRelationVO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ModelPhysicalRelationMapper {

    ModelPhysicalRelationMapper INSTANCE = Mappers.getMapper(ModelPhysicalRelationMapper.class);

    List<ModelPhysicalRelation> use(List<ModelPhysicalRelationDTO> modelPhysicalRelationDTOList);

    ModelPhysicalRelationVO of(ModelPhysicalRelation modelPhysicalRelation);

    List<ModelPhysicalRelationVO> of(List<ModelPhysicalRelation> modelPhysicalRelationList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void from(ModelPhysicalRelationDTO modelPhysicalRelationDTO, @MappingTarget ModelPhysicalRelation modelPhysicalRelation);
}
