package com.qk.dm.dataservice.mapstruct.mapper;

import com.qk.dm.dataservice.entity.DasApiCreateSqlScript;
import com.qk.dm.dataservice.vo.DasApiCreateSqlScriptVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 新建API配置方式-vo转换mapper
 *
 * @author wjq
 * @date 20210823
 * @since 1.0.0
 */
@Mapper
public interface DasApiCreateSqlScriptMapper {
  DasApiCreateSqlScriptMapper INSTANCE = Mappers.getMapper(DasApiCreateSqlScriptMapper.class);

  DasApiCreateSqlScriptVO useDasApiCreateSqlScriptVO(DasApiCreateSqlScript dasApiCreateSqlScript);

  DasApiCreateSqlScript useDasApiCreateSqlScript(DasApiCreateSqlScriptVO dasApiCreateSqlScriptVO);
}
