package com.qk.dm.reptile.mapstruct.mapper;

import com.qk.dm.reptile.entity.RptDimensionColumnInfo;
import com.qk.dm.reptile.params.dto.RptDimensionInfoColumnDTO;
import com.qk.dm.reptile.params.vo.RptDimensionInfoColumnVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
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
}
