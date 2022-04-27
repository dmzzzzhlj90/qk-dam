package com.qk.dm.datacollect.mapstruct;

import com.qk.dm.datacollect.entity.DctTaskDir;
import com.qk.dm.datacollect.vo.DctTaskDirTreeVO;
import com.qk.dm.datacollect.vo.DctTaskDirVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * 任务目录实体-VO相互转换
 */
@Mapper
public interface DctTaskDirTreeMapper {
  DctTaskDirTreeMapper INSTANCE = Mappers.getMapper(DctTaskDirTreeMapper.class);

  @Mappings({
      @Mapping(source = "id", target = "id"),
      @Mapping(source = "ruleDirId", target = "dirId"),
      @Mapping(source = "ruleDirName", target = "title"),
      @Mapping(source = "ruleDirId", target = "value"),
      @Mapping(source = "parentId", target = "parentId")
  })
  DctTaskDirTreeVO useDqcRuleDirTreeVO(DctTaskDir dctTaskDir);

  @Mappings({
      @Mapping(source = "dirId", target = "ruleDirId"),
      @Mapping(source = "title", target = "ruleDirName"),
      @Mapping(source = "parentId", target = "parentId")
  })
  DctTaskDir useDqcRuleDir(DctTaskDirVO dctRuleDirVO);
}
