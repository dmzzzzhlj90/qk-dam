package com.qk.dm.datamodel.mapstruct.mapper;

import com.qk.dam.entity.DataStandardInfoVO;
import com.qk.dam.entity.DataStandardTreeVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 关系建模获取主题
 */
@Mapper
public interface ModelDirMapper {
  ModelDirMapper INSTANCE = Mappers.getMapper(ModelDirMapper.class);
  @Mappings({
      @Mapping(source = "id", target = "id"),
      @Mapping(source = "dirDsdId", target = "dirId"),
      @Mapping(source = "dirDsdName", target = "title"),
      @Mapping(source = "dirDsdId", target = "value"),
      @Mapping(source = "parentId", target = "parentId")
  })
  DataStandardInfoVO of(DataStandardTreeVO dataStandardTreeVO);

  List<DataStandardInfoVO> list(List<DataStandardTreeVO> tree);
}
