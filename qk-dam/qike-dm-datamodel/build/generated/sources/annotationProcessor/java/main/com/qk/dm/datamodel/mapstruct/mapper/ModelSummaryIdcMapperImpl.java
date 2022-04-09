package com.qk.dm.datamodel.mapstruct.mapper;

import com.qk.dm.datamodel.entity.ModelSummaryIdc;
import com.qk.dm.datamodel.params.dto.ModelSummaryIdcDTO;
import com.qk.dm.datamodel.params.vo.ModelSummaryIdcVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T20:19:48+0800",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.9.1.jar, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class ModelSummaryIdcMapperImpl implements ModelSummaryIdcMapper {

    @Override
    public ModelSummaryIdc of(ModelSummaryIdcDTO modelSummaryIdcDTO) {
        if ( modelSummaryIdcDTO == null ) {
            return null;
        }

        ModelSummaryIdc modelSummaryIdc = new ModelSummaryIdc();

        modelSummaryIdc.setId( modelSummaryIdcDTO.getId() );
        modelSummaryIdc.setSummaryId( modelSummaryIdcDTO.getSummaryId() );
        modelSummaryIdc.setIndicatorsType( modelSummaryIdcDTO.getIndicatorsType() );
        modelSummaryIdc.setIndicatorsName( modelSummaryIdcDTO.getIndicatorsName() );
        modelSummaryIdc.setIndicatorsCode( modelSummaryIdcDTO.getIndicatorsCode() );
        modelSummaryIdc.setDataType( modelSummaryIdcDTO.getDataType() );
        modelSummaryIdc.setItsNull( modelSummaryIdcDTO.getItsNull() );
        modelSummaryIdc.setDescription( modelSummaryIdcDTO.getDescription() );

        return modelSummaryIdc;
    }

    @Override
    public List<ModelSummaryIdc> use(List<ModelSummaryIdcDTO> modelSummaryIdcDTOList) {
        if ( modelSummaryIdcDTOList == null ) {
            return null;
        }

        List<ModelSummaryIdc> list = new ArrayList<ModelSummaryIdc>( modelSummaryIdcDTOList.size() );
        for ( ModelSummaryIdcDTO modelSummaryIdcDTO : modelSummaryIdcDTOList ) {
            list.add( of( modelSummaryIdcDTO ) );
        }

        return list;
    }

    @Override
    public ModelSummaryIdcVO of(ModelSummaryIdc modelSummaryIdc) {
        if ( modelSummaryIdc == null ) {
            return null;
        }

        ModelSummaryIdcVO modelSummaryIdcVO = new ModelSummaryIdcVO();

        modelSummaryIdcVO.setSummaryId( modelSummaryIdc.getSummaryId() );
        modelSummaryIdcVO.setIndicatorsType( modelSummaryIdc.getIndicatorsType() );
        modelSummaryIdcVO.setIndicatorsName( modelSummaryIdc.getIndicatorsName() );
        modelSummaryIdcVO.setIndicatorsCode( modelSummaryIdc.getIndicatorsCode() );
        modelSummaryIdcVO.setDataType( modelSummaryIdc.getDataType() );
        modelSummaryIdcVO.setItsNull( modelSummaryIdc.getItsNull() );
        modelSummaryIdcVO.setDescription( modelSummaryIdc.getDescription() );
        modelSummaryIdcVO.setGmtCreate( modelSummaryIdc.getGmtCreate() );
        modelSummaryIdcVO.setGmtModified( modelSummaryIdc.getGmtModified() );

        return modelSummaryIdcVO;
    }

    @Override
    public List<ModelSummaryIdcVO> of(List<ModelSummaryIdc> modelSummaryIdcList) {
        if ( modelSummaryIdcList == null ) {
            return null;
        }

        List<ModelSummaryIdcVO> list = new ArrayList<ModelSummaryIdcVO>( modelSummaryIdcList.size() );
        for ( ModelSummaryIdc modelSummaryIdc : modelSummaryIdcList ) {
            list.add( of( modelSummaryIdc ) );
        }

        return list;
    }

    @Override
    public void from(ModelSummaryIdcDTO modelSummaryIdcDTO, ModelSummaryIdc modelSummaryIdc) {
        if ( modelSummaryIdcDTO == null ) {
            return;
        }

        if ( modelSummaryIdcDTO.getId() != null ) {
            modelSummaryIdc.setId( modelSummaryIdcDTO.getId() );
        }
        if ( modelSummaryIdcDTO.getSummaryId() != null ) {
            modelSummaryIdc.setSummaryId( modelSummaryIdcDTO.getSummaryId() );
        }
        if ( modelSummaryIdcDTO.getIndicatorsType() != null ) {
            modelSummaryIdc.setIndicatorsType( modelSummaryIdcDTO.getIndicatorsType() );
        }
        if ( modelSummaryIdcDTO.getIndicatorsName() != null ) {
            modelSummaryIdc.setIndicatorsName( modelSummaryIdcDTO.getIndicatorsName() );
        }
        if ( modelSummaryIdcDTO.getIndicatorsCode() != null ) {
            modelSummaryIdc.setIndicatorsCode( modelSummaryIdcDTO.getIndicatorsCode() );
        }
        if ( modelSummaryIdcDTO.getDataType() != null ) {
            modelSummaryIdc.setDataType( modelSummaryIdcDTO.getDataType() );
        }
        if ( modelSummaryIdcDTO.getItsNull() != null ) {
            modelSummaryIdc.setItsNull( modelSummaryIdcDTO.getItsNull() );
        }
        if ( modelSummaryIdcDTO.getDescription() != null ) {
            modelSummaryIdc.setDescription( modelSummaryIdcDTO.getDescription() );
        }
    }
}
