package com.qk.dm.dataservice.mapstruct.mapper;

import com.qk.dm.dataservice.entity.DasApiCreateSqlScript;
import com.qk.dm.dataservice.vo.DasApiCreateSqlScriptDefinitionVO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T17:36:28+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class DasApiCreateSqlScriptMapperImpl implements DasApiCreateSqlScriptMapper {

    @Override
    public DasApiCreateSqlScriptDefinitionVO useDasApiCreateSqlScriptDefinitionVO(DasApiCreateSqlScript apiCreateSqlScript) {
        if ( apiCreateSqlScript == null ) {
            return null;
        }

        DasApiCreateSqlScriptDefinitionVO dasApiCreateSqlScriptDefinitionVO = new DasApiCreateSqlScriptDefinitionVO();

        dasApiCreateSqlScriptDefinitionVO.setId( apiCreateSqlScript.getId() );
        dasApiCreateSqlScriptDefinitionVO.setApiId( apiCreateSqlScript.getApiId() );
        dasApiCreateSqlScriptDefinitionVO.setAccessMethod( apiCreateSqlScript.getAccessMethod() );
        dasApiCreateSqlScriptDefinitionVO.setConnectType( apiCreateSqlScript.getConnectType() );
        dasApiCreateSqlScriptDefinitionVO.setDataSourceName( apiCreateSqlScript.getDataSourceName() );
        dasApiCreateSqlScriptDefinitionVO.setDataBaseName( apiCreateSqlScript.getDataBaseName() );
        dasApiCreateSqlScriptDefinitionVO.setSqlPara( apiCreateSqlScript.getSqlPara() );
        dasApiCreateSqlScriptDefinitionVO.setDescription( apiCreateSqlScript.getDescription() );

        return dasApiCreateSqlScriptDefinitionVO;
    }

    @Override
    public DasApiCreateSqlScript useDasApiCreateSqlScript(DasApiCreateSqlScriptDefinitionVO apiCreateSqlScriptDefinitionVO) {
        if ( apiCreateSqlScriptDefinitionVO == null ) {
            return null;
        }

        DasApiCreateSqlScript dasApiCreateSqlScript = new DasApiCreateSqlScript();

        dasApiCreateSqlScript.setId( apiCreateSqlScriptDefinitionVO.getId() );
        dasApiCreateSqlScript.setApiId( apiCreateSqlScriptDefinitionVO.getApiId() );
        dasApiCreateSqlScript.setAccessMethod( apiCreateSqlScriptDefinitionVO.getAccessMethod() );
        dasApiCreateSqlScript.setConnectType( apiCreateSqlScriptDefinitionVO.getConnectType() );
        dasApiCreateSqlScript.setDataSourceName( apiCreateSqlScriptDefinitionVO.getDataSourceName() );
        dasApiCreateSqlScript.setDataBaseName( apiCreateSqlScriptDefinitionVO.getDataBaseName() );
        dasApiCreateSqlScript.setDescription( apiCreateSqlScriptDefinitionVO.getDescription() );
        dasApiCreateSqlScript.setSqlPara( apiCreateSqlScriptDefinitionVO.getSqlPara() );

        return dasApiCreateSqlScript;
    }
}
