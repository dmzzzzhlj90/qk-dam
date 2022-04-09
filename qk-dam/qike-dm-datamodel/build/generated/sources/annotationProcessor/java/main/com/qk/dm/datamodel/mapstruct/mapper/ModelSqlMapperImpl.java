package com.qk.dm.datamodel.mapstruct.mapper;

import com.qk.dm.datamodel.entity.ModelSql;
import com.qk.dm.datamodel.params.dto.ModelSqlDTO;
import com.qk.dm.datamodel.params.vo.ModelSqlVO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T20:19:47+0800",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.9.1.jar, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class ModelSqlMapperImpl implements ModelSqlMapper {

    @Override
    public ModelSql of(ModelSqlDTO modelSqlDTO) {
        if ( modelSqlDTO == null ) {
            return null;
        }

        ModelSql modelSql = new ModelSql();

        modelSql.setTableId( modelSqlDTO.getTableId() );
        modelSql.setType( modelSqlDTO.getType() );
        modelSql.setSqlSentence( modelSqlDTO.getSqlSentence() );

        return modelSql;
    }

    @Override
    public ModelSqlVO of(ModelSql modelSql) {
        if ( modelSql == null ) {
            return null;
        }

        ModelSqlVO modelSqlVO = new ModelSqlVO();

        modelSqlVO.setTableId( modelSql.getTableId() );
        modelSqlVO.setType( modelSql.getType() );
        modelSqlVO.setSqlSentence( modelSql.getSqlSentence() );

        return modelSqlVO;
    }

    @Override
    public void from(ModelSqlDTO modelSqlDTO, ModelSql modelSql) {
        if ( modelSqlDTO == null ) {
            return;
        }

        if ( modelSqlDTO.getTableId() != null ) {
            modelSql.setTableId( modelSqlDTO.getTableId() );
        }
        if ( modelSqlDTO.getType() != null ) {
            modelSql.setType( modelSqlDTO.getType() );
        }
        if ( modelSqlDTO.getSqlSentence() != null ) {
            modelSql.setSqlSentence( modelSqlDTO.getSqlSentence() );
        }
    }
}
