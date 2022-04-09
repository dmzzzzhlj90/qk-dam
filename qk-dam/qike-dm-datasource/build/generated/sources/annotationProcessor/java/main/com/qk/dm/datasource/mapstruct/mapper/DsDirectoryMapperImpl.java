package com.qk.dm.datasource.mapstruct.mapper;

import com.qk.dm.datasource.entity.DsDirectory;
import com.qk.dm.datasource.vo.DsDirectoryVO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T20:19:47+0800",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.9.1.jar, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class DsDirectoryMapperImpl implements DsDirectoryMapper {

    @Override
    public DsDirectoryVO useDsDirectoryVO(DsDirectory dsDirectory) {
        if ( dsDirectory == null ) {
            return null;
        }

        DsDirectoryVO dsDirectoryVO = new DsDirectoryVO();

        dsDirectoryVO.setId( dsDirectory.getId() );
        dsDirectoryVO.setSysName( dsDirectory.getSysName() );
        dsDirectoryVO.setSysShortName( dsDirectory.getSysShortName() );
        dsDirectoryVO.setArea( dsDirectory.getArea() );
        dsDirectoryVO.setItDepartment( dsDirectory.getItDepartment() );
        dsDirectoryVO.setBusiDepartment( dsDirectory.getBusiDepartment() );
        dsDirectoryVO.setImportance( dsDirectory.getImportance() );
        dsDirectoryVO.setLeader( dsDirectory.getLeader() );
        dsDirectoryVO.setDeployPlace( dsDirectory.getDeployPlace() );
        dsDirectoryVO.setGmtCreate( dsDirectory.getGmtCreate() );
        dsDirectoryVO.setGmtModified( dsDirectory.getGmtModified() );
        dsDirectoryVO.setCreateUserid( dsDirectory.getCreateUserid() );
        dsDirectoryVO.setUpdateUserid( dsDirectory.getUpdateUserid() );
        dsDirectoryVO.setDelFlag( dsDirectory.getDelFlag() );
        dsDirectoryVO.setVersionConsumer( dsDirectory.getVersionConsumer() );
        dsDirectoryVO.setSysDesc( dsDirectory.getSysDesc() );
        dsDirectoryVO.setTagNames( dsDirectory.getTagNames() );
        dsDirectoryVO.setTagIds( dsDirectory.getTagIds() );

        return dsDirectoryVO;
    }

    @Override
    public DsDirectory useDsDirectory(DsDirectoryVO dsDirectoryVO) {
        if ( dsDirectoryVO == null ) {
            return null;
        }

        DsDirectory dsDirectory = new DsDirectory();

        dsDirectory.setId( dsDirectoryVO.getId() );
        dsDirectory.setSysName( dsDirectoryVO.getSysName() );
        dsDirectory.setSysShortName( dsDirectoryVO.getSysShortName() );
        dsDirectory.setArea( dsDirectoryVO.getArea() );
        dsDirectory.setItDepartment( dsDirectoryVO.getItDepartment() );
        dsDirectory.setBusiDepartment( dsDirectoryVO.getBusiDepartment() );
        dsDirectory.setImportance( dsDirectoryVO.getImportance() );
        dsDirectory.setLeader( dsDirectoryVO.getLeader() );
        dsDirectory.setDeployPlace( dsDirectoryVO.getDeployPlace() );
        dsDirectory.setGmtCreate( dsDirectoryVO.getGmtCreate() );
        dsDirectory.setGmtModified( dsDirectoryVO.getGmtModified() );
        dsDirectory.setCreateUserid( dsDirectoryVO.getCreateUserid() );
        dsDirectory.setUpdateUserid( dsDirectoryVO.getUpdateUserid() );
        dsDirectory.setDelFlag( dsDirectoryVO.getDelFlag() );
        dsDirectory.setVersionConsumer( dsDirectoryVO.getVersionConsumer() );
        dsDirectory.setSysDesc( dsDirectoryVO.getSysDesc() );
        dsDirectory.setTagNames( dsDirectoryVO.getTagNames() );
        dsDirectory.setTagIds( dsDirectoryVO.getTagIds() );

        return dsDirectory;
    }
}
