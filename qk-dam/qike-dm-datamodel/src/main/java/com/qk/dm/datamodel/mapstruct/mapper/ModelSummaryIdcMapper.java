package com.qk.dm.datamodel.mapstruct.mapper;

import com.qk.dm.datamodel.entity.ModelSummaryIdc;
import com.qk.dm.datamodel.params.dto.ModelSummaryIdcDTO;
import com.qk.dm.datamodel.params.vo.ModelSummaryIdcVO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ModelSummaryIdcMapper {

    ModelSummaryIdcMapper INSTANCE = Mappers.getMapper(ModelSummaryIdcMapper.class);

    ModelSummaryIdc of(ModelSummaryIdcDTO modelSummaryIdcDTO);

    List<ModelSummaryIdc> use(List<ModelSummaryIdcDTO> modelSummaryIdcDTOList);

    ModelSummaryIdcVO of(ModelSummaryIdc modelSummaryIdc);

    List<ModelSummaryIdcVO> of(List<ModelSummaryIdc> modelSummaryIdcList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void from(ModelSummaryIdcDTO modelSummaryIdcDTO, @MappingTarget ModelSummaryIdc modelSummaryIdc);

}
