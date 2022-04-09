package com.qk.dm.datastandards.mapstruct.mapper;

import com.qk.dm.datastandards.entity.DsdCodeInfo;
import com.qk.dm.datastandards.vo.DsdCodeInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 码表信息vo转换mapper
 *
 * @author wjq
 * @date 20210726
 * @since 1.0.0
 */
@Mapper
public interface DsdCodeInfoMapper {
  DsdCodeInfoMapper INSTANCE = Mappers.getMapper(DsdCodeInfoMapper.class);

  DsdCodeInfoVO useDsdCodeInfoVO(DsdCodeInfo dsdCodeInfo);

  DsdCodeInfo useDsdCodeInfo(DsdCodeInfoVO dsdCodeInfoVO);
}
