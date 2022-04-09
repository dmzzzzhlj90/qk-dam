package com.qk.dm.indicator.mapstruct.mapper;

import com.qk.dm.indicator.entity.IdcFunction;
import com.qk.dm.indicator.params.dto.IdcFunctionDTO;
import com.qk.dm.indicator.params.vo.IdcFunctionVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T20:19:47+0800",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.9.1.jar, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class IdcFunctionMapperImpl implements IdcFunctionMapper {

    @Override
    public void useIdcFunction(IdcFunctionDTO idcFunctionDTO, IdcFunction idcFunction) {
        if ( idcFunctionDTO == null ) {
            return;
        }

        if ( idcFunctionDTO.getName() != null ) {
            idcFunction.setName( idcFunctionDTO.getName() );
        }
        if ( idcFunctionDTO.getFunction() != null ) {
            idcFunction.setFunction( idcFunctionDTO.getFunction() );
        }
        if ( idcFunctionDTO.getEngine() != null ) {
            idcFunction.setEngine( idcFunctionDTO.getEngine() );
        }
        if ( idcFunctionDTO.getType() != null ) {
            idcFunction.setType( idcFunctionDTO.getType() );
        }
        if ( idcFunctionDTO.getTypeName() != null ) {
            idcFunction.setTypeName( idcFunctionDTO.getTypeName() );
        }
        if ( idcFunctionDTO.getParentId() != null ) {
            idcFunction.setParentId( idcFunctionDTO.getParentId() );
        }
    }

    @Override
    public IdcFunction useIdcFunction(IdcFunctionDTO idcFunctionDTO) {
        if ( idcFunctionDTO == null ) {
            return null;
        }

        IdcFunction idcFunction = new IdcFunction();

        idcFunction.setName( idcFunctionDTO.getName() );
        idcFunction.setFunction( idcFunctionDTO.getFunction() );
        idcFunction.setEngine( idcFunctionDTO.getEngine() );
        idcFunction.setType( idcFunctionDTO.getType() );
        idcFunction.setTypeName( idcFunctionDTO.getTypeName() );
        idcFunction.setParentId( idcFunctionDTO.getParentId() );

        return idcFunction;
    }

    @Override
    public IdcFunctionVO useIdcFunctionVO(IdcFunction idcFunction) {
        if ( idcFunction == null ) {
            return null;
        }

        IdcFunctionVO idcFunctionVO = new IdcFunctionVO();

        idcFunctionVO.setId( idcFunction.getId() );
        idcFunctionVO.setName( idcFunction.getName() );
        idcFunctionVO.setFunction( idcFunction.getFunction() );
        idcFunctionVO.setEngine( idcFunction.getEngine() );
        idcFunctionVO.setTypeName( idcFunction.getTypeName() );

        return idcFunctionVO;
    }

    @Override
    public List<IdcFunctionVO> useIdcFunctionVO(List<IdcFunction> idcFunction) {
        if ( idcFunction == null ) {
            return null;
        }

        List<IdcFunctionVO> list = new ArrayList<IdcFunctionVO>( idcFunction.size() );
        for ( IdcFunction idcFunction1 : idcFunction ) {
            list.add( useIdcFunctionVO( idcFunction1 ) );
        }

        return list;
    }
}
