package com.qk.dm.datamodel.mapstruct.mapper;

import com.qk.dm.datamodel.entity.ModelDim;
import com.qk.dm.datamodel.params.dto.ModelDimDTO;
import com.qk.dm.datamodel.params.dto.ModelDimTableDTO;
import com.qk.dm.datamodel.params.vo.ModelDimDetailVO;
import com.qk.dm.datamodel.params.vo.ModelDimVO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ModelDimMapper {

    ModelDimMapper INSTANCE = Mappers.getMapper(ModelDimMapper.class);

    ModelDim of(ModelDimDTO modelDimDTO);

    ModelDimVO of(ModelDim modelDim);

    ModelDimTableDTO ofDimTable(ModelDimDTO modelDimDTO);

    List<ModelDimVO> of(List<ModelDim> modelDimList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void from(ModelDimDTO modelDimDTO, @MappingTarget ModelDim modelDim);

    ModelDimDetailVO ofDetail(ModelDim modelDim);

}
