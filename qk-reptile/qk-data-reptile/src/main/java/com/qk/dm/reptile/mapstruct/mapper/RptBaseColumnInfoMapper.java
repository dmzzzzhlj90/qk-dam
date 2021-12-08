package com.qk.dm.reptile.mapstruct.mapper;

import com.qk.dm.reptile.entity.RptBaseColumnInfo;
import com.qk.dm.reptile.params.dto.RptBaseColumnInfoDTO;
import com.qk.dm.reptile.params.vo.RptBaseColumnInfoVO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 配置信息字段vo、实体转换
 */
@Mapper
public interface RptBaseColumnInfoMapper {
  RptBaseColumnInfoMapper INSTANCE = Mappers.getMapper(RptBaseColumnInfoMapper.class);

  RptBaseColumnInfo userRptBaseColumnInfo(RptBaseColumnInfoDTO rptBaseColumnInfoDTO);

  RptBaseColumnInfoVO userRptBaseColumnInfoVO(RptBaseColumnInfo rptBaseColumnInfo);

  List<RptBaseColumnInfo> of(List<RptBaseColumnInfoDTO> rptBaseColumnInfoDTOList);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void of(RptBaseColumnInfoDTO rptBaseColumnInfoDTO, @MappingTarget RptBaseColumnInfo rptBaseColumnInfo);

  List<RptBaseColumnInfoVO> ofVO(List<RptBaseColumnInfo> rptBaseColumnInfoList);
}
