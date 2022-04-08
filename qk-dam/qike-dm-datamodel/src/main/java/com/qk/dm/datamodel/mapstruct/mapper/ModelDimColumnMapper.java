package com.qk.dm.datamodel.mapstruct.mapper;

import com.qk.dm.datamodel.entity.ModelDimColumn;
import com.qk.dm.datamodel.params.dto.ModelDimColumnDTO;
import com.qk.dm.datamodel.params.dto.ModelDimTableColumnDTO;
import com.qk.dm.datamodel.params.vo.ModelDimColumnVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ModelDimColumnMapper {

    ModelDimColumnMapper INSTANCE = Mappers.getMapper(ModelDimColumnMapper.class);

    ModelDimColumnVO of(ModelDimColumnDTO modelDimColumnDTO);

    List<ModelDimColumn> use(List<ModelDimColumnDTO> modelDimColumnDTOList);

    List<ModelDimTableColumnDTO> ofDimTableColumn(List<ModelDimColumnDTO> modelDimColumnList);

    List<ModelDimTableColumnDTO> ofDimTableColumnDTO(List<ModelDimColumnVO> modelDimColumnList);

    ModelDimColumnVO of(ModelDimColumn modelDimColumn);

    List<ModelDimColumnVO> of(List<ModelDimColumn> modelDimColumnList);

}
