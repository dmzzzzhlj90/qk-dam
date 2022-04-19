package com.qk.dm.datamodel.mapstruct.mapper;

import com.qk.dm.datamodel.entity.ModelFactColumn;
import com.qk.dm.datamodel.params.dto.ModelFactColumnDTO;
import com.qk.dm.datamodel.params.vo.ModelFactColumnVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ModelFactColumnMapper {
    ModelFactColumnMapper INSTANCE = Mappers.getMapper(ModelFactColumnMapper.class);

    List<ModelFactColumn> use(List<ModelFactColumnDTO> modelFactColumnDTOList);

    ModelFactColumnVO of(ModelFactColumn modelFactColumn);

    List<ModelFactColumnVO> of(List<ModelFactColumn> modelFactColumnList);
}
