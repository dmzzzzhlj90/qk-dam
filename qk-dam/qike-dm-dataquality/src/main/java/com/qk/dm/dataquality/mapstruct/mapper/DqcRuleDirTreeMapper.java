package com.qk.dm.dataquality.mapstruct.mapper;


import com.qk.dm.dataquality.entity.DqcRuleDir;
import com.qk.dm.dataquality.vo.DqcRuleDirTreeVO;
import com.qk.dm.dataquality.vo.DqcRuleDirVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * 数据质量_规则模板目录vo转换mapper
 *
 * @author wjq
 * @date 2021/11/09
 * @since 1.0.0
 */
@Mapper
public interface DqcRuleDirTreeMapper {
  DqcRuleDirTreeMapper INSTANCE = Mappers.getMapper(DqcRuleDirTreeMapper.class);

  @Mappings({
          @Mapping(source = "ruleDirId", target = "key"),
          @Mapping(source = "ruleDirName", target = "title"),
          @Mapping(source = "ruleDirName", target = "value"),
          @Mapping(source = "parentId", target = "parentId")
  })
  DqcRuleDirTreeVO useDqcRuleDirTreeVO(DqcRuleDir dqcRuleDir);

  @Mappings({
          @Mapping(source = "key", target = "ruleDirId"),
          @Mapping(source = "title", target = "ruleDirName"),
          @Mapping(source = "parentId", target = "parentId")
  })
  DqcRuleDir useDqcRuleDir(DqcRuleDirVO dqcRuleDirVO);

}
