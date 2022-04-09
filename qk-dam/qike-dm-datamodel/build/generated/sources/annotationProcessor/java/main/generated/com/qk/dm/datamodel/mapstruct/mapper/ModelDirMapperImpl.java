package com.qk.dm.datamodel.mapstruct.mapper;

import com.qk.dam.entity.DataStandardInfoVO;
import com.qk.dam.entity.DataStandardInfoVO.DataStandardInfoVOBuilder;
import com.qk.dam.entity.DataStandardTreeVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T17:39:39+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class ModelDirMapperImpl implements ModelDirMapper {

    @Override
    public DataStandardInfoVO of(DataStandardTreeVO dataStandardTreeVO) {
        if ( dataStandardTreeVO == null ) {
            return null;
        }

        DataStandardInfoVOBuilder dataStandardInfoVO = DataStandardInfoVO.builder();

        dataStandardInfoVO.id( dataStandardTreeVO.getId() );
        dataStandardInfoVO.dirId( dataStandardTreeVO.getDirDsdId() );
        dataStandardInfoVO.title( dataStandardTreeVO.getDirDsdName() );
        dataStandardInfoVO.value( dataStandardTreeVO.getDirDsdId() );
        dataStandardInfoVO.parentId( dataStandardTreeVO.getParentId() );
        dataStandardInfoVO.children( list( dataStandardTreeVO.getChildren() ) );

        return dataStandardInfoVO.build();
    }

    @Override
    public List<DataStandardInfoVO> list(List<DataStandardTreeVO> tree) {
        if ( tree == null ) {
            return null;
        }

        List<DataStandardInfoVO> list = new ArrayList<DataStandardInfoVO>( tree.size() );
        for ( DataStandardTreeVO dataStandardTreeVO : tree ) {
            list.add( of( dataStandardTreeVO ) );
        }

        return list;
    }
}
