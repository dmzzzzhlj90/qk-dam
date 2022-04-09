package com.qk.dm.datamodel.mapstruct.mapper;

import com.qk.dm.datamodel.entity.ModelFactTable;
import com.qk.dm.datamodel.params.dto.ModelFactTableDTO;
import com.qk.dm.datamodel.params.vo.ModelFactTableVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T17:39:39+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class ModelFactTableMapperImpl implements ModelFactTableMapper {

    @Override
    public ModelFactTable of(ModelFactTableDTO modelFactTableDTO) {
        if ( modelFactTableDTO == null ) {
            return null;
        }

        ModelFactTable modelFactTable = new ModelFactTable();

        modelFactTable.setId( modelFactTableDTO.getId() );
        modelFactTable.setThemeId( modelFactTableDTO.getThemeId() );
        modelFactTable.setThemeName( modelFactTableDTO.getThemeName() );
        modelFactTable.setFactName( modelFactTableDTO.getFactName() );
        modelFactTable.setDescription( modelFactTableDTO.getDescription() );
        modelFactTable.setStatus( modelFactTableDTO.getStatus() );
        modelFactTable.setConnectionType( modelFactTableDTO.getConnectionType() );
        modelFactTable.setDataConnection( modelFactTableDTO.getDataConnection() );
        modelFactTable.setDatabaseName( modelFactTableDTO.getDatabaseName() );

        return modelFactTable;
    }

    @Override
    public ModelFactTableVO of(ModelFactTable modelFactTable) {
        if ( modelFactTable == null ) {
            return null;
        }

        ModelFactTableVO modelFactTableVO = new ModelFactTableVO();

        modelFactTableVO.setId( modelFactTable.getId() );
        modelFactTableVO.setThemeId( modelFactTable.getThemeId() );
        modelFactTableVO.setThemeName( modelFactTable.getThemeName() );
        modelFactTableVO.setDescription( modelFactTable.getDescription() );
        modelFactTableVO.setStatus( modelFactTable.getStatus() );
        modelFactTableVO.setConnectionType( modelFactTable.getConnectionType() );
        modelFactTableVO.setDataConnection( modelFactTable.getDataConnection() );
        modelFactTableVO.setDatabaseName( modelFactTable.getDatabaseName() );
        modelFactTableVO.setGmtCreate( modelFactTable.getGmtCreate() );
        modelFactTableVO.setGmtModified( modelFactTable.getGmtModified() );

        return modelFactTableVO;
    }

    @Override
    public List<ModelFactTableVO> of(List<ModelFactTable> modelFactTableList) {
        if ( modelFactTableList == null ) {
            return null;
        }

        List<ModelFactTableVO> list = new ArrayList<ModelFactTableVO>( modelFactTableList.size() );
        for ( ModelFactTable modelFactTable : modelFactTableList ) {
            list.add( of( modelFactTable ) );
        }

        return list;
    }

    @Override
    public void from(ModelFactTableDTO modelFactTableDTO, ModelFactTable modelFactTable) {
        if ( modelFactTableDTO == null ) {
            return;
        }

        if ( modelFactTableDTO.getId() != null ) {
            modelFactTable.setId( modelFactTableDTO.getId() );
        }
        if ( modelFactTableDTO.getThemeId() != null ) {
            modelFactTable.setThemeId( modelFactTableDTO.getThemeId() );
        }
        if ( modelFactTableDTO.getThemeName() != null ) {
            modelFactTable.setThemeName( modelFactTableDTO.getThemeName() );
        }
        if ( modelFactTableDTO.getFactName() != null ) {
            modelFactTable.setFactName( modelFactTableDTO.getFactName() );
        }
        if ( modelFactTableDTO.getDescription() != null ) {
            modelFactTable.setDescription( modelFactTableDTO.getDescription() );
        }
        if ( modelFactTableDTO.getStatus() != null ) {
            modelFactTable.setStatus( modelFactTableDTO.getStatus() );
        }
        if ( modelFactTableDTO.getConnectionType() != null ) {
            modelFactTable.setConnectionType( modelFactTableDTO.getConnectionType() );
        }
        if ( modelFactTableDTO.getDataConnection() != null ) {
            modelFactTable.setDataConnection( modelFactTableDTO.getDataConnection() );
        }
        if ( modelFactTableDTO.getDatabaseName() != null ) {
            modelFactTable.setDatabaseName( modelFactTableDTO.getDatabaseName() );
        }
    }
}
