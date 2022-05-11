package com.qk.dm.dataservice.mapstruct.mapper;

import com.qk.dm.dataservice.entity.DasApiCreateMybatisSqlScript;
import com.qk.dm.dataservice.vo.DasApiCreateMybatisSqlScriptDefinitionVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 新建API MYBATIS SQL高级方式-vo转换mapper
 *
 * @author wjq
 * @date 2022/05/10
 * @since 1.0.0
 */
@Mapper
public interface DasApiCreateMybatisSqlScriptMapper {
    DasApiCreateMybatisSqlScriptMapper INSTANCE = Mappers.getMapper(DasApiCreateMybatisSqlScriptMapper.class);

    DasApiCreateMybatisSqlScriptDefinitionVO useDasApiCreateMybatisSqlScriptDefinitionVO(DasApiCreateMybatisSqlScript apiCreateMybatisSqlScript);

    DasApiCreateMybatisSqlScript useDasApiCreateMybatisSqlScript(DasApiCreateMybatisSqlScriptDefinitionVO apiCreateMybatisSqlScriptDefinitionVO);

}
