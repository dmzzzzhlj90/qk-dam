package com.qk.dm.datamodel.mapstruct.mapper;

import com.qk.dm.datamodel.entity.ModelDimTable;
import com.qk.dm.datamodel.params.dto.ModelDimTableDTO;
import com.qk.dm.datamodel.params.vo.ModelDimTableVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T17:39:39+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class ModelDimTableMapperImpl implements ModelDimTableMapper {

    @Override
    public ModelDimTable of(ModelDimTableDTO modelDimTableDTO) {
        if ( modelDimTableDTO == null ) {
            return null;
        }

        ModelDimTable modelDimTable = new ModelDimTable();

        modelDimTable.setId( modelDimTableDTO.getId() );
        modelDimTable.setThemeId( modelDimTableDTO.getThemeId() );
        modelDimTable.setThemeName( modelDimTableDTO.getThemeName() );
        modelDimTable.setDimName( modelDimTableDTO.getDimName() );
        modelDimTable.setDimCode( modelDimTableDTO.getDimCode() );
        modelDimTable.setDimType( modelDimTableDTO.getDimType() );
        modelDimTable.setDescription( modelDimTableDTO.getDescription() );
        modelDimTable.setStatus( modelDimTableDTO.getStatus() );
        modelDimTable.setConnectionType( modelDimTableDTO.getConnectionType() );
        modelDimTable.setDataConnection( modelDimTableDTO.getDataConnection() );
        modelDimTable.setDatabaseName( modelDimTableDTO.getDatabaseName() );
        modelDimTable.setModelDimId( modelDimTableDTO.getModelDimId() );

        return modelDimTable;
    }

    @Override
    public ModelDimTableVO of(ModelDimTable modelDimTable) {
        if ( modelDimTable == null ) {
            return null;
        }

        ModelDimTableVO modelDimTableVO = new ModelDimTableVO();

        modelDimTableVO.setId( modelDimTable.getId() );
        modelDimTableVO.setThemeId( modelDimTable.getThemeId() );
        modelDimTableVO.setThemeName( modelDimTable.getThemeName() );
        modelDimTableVO.setDimName( modelDimTable.getDimName() );
        modelDimTableVO.setDimCode( modelDimTable.getDimCode() );
        modelDimTableVO.setDimType( modelDimTable.getDimType() );
        modelDimTableVO.setDescription( modelDimTable.getDescription() );
        modelDimTableVO.setStatus( modelDimTable.getStatus() );
        modelDimTableVO.setConnectionType( modelDimTable.getConnectionType() );
        modelDimTableVO.setDataConnection( modelDimTable.getDataConnection() );
        modelDimTableVO.setDatabaseName( modelDimTable.getDatabaseName() );
        modelDimTableVO.setGmtCreate( modelDimTable.getGmtCreate() );
        modelDimTableVO.setGmtModified( modelDimTable.getGmtModified() );

        return modelDimTableVO;
    }

    @Override
    public List<ModelDimTableVO> of(List<ModelDimTable> modelDimTableList) {
        if ( modelDimTableList == null ) {
            return null;
        }

        List<ModelDimTableVO> list = new ArrayList<ModelDimTableVO>( modelDimTableList.size() );
        for ( ModelDimTable modelDimTable : modelDimTableList ) {
            list.add( of( modelDimTable ) );
        }

        return list;
    }

    @Override
    public void from(ModelDimTableDTO modelDimTableDTO, ModelDimTable modelDimTable) {
        if ( modelDimTableDTO == null ) {
            return;
        }

        if ( modelDimTableDTO.getId() != null ) {
            modelDimTable.setId( modelDimTableDTO.getId() );
        }
        if ( modelDimTableDTO.getThemeId() != null ) {
            modelDimTable.setThemeId( modelDimTableDTO.getThemeId() );
        }
        if ( modelDimTableDTO.getThemeName() != null ) {
            modelDimTable.setThemeName( modelDimTableDTO.getThemeName() );
        }
        if ( modelDimTableDTO.getDimName() != null ) {
            modelDimTable.setDimName( modelDimTableDTO.getDimName() );
        }
        if ( modelDimTableDTO.getDimCode() != null ) {
            modelDimTable.setDimCode( modelDimTableDTO.getDimCode() );
        }
        if ( modelDimTableDTO.getDimType() != null ) {
            modelDimTable.setDimType( modelDimTableDTO.getDimType() );
        }
        if ( modelDimTableDTO.getDescription() != null ) {
            modelDimTable.setDescription( modelDimTableDTO.getDescription() );
        }
        if ( modelDimTableDTO.getStatus() != null ) {
            modelDimTable.setStatus( modelDimTableDTO.getStatus() );
        }
        if ( modelDimTableDTO.getConnectionType() != null ) {
            modelDimTable.setConnectionType( modelDimTableDTO.getConnectionType() );
        }
        if ( modelDimTableDTO.getDataConnection() != null ) {
            modelDimTable.setDataConnection( modelDimTableDTO.getDataConnection() );
        }
        if ( modelDimTableDTO.getDatabaseName() != null ) {
            modelDimTable.setDatabaseName( modelDimTableDTO.getDatabaseName() );
        }
        if ( modelDimTableDTO.getModelDimId() != null ) {
            modelDimTable.setModelDimId( modelDimTableDTO.getModelDimId() );
        }
    }
}
