package com.qk.dm.datastandards.mapstruct.mapper;

import com.qk.dm.datastandards.entity.DsdCodeDir;
import com.qk.dm.datastandards.vo.DataStandardCodeTreeVO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-07-12T02:36:42+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.5 (Oracle Corporation)"
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

        return dataStandardCodeTreeVO;
    }
}
