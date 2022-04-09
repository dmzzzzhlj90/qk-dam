package com.qk.dm.dataservice.mapstruct.mapper;

import com.qk.dm.dataservice.entity.DasApiCreateConfig;
import com.qk.dm.dataservice.vo.DasApiCreateConfigDefinitionVO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T20:19:53+0800",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.9.1.jar, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class DasApiCreateConfigMapperImpl implements DasApiCreateConfigMapper {

    @Override
    public DasApiCreateConfigDefinitionVO useDasApiCreateConfigDefinitionVO(DasApiCreateConfig dasApiCreateConfig) {
        if ( dasApiCreateConfig == null ) {
            return null;
        }

        DasApiCreateConfigDefinitionVO dasApiCreateConfigDefinitionVO = new DasApiCreateConfigDefinitionVO();

        dasApiCreateConfigDefinitionVO.setId( dasApiCreateConfig.getId() );
        dasApiCreateConfigDefinitionVO.setApiId( dasApiCreateConfig.getApiId() );
        dasApiCreateConfigDefinitionVO.setAccessMethod( dasApiCreateConfig.getAccessMethod() );
        dasApiCreateConfigDefinitionVO.setConnectType( dasApiCreateConfig.getConnectType() );
        dasApiCreateConfigDefinitionVO.setDataSourceName( dasApiCreateConfig.getDataSourceName() );
        dasApiCreateConfigDefinitionVO.setDataBaseName( dasApiCreateConfig.getDataBaseName() );
        dasApiCreateConfigDefinitionVO.setTableName( dasApiCreateConfig.getTableName() );
        dasApiCreateConfigDefinitionVO.setDescription( dasApiCreateConfig.getDescription() );

        return dasApiCreateConfigDefinitionVO;
    }

    @Override
    public DasApiCreateConfig useDasApiCreateConfig(DasApiCreateConfigDefinitionVO apiCreateConfigDefinitionVO) {
        if ( apiCreateConfigDefinitionVO == null ) {
            return null;
        }

        DasApiCreateConfig dasApiCreateConfig = new DasApiCreateConfig();

        dasApiCreateConfig.setId( apiCreateConfigDefinitionVO.getId() );
        dasApiCreateConfig.setApiId( apiCreateConfigDefinitionVO.getApiId() );
        dasApiCreateConfig.setAccessMethod( apiCreateConfigDefinitionVO.getAccessMethod() );
        dasApiCreateConfig.setConnectType( apiCreateConfigDefinitionVO.getConnectType() );
        dasApiCreateConfig.setDataSourceName( apiCreateConfigDefinitionVO.getDataSourceName() );
        dasApiCreateConfig.setDataBaseName( apiCreateConfigDefinitionVO.getDataBaseName() );
        dasApiCreateConfig.setTableName( apiCreateConfigDefinitionVO.getTableName() );
        dasApiCreateConfig.setDescription( apiCreateConfigDefinitionVO.getDescription() );

        return dasApiCreateConfig;
    }
}
