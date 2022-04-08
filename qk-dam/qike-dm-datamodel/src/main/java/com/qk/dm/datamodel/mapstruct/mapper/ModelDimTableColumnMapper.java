package com.qk.dm.datamodel.mapstruct.mapper;

import com.qk.dm.datamodel.entity.ModelDimColumn;
import com.qk.dm.datamodel.entity.ModelDimTableColumn;
import com.qk.dm.datamodel.params.dto.ModelDimTableColumnDTO;
import com.qk.dm.datamodel.params.vo.ModelDimTableColumnVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ModelDimTableColumnMapper {
    ModelDimTableColumnMapper INSTANCE = Mappers.getMapper(ModelDimTableColumnMapper.class);

    List<ModelDimTableColumn> use(List<ModelDimTableColumnDTO> modelDimTableColumnDTOList);

    ModelDimTableColumnVO of(ModelDimTableColumn modelDimTableColumn);

    List<ModelDimTableColumnVO> of(List<ModelDimTableColumn> modelDimTableColumnList);

    @Mapping(target = "id",ignore = true)
    ModelDimTableColumn of(ModelDimColumn modelDimColumn);

    List<ModelDimTableColumnDTO> trans(List<ModelDimColumn> modelDimColumnList);

}
