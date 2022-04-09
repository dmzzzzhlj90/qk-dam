package com.qk.dm.datamodel.mapstruct.mapper;

import com.qk.dm.datamodel.entity.ModelSummary;
import com.qk.dm.datamodel.params.dto.ModelSummaryDTO;
import com.qk.dm.datamodel.params.vo.ModelSummaryVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T20:19:47+0800",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.9.1.jar, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class ModelSummaryMapperImpl implements ModelSummaryMapper {

    @Override
    public ModelSummary of(ModelSummaryDTO modelSummaryDTO) {
        if ( modelSummaryDTO == null ) {
            return null;
        }

        ModelSummary modelSummary = new ModelSummary();

        modelSummary.setId( modelSummaryDTO.getId() );
        modelSummary.setThemeId( modelSummaryDTO.getThemeId() );
        modelSummary.setThemeName( modelSummaryDTO.getThemeName() );
        modelSummary.setTableName( modelSummaryDTO.getTableName() );
        modelSummary.setDimId( modelSummaryDTO.getDimId() );
        modelSummary.setDimName( modelSummaryDTO.getDimName() );
        modelSummary.setDataConnection( modelSummaryDTO.getDataConnection() );
        modelSummary.setDatabaseName( modelSummaryDTO.getDatabaseName() );
        modelSummary.setDescription( modelSummaryDTO.getDescription() );

        return modelSummary;
    }

    @Override
    public ModelSummaryVO of(ModelSummary modelSummary) {
        if ( modelSummary == null ) {
            return null;
        }

        ModelSummaryVO modelSummaryVO = new ModelSummaryVO();

        modelSummaryVO.setId( modelSummary.getId() );
        modelSummaryVO.setThemeId( modelSummary.getThemeId() );
        modelSummaryVO.setThemeName( modelSummary.getThemeName() );
        modelSummaryVO.setTableName( modelSummary.getTableName() );
        modelSummaryVO.setDimId( modelSummary.getDimId() );
        modelSummaryVO.setDimName( modelSummary.getDimName() );
        modelSummaryVO.setDataConnection( modelSummary.getDataConnection() );
        modelSummaryVO.setDatabaseName( modelSummary.getDatabaseName() );
        modelSummaryVO.setDescription( modelSummary.getDescription() );
        modelSummaryVO.setGmtCreate( modelSummary.getGmtCreate() );
        modelSummaryVO.setGmtModified( modelSummary.getGmtModified() );

        return modelSummaryVO;
    }

    @Override
    public List<ModelSummaryVO> of(List<ModelSummary> modelSummaryList) {
        if ( modelSummaryList == null ) {
            return null;
        }

        List<ModelSummaryVO> list = new ArrayList<ModelSummaryVO>( modelSummaryList.size() );
        for ( ModelSummary modelSummary : modelSummaryList ) {
            list.add( of( modelSummary ) );
        }

        return list;
    }

    @Override
    public void from(ModelSummaryDTO modelSummaryDTO, ModelSummary modelSummary) {
        if ( modelSummaryDTO == null ) {
            return;
        }

        if ( modelSummaryDTO.getId() != null ) {
            modelSummary.setId( modelSummaryDTO.getId() );
        }
        if ( modelSummaryDTO.getThemeId() != null ) {
            modelSummary.setThemeId( modelSummaryDTO.getThemeId() );
        }
        if ( modelSummaryDTO.getThemeName() != null ) {
            modelSummary.setThemeName( modelSummaryDTO.getThemeName() );
        }
        if ( modelSummaryDTO.getTableName() != null ) {
            modelSummary.setTableName( modelSummaryDTO.getTableName() );
        }
        if ( modelSummaryDTO.getDimId() != null ) {
            modelSummary.setDimId( modelSummaryDTO.getDimId() );
        }
        if ( modelSummaryDTO.getDimName() != null ) {
            modelSummary.setDimName( modelSummaryDTO.getDimName() );
        }
        if ( modelSummaryDTO.getDataConnection() != null ) {
            modelSummary.setDataConnection( modelSummaryDTO.getDataConnection() );
        }
        if ( modelSummaryDTO.getDatabaseName() != null ) {
            modelSummary.setDatabaseName( modelSummaryDTO.getDatabaseName() );
        }
        if ( modelSummaryDTO.getDescription() != null ) {
            modelSummary.setDescription( modelSummaryDTO.getDescription() );
        }
    }
}
