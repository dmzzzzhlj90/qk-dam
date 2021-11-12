package com.qk.dm.metadata.mapstruct.mapper;

import com.qk.dm.metadata.vo.MtdColumnVO;
import com.qk.dm.metadata.vo.MtdCommonDetailVO;
import com.qk.dm.metadata.vo.MtdDbDetailVO;
import com.qk.dm.metadata.vo.MtdTableDetailVO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-10T15:00:28+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class MtdCommonDetailMapperImpl implements MtdCommonDetailMapper {

    @Override
    public MtdCommonDetailVO userMtdCommonDetail(MtdDbDetailVO mtdDbDetailVO) {
        if ( mtdDbDetailVO == null ) {
            return null;
        }

        MtdCommonDetailVO mtdCommonDetailVO = new MtdCommonDetailVO();

        mtdCommonDetailVO.setOwner( mtdDbDetailVO.getOwner() );
        mtdCommonDetailVO.setTypeName( mtdDbDetailVO.getTypeName() );
        mtdCommonDetailVO.setComment( mtdDbDetailVO.getComment() );
        mtdCommonDetailVO.setQualifiedName( mtdDbDetailVO.getQualifiedName() );
        mtdCommonDetailVO.setDescription( mtdDbDetailVO.getDescription() );
        mtdCommonDetailVO.setDataType( mtdDbDetailVO.getDataType() );
        mtdCommonDetailVO.setLabels( mtdDbDetailVO.getLabels() );
        mtdCommonDetailVO.setClassification( mtdDbDetailVO.getClassification() );
        mtdCommonDetailVO.setCreateTime( mtdDbDetailVO.getCreateTime() );

        return mtdCommonDetailVO;
    }

    @Override
    public MtdCommonDetailVO userMtdCommonDetail(MtdTableDetailVO mtdTableDetailVO) {
        if ( mtdTableDetailVO == null ) {
            return null;
        }

        MtdCommonDetailVO mtdCommonDetailVO = new MtdCommonDetailVO();

        mtdCommonDetailVO.setOwner( mtdTableDetailVO.getOwner() );
        mtdCommonDetailVO.setTypeName( mtdTableDetailVO.getTypeName() );
        mtdCommonDetailVO.setTableRows( mtdTableDetailVO.getTableRows() );
        mtdCommonDetailVO.setDataLength( mtdTableDetailVO.getDataLength() );
        mtdCommonDetailVO.setIndexLength( mtdTableDetailVO.getIndexLength() );
        mtdCommonDetailVO.setComment( mtdTableDetailVO.getComment() );
        mtdCommonDetailVO.setQualifiedName( mtdTableDetailVO.getQualifiedName() );
        mtdCommonDetailVO.setDescription( mtdTableDetailVO.getDescription() );
        mtdCommonDetailVO.setLabels( mtdTableDetailVO.getLabels() );
        mtdCommonDetailVO.setClassification( mtdTableDetailVO.getClassification() );
        mtdCommonDetailVO.setCreateTime( mtdTableDetailVO.getCreateTime() );
        mtdCommonDetailVO.setDb( mtdTableDetailVO.getDb() );

        return mtdCommonDetailVO;
    }

    @Override
    public MtdCommonDetailVO userMtdCommonDetail(MtdColumnVO mtdColumnVO) {
        if ( mtdColumnVO == null ) {
            return null;
        }

        MtdCommonDetailVO mtdCommonDetailVO = new MtdCommonDetailVO();

        mtdCommonDetailVO.setOwner( mtdColumnVO.getOwner() );
        mtdCommonDetailVO.setTypeName( mtdColumnVO.getTypeName() );
        mtdCommonDetailVO.setComment( mtdColumnVO.getComment() );
        mtdCommonDetailVO.setQualifiedName( mtdColumnVO.getQualifiedName() );
        mtdCommonDetailVO.setDescription( mtdColumnVO.getDescription() );
        mtdCommonDetailVO.setDataType( mtdColumnVO.getDataType() );
        mtdCommonDetailVO.setIsPrimaryKey( mtdColumnVO.getIsPrimaryKey() );
        mtdCommonDetailVO.setDefaultValue( mtdColumnVO.getDefaultValue() );
        mtdCommonDetailVO.setLabels( mtdColumnVO.getLabels() );
        mtdCommonDetailVO.setClassification( mtdColumnVO.getClassification() );
        mtdCommonDetailVO.setCreateTime( mtdColumnVO.getCreateTime() );

        return mtdCommonDetailVO;
    }
}
