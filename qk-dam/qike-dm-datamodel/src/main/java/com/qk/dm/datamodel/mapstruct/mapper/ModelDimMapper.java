package com.qk.dm.datamodel.mapstruct.mapper;

import com.qk.dm.datamodel.entity.ModelDim;
import com.qk.dm.datamodel.params.dto.ModelDimDTO;
import com.qk.dm.datamodel.params.dto.ModelDimTableDTO;
import com.qk.dm.datamodel.params.vo.ModelDimDetailVO;
import com.qk.dm.datamodel.params.vo.ModelDimVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ModelDimMapper {

    ModelDimMapper INSTANCE = Mappers.getMapper(ModelDimMapper.class);

    ModelDim of(ModelDimDTO modelDimDTO);

    ModelDimVO of(ModelDim modelDim);

    ModelDimTableDTO ofDimTable(ModelDimDTO modelDimDTO);

    @IterableMapping(elementTargetType = ModelDimVO.class)
    List<ModelDimVO> of(List<ModelDim> modelDimList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void from(ModelDimDTO modelDimDTO, @MappingTarget ModelDim modelDim);

    @IterableMapping(elementTargetType = ModelDimDetailVO.class)
    ModelDimDetailVO ofDetail(ModelDim modelDim);

}
