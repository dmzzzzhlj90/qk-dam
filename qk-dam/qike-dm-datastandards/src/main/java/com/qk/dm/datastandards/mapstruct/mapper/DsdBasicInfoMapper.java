package com.qk.dm.datastandards.mapstruct.mapper;

import com.qk.dm.datastandards.entity.DsdBasicinfo;
import com.qk.dm.datastandards.vo.DsdBasicinfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 数据标准码基本信息vo转换mapper
 *
 * @author wjq
 * @date 2021/6/7
 * @since 1.0.0
 */
@Mapper
public interface DsdBasicInfoMapper {
  DsdBasicInfoMapper INSTANCE = Mappers.getMapper(DsdBasicInfoMapper.class);

  DsdBasicinfoVO useDsdBasicInfoVO(DsdBasicinfo dsdBasicinfo);

  DsdBasicinfo useDsdBasicInfo(DsdBasicinfoVO dsdBasicinfoVO);
}
