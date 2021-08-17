package com.qk.dm.dataservice.mapstruct.mapper;

import com.qk.dm.dataservice.entity.DasApiBasicInfo;
import com.qk.dm.dataservice.vo.DasApiBasicInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * API基础信息vo转换mapper
 *
 * @author wjq
 * @date 20210816
 * @since 1.0.0
 */
@Mapper
public interface DasApiBasicInfoMapper {
  DasApiBasicInfoMapper INSTANCE = Mappers.getMapper(DasApiBasicInfoMapper.class);

  DasApiBasicInfoVO useDasApiBasicInfoVO(DasApiBasicInfo dasApiBasicinfo);

  DasApiBasicInfo useDasApiBasicInfo(DasApiBasicInfoVO dasApiBasicinfoVO);
}
