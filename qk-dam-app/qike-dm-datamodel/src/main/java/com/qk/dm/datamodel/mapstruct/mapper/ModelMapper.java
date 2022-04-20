package com.qk.dm.datamodel.mapstruct.mapper;

import com.qk.dm.datamodel.entity.Model;
import com.qk.dm.datamodel.params.dto.ModelDTO;
import com.qk.dm.datamodel.params.vo.ModelVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ModelMapper {
    ModelMapper INSTANCE = Mappers.getMapper(ModelMapper.class);

    Model useModel(ModelDTO modelDTO);

    List<ModelVO> userModelVO(List<Model> modelList);
}
