package com.qk.dm.datamodel.mapstruct.mapper;

import com.qk.dm.datamodel.entity.ModelPhysicalTable;
import com.qk.dm.datamodel.params.dto.ModelPhysicalTableDTO;
import com.qk.dm.datamodel.params.dto.ModelReverseBaseDTO;
import com.qk.dm.datamodel.params.vo.ModelPhysicalTableVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T17:39:39+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class ModelPhysicalTableMapperImpl implements ModelPhysicalTableMapper {

    @Override
    public ModelPhysicalTable of(ModelPhysicalTableDTO modelPhysicalTableDTO) {
        if ( modelPhysicalTableDTO == null ) {
            return null;
        }

        ModelPhysicalTable modelPhysicalTable = new ModelPhysicalTable();

        modelPhysicalTable.setId( modelPhysicalTableDTO.getId() );
        modelPhysicalTable.setTableName( modelPhysicalTableDTO.getTableName() );
        modelPhysicalTable.setTheme( modelPhysicalTableDTO.getTheme() );
        modelPhysicalTable.setDataConnection( modelPhysicalTableDTO.getDataConnection() );
        modelPhysicalTable.setDataSourceName( modelPhysicalTableDTO.getDataSourceName() );
        modelPhysicalTable.setDataSourceId( modelPhysicalTableDTO.getDataSourceId() );
        modelPhysicalTable.setDataBaseName( modelPhysicalTableDTO.getDataBaseName() );
        modelPhysicalTable.setDescription( modelPhysicalTableDTO.getDescription() );
        modelPhysicalTable.setStatus( modelPhysicalTableDTO.getStatus() );
        modelPhysicalTable.setGmtCreate( modelPhysicalTableDTO.getGmtCreate() );
        modelPhysicalTable.setGmtModified( modelPhysicalTableDTO.getGmtModified() );
        modelPhysicalTable.setTableType( modelPhysicalTableDTO.getTableType() );
        modelPhysicalTable.setResponsibleBy( modelPhysicalTableDTO.getResponsibleBy() );
        modelPhysicalTable.setDataFormat( modelPhysicalTableDTO.getDataFormat() );
        modelPhysicalTable.setThemeId( modelPhysicalTableDTO.getThemeId() );
        modelPhysicalTable.setModelId( modelPhysicalTableDTO.getModelId() );
        modelPhysicalTable.setHftsRoute( modelPhysicalTableDTO.getHftsRoute() );
        modelPhysicalTable.setSyncStatus( modelPhysicalTableDTO.getSyncStatus() );

        return modelPhysicalTable;
    }

    @Override
    public ModelPhysicalTableVO of(ModelPhysicalTable modelPhysicalTable) {
        if ( modelPhysicalTable == null ) {
            return null;
        }

        ModelPhysicalTableVO modelPhysicalTableVO = new ModelPhysicalTableVO();

        modelPhysicalTableVO.setId( modelPhysicalTable.getId() );
        modelPhysicalTableVO.setTableName( modelPhysicalTable.getTableName() );
        modelPhysicalTableVO.setTheme( modelPhysicalTable.getTheme() );
        modelPhysicalTableVO.setDataConnection( modelPhysicalTable.getDataConnection() );
        modelPhysicalTableVO.setDataBaseName( modelPhysicalTable.getDataBaseName() );
        modelPhysicalTableVO.setDataSourceName( modelPhysicalTable.getDataSourceName() );
        modelPhysicalTableVO.setDataSourceId( modelPhysicalTable.getDataSourceId() );
        modelPhysicalTableVO.setDescription( modelPhysicalTable.getDescription() );
        modelPhysicalTableVO.setStatus( modelPhysicalTable.getStatus() );
        modelPhysicalTableVO.setGmtCreate( modelPhysicalTable.getGmtCreate() );
        modelPhysicalTableVO.setGmtModified( modelPhysicalTable.getGmtModified() );
        modelPhysicalTableVO.setTableType( modelPhysicalTable.getTableType() );
        modelPhysicalTableVO.setResponsibleBy( modelPhysicalTable.getResponsibleBy() );
        modelPhysicalTableVO.setDataFormat( modelPhysicalTable.getDataFormat() );
        modelPhysicalTableVO.setThemeId( modelPhysicalTable.getThemeId() );
        modelPhysicalTableVO.setModelId( modelPhysicalTable.getModelId() );
        modelPhysicalTableVO.setHftsRoute( modelPhysicalTable.getHftsRoute() );
        modelPhysicalTableVO.setSyncStatus( modelPhysicalTable.getSyncStatus() );

        return modelPhysicalTableVO;
    }

    @Override
    public List<ModelPhysicalTableVO> of(List<ModelPhysicalTable> modelPhysicalTableList) {
        if ( modelPhysicalTableList == null ) {
            return null;
        }

        List<ModelPhysicalTableVO> list = new ArrayList<ModelPhysicalTableVO>( modelPhysicalTableList.size() );
        for ( ModelPhysicalTable modelPhysicalTable : modelPhysicalTableList ) {
            list.add( of( modelPhysicalTable ) );
        }

        return list;
    }

    @Override
    public void from(ModelPhysicalTableDTO modelPhysicalTableDTO, ModelPhysicalTable modelPhysicalTable) {
        if ( modelPhysicalTableDTO == null ) {
            return;
        }

        if ( modelPhysicalTableDTO.getId() != null ) {
            modelPhysicalTable.setId( modelPhysicalTableDTO.getId() );
        }
        if ( modelPhysicalTableDTO.getTableName() != null ) {
            modelPhysicalTable.setTableName( modelPhysicalTableDTO.getTableName() );
        }
        if ( modelPhysicalTableDTO.getTheme() != null ) {
            modelPhysicalTable.setTheme( modelPhysicalTableDTO.getTheme() );
        }
        if ( modelPhysicalTableDTO.getDataConnection() != null ) {
            modelPhysicalTable.setDataConnection( modelPhysicalTableDTO.getDataConnection() );
        }
        if ( modelPhysicalTableDTO.getDataSourceName() != null ) {
            modelPhysicalTable.setDataSourceName( modelPhysicalTableDTO.getDataSourceName() );
        }
        if ( modelPhysicalTableDTO.getDataSourceId() != null ) {
            modelPhysicalTable.setDataSourceId( modelPhysicalTableDTO.getDataSourceId() );
        }
        if ( modelPhysicalTableDTO.getDataBaseName() != null ) {
            modelPhysicalTable.setDataBaseName( modelPhysicalTableDTO.getDataBaseName() );
        }
        if ( modelPhysicalTableDTO.getDescription() != null ) {
            modelPhysicalTable.setDescription( modelPhysicalTableDTO.getDescription() );
        }
        if ( modelPhysicalTableDTO.getStatus() != null ) {
            modelPhysicalTable.setStatus( modelPhysicalTableDTO.getStatus() );
        }
        if ( modelPhysicalTableDTO.getGmtCreate() != null ) {
            modelPhysicalTable.setGmtCreate( modelPhysicalTableDTO.getGmtCreate() );
        }
        if ( modelPhysicalTableDTO.getGmtModified() != null ) {
            modelPhysicalTable.setGmtModified( modelPhysicalTableDTO.getGmtModified() );
        }
        if ( modelPhysicalTableDTO.getTableType() != null ) {
            modelPhysicalTable.setTableType( modelPhysicalTableDTO.getTableType() );
        }
        if ( modelPhysicalTableDTO.getResponsibleBy() != null ) {
            modelPhysicalTable.setResponsibleBy( modelPhysicalTableDTO.getResponsibleBy() );
        }
        if ( modelPhysicalTableDTO.getDataFormat() != null ) {
            modelPhysicalTable.setDataFormat( modelPhysicalTableDTO.getDataFormat() );
        }
        if ( modelPhysicalTableDTO.getThemeId() != null ) {
            modelPhysicalTable.setThemeId( modelPhysicalTableDTO.getThemeId() );
        }
        if ( modelPhysicalTableDTO.getModelId() != null ) {
            modelPhysicalTable.setModelId( modelPhysicalTableDTO.getModelId() );
        }
        if ( modelPhysicalTableDTO.getHftsRoute() != null ) {
            modelPhysicalTable.setHftsRoute( modelPhysicalTableDTO.getHftsRoute() );
        }
        if ( modelPhysicalTableDTO.getSyncStatus() != null ) {
            modelPhysicalTable.setSyncStatus( modelPhysicalTableDTO.getSyncStatus() );
        }
    }

    @Override
    public void copy(ModelReverseBaseDTO modelReverseBaseDTO, ModelPhysicalTable modelPhysicalTable) {
        if ( modelReverseBaseDTO == null ) {
            return;
        }

        if ( modelReverseBaseDTO.getTheme() != null ) {
            modelPhysicalTable.setTheme( modelReverseBaseDTO.getTheme() );
        }
        if ( modelReverseBaseDTO.getDataConnection() != null ) {
            modelPhysicalTable.setDataConnection( modelReverseBaseDTO.getDataConnection() );
        }
        if ( modelReverseBaseDTO.getDataSourceName() != null ) {
            modelPhysicalTable.setDataSourceName( modelReverseBaseDTO.getDataSourceName() );
        }
        if ( modelReverseBaseDTO.getDataSourceId() != null ) {
            modelPhysicalTable.setDataSourceId( modelReverseBaseDTO.getDataSourceId() );
        }
        if ( modelReverseBaseDTO.getDataBaseName() != null ) {
            modelPhysicalTable.setDataBaseName( modelReverseBaseDTO.getDataBaseName() );
        }
        if ( modelReverseBaseDTO.getThemeId() != null ) {
            modelPhysicalTable.setThemeId( String.valueOf( modelReverseBaseDTO.getThemeId() ) );
        }
        if ( modelReverseBaseDTO.getModelId() != null ) {
            modelPhysicalTable.setModelId( modelReverseBaseDTO.getModelId() );
        }
    }
}
