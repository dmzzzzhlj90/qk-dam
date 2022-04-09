package com.qk.dm.datamodel.mapstruct.mapper;

import com.qk.dm.datamodel.entity.ModelDim;
import com.qk.dm.datamodel.params.dto.ModelDimDTO;
import com.qk.dm.datamodel.params.dto.ModelDimTableDTO;
import com.qk.dm.datamodel.params.vo.ModelDimVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T17:39:39+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class ModelDimMapperImpl implements ModelDimMapper {

    @Override
    public ModelDim of(ModelDimDTO modelDimDTO) {
        if ( modelDimDTO == null ) {
            return null;
        }

        ModelDim modelDim = new ModelDim();

        modelDim.setId( modelDimDTO.getId() );
        modelDim.setThemeId( modelDimDTO.getThemeId() );
        modelDim.setThemeName( modelDimDTO.getThemeName() );
        modelDim.setDimName( modelDimDTO.getDimName() );
        modelDim.setDimCode( modelDimDTO.getDimCode() );
        modelDim.setDimType( modelDimDTO.getDimType() );
        modelDim.setDescription( modelDimDTO.getDescription() );
        modelDim.setStatus( modelDimDTO.getStatus() );
        modelDim.setConnectionType( modelDimDTO.getConnectionType() );
        modelDim.setDataConnection( modelDimDTO.getDataConnection() );
        modelDim.setDatabaseName( modelDimDTO.getDatabaseName() );

        return modelDim;
    }

    @Override
    public ModelDimVO of(ModelDim modelDim) {
        if ( modelDim == null ) {
            return null;
        }

        ModelDimVO modelDimVO = new ModelDimVO();

        modelDimVO.setId( modelDim.getId() );
        modelDimVO.setThemeId( modelDim.getThemeId() );
        modelDimVO.setThemeName( modelDim.getThemeName() );
        modelDimVO.setDimName( modelDim.getDimName() );
        modelDimVO.setDimCode( modelDim.getDimCode() );
        modelDimVO.setDimType( modelDim.getDimType() );
        modelDimVO.setDescription( modelDim.getDescription() );
        modelDimVO.setStatus( modelDim.getStatus() );
        modelDimVO.setConnectionType( modelDim.getConnectionType() );
        modelDimVO.setDataConnection( modelDim.getDataConnection() );
        modelDimVO.setDatabaseName( modelDim.getDatabaseName() );
        modelDimVO.setGmtCreate( modelDim.getGmtCreate() );
        modelDimVO.setGmtModified( modelDim.getGmtModified() );

        return modelDimVO;
    }

    @Override
    public ModelDimTableDTO ofDimTable(ModelDimDTO modelDimDTO) {
        if ( modelDimDTO == null ) {
            return null;
        }

        ModelDimTableDTO modelDimTableDTO = new ModelDimTableDTO();

        modelDimTableDTO.setPagination( modelDimDTO.getPagination() );
        modelDimTableDTO.setId( modelDimDTO.getId() );
        modelDimTableDTO.setThemeId( modelDimDTO.getThemeId() );
        modelDimTableDTO.setThemeName( modelDimDTO.getThemeName() );
        modelDimTableDTO.setDimName( modelDimDTO.getDimName() );
        modelDimTableDTO.setDimCode( modelDimDTO.getDimCode() );
        modelDimTableDTO.setDimType( modelDimDTO.getDimType() );
        modelDimTableDTO.setDescription( modelDimDTO.getDescription() );
        modelDimTableDTO.setStatus( modelDimDTO.getStatus() );
        modelDimTableDTO.setConnectionType( modelDimDTO.getConnectionType() );
        modelDimTableDTO.setDataConnection( modelDimDTO.getDataConnection() );
        modelDimTableDTO.setDatabaseName( modelDimDTO.getDatabaseName() );

        return modelDimTableDTO;
    }

    @Override
    public List<ModelDimVO> of(List<ModelDim> modelDimList) {
        if ( modelDimList == null ) {
            return null;
        }

        List<ModelDimVO> list = new ArrayList<ModelDimVO>( modelDimList.size() );
        for ( ModelDim modelDim : modelDimList ) {
            list.add( of( modelDim ) );
        }

        return list;
    }

    @Override
    public void from(ModelDimDTO modelDimDTO, ModelDim modelDim) {
        if ( modelDimDTO == null ) {
            return;
        }

        if ( modelDimDTO.getId() != null ) {
            modelDim.setId( modelDimDTO.getId() );
        }
        if ( modelDimDTO.getThemeId() != null ) {
            modelDim.setThemeId( modelDimDTO.getThemeId() );
        }
        if ( modelDimDTO.getThemeName() != null ) {
            modelDim.setThemeName( modelDimDTO.getThemeName() );
        }
        if ( modelDimDTO.getDimName() != null ) {
            modelDim.setDimName( modelDimDTO.getDimName() );
        }
        if ( modelDimDTO.getDimCode() != null ) {
            modelDim.setDimCode( modelDimDTO.getDimCode() );
        }
        if ( modelDimDTO.getDimType() != null ) {
            modelDim.setDimType( modelDimDTO.getDimType() );
        }
        if ( modelDimDTO.getDescription() != null ) {
            modelDim.setDescription( modelDimDTO.getDescription() );
        }
        if ( modelDimDTO.getStatus() != null ) {
            modelDim.setStatus( modelDimDTO.getStatus() );
        }
        if ( modelDimDTO.getConnectionType() != null ) {
            modelDim.setConnectionType( modelDimDTO.getConnectionType() );
        }
        if ( modelDimDTO.getDataConnection() != null ) {
            modelDim.setDataConnection( modelDimDTO.getDataConnection() );
        }
        if ( modelDimDTO.getDatabaseName() != null ) {
            modelDim.setDatabaseName( modelDimDTO.getDatabaseName() );
        }
    }
}
