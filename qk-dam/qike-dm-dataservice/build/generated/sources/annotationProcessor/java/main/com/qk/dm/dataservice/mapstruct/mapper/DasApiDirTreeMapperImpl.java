package com.qk.dm.dataservice.mapstruct.mapper;

import com.qk.dm.dataservice.entity.DasApiDir;
import com.qk.dm.dataservice.vo.DasApiDirTreeVO;
import com.qk.dm.dataservice.vo.DasApiDirVO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T20:19:53+0800",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.9.1.jar, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class DasApiDirTreeMapperImpl implements DasApiDirTreeMapper {

    @Override
    public DasApiDirTreeVO useDasApiDirTreeVO(DasApiDir dasApiDir) {
        if ( dasApiDir == null ) {
            return null;
        }

        DasApiDirTreeVO dasApiDirTreeVO = new DasApiDirTreeVO();

        dasApiDirTreeVO.setId( dasApiDir.getId() );
        dasApiDirTreeVO.setDirId( dasApiDir.getDirId() );
        dasApiDirTreeVO.setTitle( dasApiDir.getDirName() );
        dasApiDirTreeVO.setValue( dasApiDir.getDirName() );
        dasApiDirTreeVO.setParentId( dasApiDir.getParentId() );

        return dasApiDirTreeVO;
    }

    @Override
    public DasApiDir useDasApiDir(DasApiDirVO dasApiDirVO) {
        if ( dasApiDirVO == null ) {
            return null;
        }

        DasApiDir dasApiDir = new DasApiDir();

        dasApiDir.setDirId( dasApiDirVO.getDirId() );
        dasApiDir.setDirName( dasApiDirVO.getTitle() );
        dasApiDir.setParentId( dasApiDirVO.getParentId() );
        dasApiDir.setId( dasApiDirVO.getId() );
        dasApiDir.setDescription( dasApiDirVO.getDescription() );
        dasApiDir.setGmtCreate( dasApiDirVO.getGmtCreate() );
        dasApiDir.setGmtModified( dasApiDirVO.getGmtModified() );
        dasApiDir.setDelFlag( dasApiDirVO.getDelFlag() );

        return dasApiDir;
    }

    @Override
    public DasApiDirVO useDasApiDirVO(DasApiDir dasApiDir) {
        if ( dasApiDir == null ) {
            return null;
        }

        DasApiDirVO dasApiDirVO = new DasApiDirVO();

        dasApiDirVO.setDirId( dasApiDir.getDirId() );
        dasApiDirVO.setTitle( dasApiDir.getDirName() );
        dasApiDirVO.setValue( dasApiDir.getDirName() );
        dasApiDirVO.setParentId( dasApiDir.getParentId() );
        dasApiDirVO.setId( dasApiDir.getId() );
        dasApiDirVO.setDescription( dasApiDir.getDescription() );
        dasApiDirVO.setGmtCreate( dasApiDir.getGmtCreate() );
        dasApiDirVO.setGmtModified( dasApiDir.getGmtModified() );
        dasApiDirVO.setDelFlag( dasApiDir.getDelFlag() );

        return dasApiDirVO;
    }
}
