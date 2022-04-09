package com.qk.dm.datamodel.mapstruct.mapper;

import com.qk.dm.datamodel.entity.Model;
import com.qk.dm.datamodel.params.dto.ModelDTO;
import com.qk.dm.datamodel.params.vo.ModelVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T20:19:48+0800",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.9.1.jar, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class ModelMapperImpl implements ModelMapper {

    @Override
    public Model useModel(ModelDTO modelDTO) {
        if ( modelDTO == null ) {
            return null;
        }

        Model model = new Model();

        model.setModelName( modelDTO.getModelName() );
        model.setLayeredName( modelDTO.getLayeredName() );
        model.setDescription( modelDTO.getDescription() );
        model.setModelType( modelDTO.getModelType() );

        return model;
    }

    @Override
    public List<ModelVO> userModelVO(List<Model> modelList) {
        if ( modelList == null ) {
            return null;
        }

        List<ModelVO> list = new ArrayList<ModelVO>( modelList.size() );
        for ( Model model : modelList ) {
            list.add( modelToModelVO( model ) );
        }

        return list;
    }

    protected ModelVO modelToModelVO(Model model) {
        if ( model == null ) {
            return null;
        }

        ModelVO modelVO = new ModelVO();

        modelVO.setId( model.getId() );
        modelVO.setModelName( model.getModelName() );
        modelVO.setLayeredName( model.getLayeredName() );
        modelVO.setDescription( model.getDescription() );
        modelVO.setModelType( model.getModelType() );
        modelVO.setGmtCreate( model.getGmtCreate() );
        modelVO.setGmtModified( model.getGmtModified() );

        return modelVO;
    }
}
