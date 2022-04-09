package com.qk.dm.reptile.mapstruct.mapper;

import com.qk.dm.reptile.entity.RptBaseInfo;
import com.qk.dm.reptile.params.dto.RptBaseInfoBatchDTO;
import com.qk.dm.reptile.params.dto.RptBaseInfoDTO;
import com.qk.dm.reptile.params.vo.RptBaseInfoVO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 配置信息vo、实体转换
 */
@Mapper
public interface RptBaseInfoMapper {
  RptBaseInfoMapper INSTANCE = Mappers.getMapper(RptBaseInfoMapper.class);

  RptBaseInfo userRtpBaseInfo(RptBaseInfoDTO rtpBaseInfoDTO);

  RptBaseInfoVO userRtpBaseInfoVO(RptBaseInfo rptBaseInfo);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void of(RptBaseInfoDTO rtpBaseInfoDTO, @MappingTarget RptBaseInfo rptBaseInfo);

  List<RptBaseInfoVO> of(List<RptBaseInfo> rptBaseInfoList);

  RptBaseInfo userVoToRtpBaseInfo(RptBaseInfoVO rptBaseInfoVO);

  RptBaseInfo of(RptBaseInfoBatchDTO rptBaseInfoBatchDTO);
}
