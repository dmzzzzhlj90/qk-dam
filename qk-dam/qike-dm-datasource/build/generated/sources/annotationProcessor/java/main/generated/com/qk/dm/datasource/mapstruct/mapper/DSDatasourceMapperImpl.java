package com.qk.dm.datasource.mapstruct.mapper;

import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import com.qk.dam.datasource.entity.ResultDatasourceInfo.ResultDatasourceInfoBuilder;
import com.qk.dm.datasource.entity.DsDatasource;
import com.qk.dm.datasource.vo.DsDatasourceVO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T17:39:39+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class DSDatasourceMapperImpl implements DSDatasourceMapper {

    @Override
    public DsDatasourceVO useDsDatasourceVO(DsDatasource dsDatasource) {
        if ( dsDatasource == null ) {
            return null;
        }

        DsDatasourceVO dsDatasourceVO = new DsDatasourceVO();

        dsDatasourceVO.setId( dsDatasource.getId() );
        dsDatasourceVO.setDataSourceName( dsDatasource.getDataSourceName() );
        dsDatasourceVO.setHomeSystem( dsDatasource.getHomeSystem() );
        dsDatasourceVO.setLinkType( dsDatasource.getLinkType() );
        dsDatasourceVO.setTagNames( dsDatasource.getTagNames() );
        dsDatasourceVO.setTagIds( dsDatasource.getTagIds() );
        dsDatasourceVO.setDeployPlace( dsDatasource.getDeployPlace() );
        dsDatasourceVO.setStatus( dsDatasource.getStatus() );
        dsDatasourceVO.setGmtModified( dsDatasource.getGmtModified() );
        dsDatasourceVO.setGmtCreate( dsDatasource.getGmtCreate() );
        dsDatasourceVO.setCreateUserid( dsDatasource.getCreateUserid() );
        dsDatasourceVO.setUpdateUserid( dsDatasource.getUpdateUserid() );
        dsDatasourceVO.setDelFlag( dsDatasource.getDelFlag() );
        dsDatasourceVO.setDicId( dsDatasource.getDicId() );
        dsDatasourceVO.setRemark( dsDatasource.getRemark() );

        return dsDatasourceVO;
    }

    @Override
    public DsDatasource useDsDatasource(DsDatasourceVO dsDatasourceVO) {
        if ( dsDatasourceVO == null ) {
            return null;
        }

        DsDatasource dsDatasource = new DsDatasource();

        dsDatasource.setId( dsDatasourceVO.getId() );
        dsDatasource.setDataSourceName( dsDatasourceVO.getDataSourceName() );
        dsDatasource.setHomeSystem( dsDatasourceVO.getHomeSystem() );
        dsDatasource.setLinkType( dsDatasourceVO.getLinkType() );
        dsDatasource.setTagNames( dsDatasourceVO.getTagNames() );
        dsDatasource.setTagIds( dsDatasourceVO.getTagIds() );
        dsDatasource.setDeployPlace( dsDatasourceVO.getDeployPlace() );
        dsDatasource.setStatus( dsDatasourceVO.getStatus() );
        dsDatasource.setGmtCreate( dsDatasourceVO.getGmtCreate() );
        dsDatasource.setGmtModified( dsDatasourceVO.getGmtModified() );
        dsDatasource.setCreateUserid( dsDatasourceVO.getCreateUserid() );
        dsDatasource.setUpdateUserid( dsDatasourceVO.getUpdateUserid() );
        dsDatasource.setDelFlag( dsDatasourceVO.getDelFlag() );
        dsDatasource.setDicId( dsDatasourceVO.getDicId() );
        dsDatasource.setRemark( dsDatasourceVO.getRemark() );

        return dsDatasource;
    }

    @Override
    public ResultDatasourceInfo useResultDatasourceInfo(DsDatasource dsDatasource) {
        if ( dsDatasource == null ) {
            return null;
        }

        ResultDatasourceInfoBuilder resultDatasourceInfo = ResultDatasourceInfo.builder();

        if ( dsDatasource.getId() != null ) {
            resultDatasourceInfo.id( Integer.parseInt( dsDatasource.getId() ) );
        }
        resultDatasourceInfo.dataSourceName( dsDatasource.getDataSourceName() );
        resultDatasourceInfo.homeSystem( dsDatasource.getHomeSystem() );
        resultDatasourceInfo.status( dsDatasource.getStatus() );

        return resultDatasourceInfo.build();
    }
}
