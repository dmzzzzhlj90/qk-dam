package com.qk.dm.datastandards.mapstruct.mapper;

import com.qk.dm.datastandards.entity.DsdCodeInfoExt;
import com.qk.dm.datastandards.vo.DsdCodeInfoExtVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 码值信息vo转换mapper
 *
 * @author wjq
 * @date 20210726
 * @since 1.0.0
 */
@Mapper
public interface DsdCodeInfoExtMapper {
  DsdCodeInfoExtMapper INSTANCE = Mappers.getMapper(DsdCodeInfoExtMapper.class);

  DsdCodeInfoExtVO useDsdCodeInfoExtVO(DsdCodeInfoExt dsdCodeInfoExt);

  DsdCodeInfoExt useDsdCodeInfoExt(DsdCodeInfoExtVO dsdCodeInfoExtVO);
}
