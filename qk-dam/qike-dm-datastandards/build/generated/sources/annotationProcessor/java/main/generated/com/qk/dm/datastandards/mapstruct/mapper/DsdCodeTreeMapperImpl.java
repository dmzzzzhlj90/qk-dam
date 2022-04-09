package com.qk.dm.datastandards.mapstruct.mapper;

import com.qk.dm.datastandards.entity.DsdCodeDir;
import com.qk.dm.datastandards.vo.DataStandardCodeTreeVO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T17:36:26+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class DsdCodeTreeMapperImpl implements DsdCodeTreeMapper {

    @Override
    public DataStandardCodeTreeVO useCodeTreeVO(DsdCodeDir dsdCodeDir) {
        if ( dsdCodeDir == null ) {
            return null;
        }

        DataStandardCodeTreeVO dataStandardCodeTreeVO = new DataStandardCodeTreeVO();

        dataStandardCodeTreeVO.setId( dsdCodeDir.getId() );
        dataStandardCodeTreeVO.setCodeDirId( dsdCodeDir.getCodeDirId() );
        dataStandardCodeTreeVO.setCodeDirName( dsdCodeDir.getCodeDirName() );
        dataStandardCodeTreeVO.setParentId( dsdCodeDir.getParentId() );
        dataStandardCodeTreeVO.setCodeDirLevel( dsdCodeDir.getCodeDirLevel() );

        return dataStandardCodeTreeVO;
    }
}
