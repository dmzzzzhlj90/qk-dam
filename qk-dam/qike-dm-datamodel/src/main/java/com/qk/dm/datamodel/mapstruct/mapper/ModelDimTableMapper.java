package com.qk.dm.datamodel.mapstruct.mapper;

import com.qk.dm.datamodel.entity.ModelDimTable;
import com.qk.dm.datamodel.params.dto.ModelDimTableDTO;
import com.qk.dm.datamodel.params.vo.ModelDimTableDetailVO;
import com.qk.dm.datamodel.params.vo.ModelDimTableVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ModelDimTableMapper {

    ModelDimTableMapper INSTANCE = Mappers.getMapper(ModelDimTableMapper.class);

    ModelDimTable of(ModelDimTableDTO modelDimTableDTO);

    ModelDimTableVO of(ModelDimTable modelDimTable);

    @IterableMapping(elementTargetType = ModelDimTableVO.class)
    List<ModelDimTableVO> of(List<ModelDimTable> modelDimTableList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void from(ModelDimTableDTO modelDimTableDTO, @MappingTarget ModelDimTable modelDimTable);

    @IterableMapping(elementTargetType = ModelDimTableDetailVO.class)
    ModelDimTableDetailVO ofDetail(ModelDimTable modelDimTable);
}
