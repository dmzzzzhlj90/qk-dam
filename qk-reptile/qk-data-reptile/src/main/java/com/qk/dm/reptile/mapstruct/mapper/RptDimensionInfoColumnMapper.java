package com.qk.dm.reptile.mapstruct.mapper;

import com.qk.dm.reptile.entity.RptDimensionColumnInfo;
import com.qk.dm.reptile.params.dto.RptDimensionInfoColumnDTO;
import com.qk.dm.reptile.params.vo.RptDimensionInfoColumnVO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
/**
 * 维度信息vo、实体转换
 */
public interface RptDimensionInfoColumnMapper {
  RptDimensionInfoColumnMapper INSTANCE = Mappers.getMapper(
      RptDimensionInfoColumnMapper.class);
  RptDimensionColumnInfo userRptDimensionInfoColumnDTO(
      RptDimensionInfoColumnDTO rptDimensionInfoColumnDTO);

  RptDimensionInfoColumnVO userRptDimensionInfoColumnVO(RptDimensionColumnInfo rptDimensionColumnInfo);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void of(RptDimensionInfoColumnDTO rptDimensionInfoColumnDTO, @MappingTarget RptDimensionColumnInfo rptDimensionColumnInfo);

  List<RptDimensionInfoColumnVO> of(List<RptDimensionColumnInfo> list);
}
