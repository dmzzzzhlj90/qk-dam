package com.qk.dm.reptile.mapstruct.mapper;

import com.qk.dm.reptile.entity.RptDimensionInfo;
import com.qk.dm.reptile.params.dto.RptDimensionInfoDTO;
import com.qk.dm.reptile.params.vo.RptDimensionInfoParamsVO;
import com.qk.dm.reptile.params.vo.RptDimensionInfoVO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
/**
 * 维度目录vo、实体转换
 */
public interface RptDimensionInfoMapper {
  RptDimensionInfoMapper INSTANCE = Mappers.getMapper(RptDimensionInfoMapper.class);
  RptDimensionInfo userRptDimensionInfoDTO(RptDimensionInfoDTO rptDimensionInfoDTO);

  RptDimensionInfoVO userRptDimensionInfoVO(RptDimensionInfo rptDimensionInfo);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void of(RptDimensionInfoDTO rptDimensionInfoDTO, @MappingTarget RptDimensionInfo rptDimensionInfo);

  List<RptDimensionInfoVO> of(List<RptDimensionInfo> rptDimensionInfoList);

  List<RptDimensionInfoParamsVO> paramsof(List<RptDimensionInfo> rptDimensionInfoList);
}
