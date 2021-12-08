package com.qk.dm.reptile.mapstruct.mapper;

import com.qk.dm.reptile.entity.RptDimensionInfo;
import com.qk.dm.reptile.params.dto.RptDimensionInfoDTO;
import com.qk.dm.reptile.params.vo.RptDimensionInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
/**
 * 维度目录vo、实体转换
 */
public interface RptDimensionInfoMapper {
  RptDimensionInfoMapper INSTANCE = Mappers.getMapper(RptDimensionInfoMapper.class);
  RptDimensionInfo userRptDimensionInfoDTO(RptDimensionInfoDTO rptDimensionInfoDTO);

  RptDimensionInfoVO userRptDimensionInfoVO(RptDimensionInfo rptDimensionInfo);
}
