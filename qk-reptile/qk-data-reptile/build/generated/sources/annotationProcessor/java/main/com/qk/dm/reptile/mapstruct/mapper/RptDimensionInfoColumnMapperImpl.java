package com.qk.dm.reptile.mapstruct.mapper;

import com.qk.dm.reptile.entity.RptDimensionColumnInfo;
import com.qk.dm.reptile.params.dto.RptDimensionInfoColumnDTO;
import com.qk.dm.reptile.params.vo.RptDimensionInfoColumnVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T20:19:48+0800",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.9.1.jar, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class RptDimensionInfoColumnMapperImpl implements RptDimensionInfoColumnMapper {

    @Override
    public RptDimensionColumnInfo userRptDimensionInfoColumnDTO(RptDimensionInfoColumnDTO rptDimensionInfoColumnDTO) {
        if ( rptDimensionInfoColumnDTO == null ) {
            return null;
        }

        RptDimensionColumnInfo rptDimensionColumnInfo = new RptDimensionColumnInfo();

        rptDimensionColumnInfo.setId( rptDimensionInfoColumnDTO.getId() );
        rptDimensionColumnInfo.setDimensionId( rptDimensionInfoColumnDTO.getDimensionId() );
        rptDimensionColumnInfo.setDescription( rptDimensionInfoColumnDTO.getDescription() );
        rptDimensionColumnInfo.setDimensionColumnName( rptDimensionInfoColumnDTO.getDimensionColumnName() );
        rptDimensionColumnInfo.setGmtModified( rptDimensionInfoColumnDTO.getGmtModified() );
        rptDimensionColumnInfo.setGmtCreate( rptDimensionInfoColumnDTO.getGmtCreate() );
        rptDimensionColumnInfo.setCreateUserid( rptDimensionInfoColumnDTO.getCreateUserid() );
        rptDimensionColumnInfo.setUpdateUserid( rptDimensionInfoColumnDTO.getUpdateUserid() );
        rptDimensionColumnInfo.setCreateUsername( rptDimensionInfoColumnDTO.getCreateUsername() );
        rptDimensionColumnInfo.setUpdateUsername( rptDimensionInfoColumnDTO.getUpdateUsername() );
        rptDimensionColumnInfo.setDimensionColumnCode( rptDimensionInfoColumnDTO.getDimensionColumnCode() );

        return rptDimensionColumnInfo;
    }

    @Override
    public RptDimensionInfoColumnVO userRptDimensionInfoColumnVO(RptDimensionColumnInfo rptDimensionColumnInfo) {
        if ( rptDimensionColumnInfo == null ) {
            return null;
        }

        RptDimensionInfoColumnVO rptDimensionInfoColumnVO = new RptDimensionInfoColumnVO();

        rptDimensionInfoColumnVO.setId( rptDimensionColumnInfo.getId() );
        rptDimensionInfoColumnVO.setDimensionId( rptDimensionColumnInfo.getDimensionId() );
        rptDimensionInfoColumnVO.setDescription( rptDimensionColumnInfo.getDescription() );
        rptDimensionInfoColumnVO.setDimensionColumnName( rptDimensionColumnInfo.getDimensionColumnName() );
        rptDimensionInfoColumnVO.setGmtModified( rptDimensionColumnInfo.getGmtModified() );
        rptDimensionInfoColumnVO.setGmtCreate( rptDimensionColumnInfo.getGmtCreate() );
        rptDimensionInfoColumnVO.setCreateUserid( rptDimensionColumnInfo.getCreateUserid() );
        rptDimensionInfoColumnVO.setUpdateUserid( rptDimensionColumnInfo.getUpdateUserid() );
        rptDimensionInfoColumnVO.setCreateUsername( rptDimensionColumnInfo.getCreateUsername() );
        rptDimensionInfoColumnVO.setUpdateUsername( rptDimensionColumnInfo.getUpdateUsername() );
        rptDimensionInfoColumnVO.setDimensionColumnCode( rptDimensionColumnInfo.getDimensionColumnCode() );

        return rptDimensionInfoColumnVO;
    }

    @Override
    public void of(RptDimensionInfoColumnDTO rptDimensionInfoColumnDTO, RptDimensionColumnInfo rptDimensionColumnInfo) {
        if ( rptDimensionInfoColumnDTO == null ) {
            return;
        }

        if ( rptDimensionInfoColumnDTO.getId() != null ) {
            rptDimensionColumnInfo.setId( rptDimensionInfoColumnDTO.getId() );
        }
        if ( rptDimensionInfoColumnDTO.getDimensionId() != null ) {
            rptDimensionColumnInfo.setDimensionId( rptDimensionInfoColumnDTO.getDimensionId() );
        }
        if ( rptDimensionInfoColumnDTO.getDescription() != null ) {
            rptDimensionColumnInfo.setDescription( rptDimensionInfoColumnDTO.getDescription() );
        }
        if ( rptDimensionInfoColumnDTO.getDimensionColumnName() != null ) {
            rptDimensionColumnInfo.setDimensionColumnName( rptDimensionInfoColumnDTO.getDimensionColumnName() );
        }
        if ( rptDimensionInfoColumnDTO.getGmtModified() != null ) {
            rptDimensionColumnInfo.setGmtModified( rptDimensionInfoColumnDTO.getGmtModified() );
        }
        if ( rptDimensionInfoColumnDTO.getGmtCreate() != null ) {
            rptDimensionColumnInfo.setGmtCreate( rptDimensionInfoColumnDTO.getGmtCreate() );
        }
        if ( rptDimensionInfoColumnDTO.getCreateUserid() != null ) {
            rptDimensionColumnInfo.setCreateUserid( rptDimensionInfoColumnDTO.getCreateUserid() );
        }
        if ( rptDimensionInfoColumnDTO.getUpdateUserid() != null ) {
            rptDimensionColumnInfo.setUpdateUserid( rptDimensionInfoColumnDTO.getUpdateUserid() );
        }
        if ( rptDimensionInfoColumnDTO.getCreateUsername() != null ) {
            rptDimensionColumnInfo.setCreateUsername( rptDimensionInfoColumnDTO.getCreateUsername() );
        }
        if ( rptDimensionInfoColumnDTO.getUpdateUsername() != null ) {
            rptDimensionColumnInfo.setUpdateUsername( rptDimensionInfoColumnDTO.getUpdateUsername() );
        }
        if ( rptDimensionInfoColumnDTO.getDimensionColumnCode() != null ) {
            rptDimensionColumnInfo.setDimensionColumnCode( rptDimensionInfoColumnDTO.getDimensionColumnCode() );
        }
    }

    @Override
    public List<RptDimensionInfoColumnVO> of(List<RptDimensionColumnInfo> list) {
        if ( list == null ) {
            return null;
        }

        List<RptDimensionInfoColumnVO> list1 = new ArrayList<RptDimensionInfoColumnVO>( list.size() );
        for ( RptDimensionColumnInfo rptDimensionColumnInfo : list ) {
            list1.add( userRptDimensionInfoColumnVO( rptDimensionColumnInfo ) );
        }

        return list1;
    }
}
