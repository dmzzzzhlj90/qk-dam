package com.qk.dm.dataservice.mapstruct.mapper;

import com.qk.dm.dataservice.entity.DasApiBasicinfo;
import com.qk.dm.dataservice.vo.DasApiBasicinfoVO;
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

  DasApiBasicinfoVO useDasApiBasicInfoVO(DasApiBasicinfo dasApiBasicinfo);

  DasApiBasicinfo useDasApiBasicInfo(DasApiBasicinfoVO dasApiBasicinfoVO);
}
