package com.qk.dm.datamodel.mapstruct.mapper;

import com.qk.dm.datamodel.entity.ModelFactTable;
import com.qk.dm.datamodel.entity.ModelSummary;
import com.qk.dm.datamodel.params.dto.ModelSummaryDTO;
import com.qk.dm.datamodel.params.vo.ModelSummaryVO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ModelSummaryMapper {

    ModelSummaryMapper INSTANCE = Mappers.getMapper(ModelSummaryMapper.class);

    ModelSummary of(ModelSummaryDTO modelSummaryDTO);

    ModelSummaryVO of(ModelSummary modelSummary);

    List<ModelSummaryVO> of(List<ModelSummary> modelSummaryList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void from(ModelSummaryDTO modelSummaryDTO, @MappingTarget ModelFactTable modelFactTable);
}
