package com.qk.dm.reptile.mapstruct.mapper;

import com.qk.dm.reptile.entity.RptBaseInfo;
import com.qk.dm.reptile.params.dto.RptBaseInfoDTO;
import com.qk.dm.reptile.params.vo.RptBaseInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
/**
 * 配置信息vo、实体转换
 */
public interface RptBaseInfoMapper {
  RptBaseInfoMapper INSTANCE = Mappers.getMapper(RptBaseInfoMapper.class);

  RptBaseInfo userRtpBaseInfoDTO(RptBaseInfoDTO rtpBaseInfoDTO);

  RptBaseInfoVO userRtpBaseInfoVO(RptBaseInfo rptBaseInfo);
}
