package com.qk.dm.datastandards.mapstruct.mapper;

import com.qk.dm.datastandards.entity.DsdDir;
import com.qk.dm.datastandards.vo.DataStandardTreeVO;
import com.qk.dm.datastandards.vo.DsdDirVO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T17:36:26+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class DsdDirTreeMapperImpl implements DsdDirTreeMapper {

    @Override
    public DataStandardTreeVO useDirTreeVO(DsdDir dsdDir) {
        if ( dsdDir == null ) {
            return null;
        }

        DataStandardTreeVO dataStandardTreeVO = new DataStandardTreeVO();

        dataStandardTreeVO.setId( dsdDir.getId() );
        dataStandardTreeVO.setDirDsdId( dsdDir.getDirDsdId() );
        dataStandardTreeVO.setDirDsdName( dsdDir.getDirDsdName() );
        dataStandardTreeVO.setParentId( dsdDir.getParentId() );
        dataStandardTreeVO.setDsdDirLevel( dsdDir.getDsdDirLevel() );

        return dataStandardTreeVO;
    }

    @Override
    public DsdDir useDsdDir(DsdDirVO dsdDirVO) {
        if ( dsdDirVO == null ) {
            return null;
        }

        DsdDir dsdDir = new DsdDir();

        dsdDir.setId( dsdDirVO.getId() );
        dsdDir.setDirDsdId( dsdDirVO.getDirDsdId() );
        dsdDir.setDirDsdName( dsdDirVO.getDirDsdName() );
        dsdDir.setParentId( dsdDirVO.getParentId() );
        dsdDir.setDsdDirLevel( dsdDirVO.getDsdDirLevel() );
        dsdDir.setDescription( dsdDirVO.getDescription() );

        return dsdDir;
    }
}
