package com.qk.dm.datastandards.mapstruct.mapper;

import com.qk.dm.datastandards.entity.DsdCodeDir;
import com.qk.dm.datastandards.vo.DsdCodeDirVO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T17:36:26+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class DsdDirCodeDirTreeMapperImpl implements DsdDirCodeDirTreeMapper {

    @Override
    public DsdCodeDirVO useDsdCodeDirVO(DsdCodeDir dsdCodeDir) {
        if ( dsdCodeDir == null ) {
            return null;
        }

        DsdCodeDirVO dsdCodeDirVO = new DsdCodeDirVO();

        dsdCodeDirVO.setId( dsdCodeDir.getId() );
        dsdCodeDirVO.setCodeDirId( dsdCodeDir.getCodeDirId() );
        dsdCodeDirVO.setCodeDirName( dsdCodeDir.getCodeDirName() );
        dsdCodeDirVO.setParentId( dsdCodeDir.getParentId() );
        dsdCodeDirVO.setCodeDirLevel( dsdCodeDir.getCodeDirLevel() );
        dsdCodeDirVO.setDescription( dsdCodeDir.getDescription() );

        return dsdCodeDirVO;
    }

    @Override
    public DsdCodeDir useDsdCodeDir(DsdCodeDirVO dsdDirVO) {
        if ( dsdDirVO == null ) {
            return null;
        }

        DsdCodeDir dsdCodeDir = new DsdCodeDir();

        dsdCodeDir.setId( dsdDirVO.getId() );
        dsdCodeDir.setCodeDirId( dsdDirVO.getCodeDirId() );
        dsdCodeDir.setCodeDirName( dsdDirVO.getCodeDirName() );
        dsdCodeDir.setParentId( dsdDirVO.getParentId() );
        dsdCodeDir.setCodeDirLevel( dsdDirVO.getCodeDirLevel() );
        dsdCodeDir.setDescription( dsdDirVO.getDescription() );

        return dsdCodeDir;
    }
}
