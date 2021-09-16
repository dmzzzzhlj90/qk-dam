package com.qk.dm.dataservice.mapstruct.mapper;

import com.qk.dm.dataservice.entity.DasApiDir;
import com.qk.dm.dataservice.vo.DasApiDirTreeVO;
import com.qk.dm.dataservice.vo.DasApiDirVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * API目录vo转换mapper
 *
 * @author wjq
 * @date 20210816
 * @since 1.0.0
 */
@Mapper
public interface DasApiDirTreeMapper {
  DasApiDirTreeMapper INSTANCE = Mappers.getMapper(DasApiDirTreeMapper.class);

  DasApiDirTreeVO useDasApiDirTreeVO(DasApiDir dasApiDir);

  DasApiDir useDasApiDir(DasApiDirVO dasApiDirVO);

  DasApiDirVO useDasApiDirVO(DasApiDir dasApiDir);
}
