package com.qk.dm.dataservice.mapstruct.mapper;

import com.qk.dm.dataservice.entity.DasApiDir;
import com.qk.dm.dataservice.vo.DasApiDirTreeVO;
import com.qk.dm.dataservice.vo.DasApiDirVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
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

  @Mappings({
          @Mapping(source = "id", target = "id"),
          @Mapping(source = "apiDirId", target = "dirId"),
          @Mapping(source = "apiDirName", target = "title"),
          @Mapping(source = "apiDirName", target = "value"),
          @Mapping(source = "parentId", target = "parentId")
  })
  DasApiDirTreeVO useDasApiDirTreeVO(DasApiDir dasApiDir);

  @Mappings({
          @Mapping(source = "dirId", target = "apiDirId"),
          @Mapping(source = "title", target = "apiDirName"),
          @Mapping(source = "parentId", target = "parentId")
  })
  DasApiDir useDasApiDir(DasApiDirVO dasApiDirVO);

  @Mappings({
          @Mapping(source = "apiDirId", target = "dirId"),
          @Mapping(source = "apiDirName", target = "title"),
          @Mapping(source = "apiDirName", target = "value"),
          @Mapping(source = "parentId", target = "parentId")
  })
  DasApiDirVO useDasApiDirVO(DasApiDir dasApiDir);

}
