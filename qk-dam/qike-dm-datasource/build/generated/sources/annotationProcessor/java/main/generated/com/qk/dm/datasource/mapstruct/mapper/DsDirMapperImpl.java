package com.qk.dm.datasource.mapstruct.mapper;

import com.qk.dm.datasource.entity.DsDir;
import com.qk.dm.datasource.vo.DsDirReturnVO;
import com.qk.dm.datasource.vo.DsDirVO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T17:39:39+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class DsDirMapperImpl implements DsDirMapper {

    @Override
    public DsDirReturnVO useDsDirVO(DsDir dsDir) {
        if ( dsDir == null ) {
            return null;
        }

        DsDirReturnVO dsDirReturnVO = new DsDirReturnVO();

        dsDirReturnVO.setParentId( dsDir.getParentId() );
        dsDirReturnVO.setDsDirCode( dsDir.getDsDirCode() );

        return dsDirReturnVO;
    }

    @Override
    public DsDir useDsDir(DsDirVO dsDirVO) {
        if ( dsDirVO == null ) {
            return null;
        }

        DsDir dsDir = new DsDir();

        dsDir.setId( dsDirVO.getId() );
        dsDir.setDicName( dsDirVO.getDicName() );
        dsDir.setParentId( dsDirVO.getParentId() );
        dsDir.setGmtCreate( dsDirVO.getGmtCreate() );
        dsDir.setGmtModified( dsDirVO.getGmtModified() );
        dsDir.setCreateUserid( dsDirVO.getCreateUserid() );
        dsDir.setUpdateUserid( dsDirVO.getUpdateUserid() );
        dsDir.setDelFlag( dsDirVO.getDelFlag() );
        dsDir.setVersionConsumer( dsDirVO.getVersionConsumer() );
        dsDir.setDsDirCode( dsDirVO.getDsDirCode() );

        return dsDir;
    }
}
