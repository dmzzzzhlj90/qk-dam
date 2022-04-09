package com.qk.dm.reptile.mapstruct.mapper;

import com.qk.dm.reptile.entity.RptDimensionInfo;
import com.qk.dm.reptile.params.dto.RptDimensionInfoDTO;
import com.qk.dm.reptile.params.vo.RptDimensionInfoParamsVO;
import com.qk.dm.reptile.params.vo.RptDimensionInfoVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T17:36:27+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class RptDimensionInfoMapperImpl implements RptDimensionInfoMapper {

    @Override
    public RptDimensionInfo userRptDimensionInfoDTO(RptDimensionInfoDTO rptDimensionInfoDTO) {
        if ( rptDimensionInfoDTO == null ) {
            return null;
        }

        RptDimensionInfo rptDimensionInfo = new RptDimensionInfo();

        rptDimensionInfo.setId( rptDimensionInfoDTO.getId() );
        rptDimensionInfo.setDimensionName( rptDimensionInfoDTO.getDimensionName() );
        rptDimensionInfo.setFid( rptDimensionInfoDTO.getFid() );
        rptDimensionInfo.setDescription( rptDimensionInfoDTO.getDescription() );
        rptDimensionInfo.setGmtModified( rptDimensionInfoDTO.getGmtModified() );
        rptDimensionInfo.setGmtCreate( rptDimensionInfoDTO.getGmtCreate() );
        rptDimensionInfo.setCreateUserid( rptDimensionInfoDTO.getCreateUserid() );
        rptDimensionInfo.setUpdateUserid( rptDimensionInfoDTO.getUpdateUserid() );
        rptDimensionInfo.setCreateUsername( rptDimensionInfoDTO.getCreateUsername() );
        rptDimensionInfo.setUpdateUsername( rptDimensionInfoDTO.getUpdateUsername() );
        rptDimensionInfo.setDimensionCode( rptDimensionInfoDTO.getDimensionCode() );

        return rptDimensionInfo;
    }

    @Override
    public RptDimensionInfoVO userRptDimensionInfoVO(RptDimensionInfo rptDimensionInfo) {
        if ( rptDimensionInfo == null ) {
            return null;
        }

        RptDimensionInfoVO rptDimensionInfoVO = new RptDimensionInfoVO();

        rptDimensionInfoVO.setId( rptDimensionInfo.getId() );
        rptDimensionInfoVO.setDimensionName( rptDimensionInfo.getDimensionName() );
        rptDimensionInfoVO.setFid( rptDimensionInfo.getFid() );
        rptDimensionInfoVO.setDescription( rptDimensionInfo.getDescription() );
        rptDimensionInfoVO.setGmtModified( rptDimensionInfo.getGmtModified() );
        rptDimensionInfoVO.setGmtCreate( rptDimensionInfo.getGmtCreate() );
        rptDimensionInfoVO.setCreateUserid( rptDimensionInfo.getCreateUserid() );
        rptDimensionInfoVO.setUpdateUserid( rptDimensionInfo.getUpdateUserid() );
        rptDimensionInfoVO.setCreateUsername( rptDimensionInfo.getCreateUsername() );
        rptDimensionInfoVO.setUpdateUsername( rptDimensionInfo.getUpdateUsername() );
        rptDimensionInfoVO.setDimensionCode( rptDimensionInfo.getDimensionCode() );

        return rptDimensionInfoVO;
    }

    @Override
    public void of(RptDimensionInfoDTO rptDimensionInfoDTO, RptDimensionInfo rptDimensionInfo) {
        if ( rptDimensionInfoDTO == null ) {
            return;
        }

        if ( rptDimensionInfoDTO.getId() != null ) {
            rptDimensionInfo.setId( rptDimensionInfoDTO.getId() );
        }
        if ( rptDimensionInfoDTO.getDimensionName() != null ) {
            rptDimensionInfo.setDimensionName( rptDimensionInfoDTO.getDimensionName() );
        }
        if ( rptDimensionInfoDTO.getFid() != null ) {
            rptDimensionInfo.setFid( rptDimensionInfoDTO.getFid() );
        }
        if ( rptDimensionInfoDTO.getDescription() != null ) {
            rptDimensionInfo.setDescription( rptDimensionInfoDTO.getDescription() );
        }
        if ( rptDimensionInfoDTO.getGmtModified() != null ) {
            rptDimensionInfo.setGmtModified( rptDimensionInfoDTO.getGmtModified() );
        }
        if ( rptDimensionInfoDTO.getGmtCreate() != null ) {
            rptDimensionInfo.setGmtCreate( rptDimensionInfoDTO.getGmtCreate() );
        }
        if ( rptDimensionInfoDTO.getCreateUserid() != null ) {
            rptDimensionInfo.setCreateUserid( rptDimensionInfoDTO.getCreateUserid() );
        }
        if ( rptDimensionInfoDTO.getUpdateUserid() != null ) {
            rptDimensionInfo.setUpdateUserid( rptDimensionInfoDTO.getUpdateUserid() );
        }
        if ( rptDimensionInfoDTO.getCreateUsername() != null ) {
            rptDimensionInfo.setCreateUsername( rptDimensionInfoDTO.getCreateUsername() );
        }
        if ( rptDimensionInfoDTO.getUpdateUsername() != null ) {
            rptDimensionInfo.setUpdateUsername( rptDimensionInfoDTO.getUpdateUsername() );
        }
        if ( rptDimensionInfoDTO.getDimensionCode() != null ) {
            rptDimensionInfo.setDimensionCode( rptDimensionInfoDTO.getDimensionCode() );
        }
    }

    @Override
    public List<RptDimensionInfoVO> of(List<RptDimensionInfo> rptDimensionInfoList) {
        if ( rptDimensionInfoList == null ) {
            return null;
        }

        List<RptDimensionInfoVO> list = new ArrayList<RptDimensionInfoVO>( rptDimensionInfoList.size() );
        for ( RptDimensionInfo rptDimensionInfo : rptDimensionInfoList ) {
            list.add( userRptDimensionInfoVO( rptDimensionInfo ) );
        }

        return list;
    }

    @Override
    public List<RptDimensionInfoParamsVO> paramsof(List<RptDimensionInfo> rptDimensionInfoList) {
        if ( rptDimensionInfoList == null ) {
            return null;
        }

        List<RptDimensionInfoParamsVO> list = new ArrayList<RptDimensionInfoParamsVO>( rptDimensionInfoList.size() );
        for ( RptDimensionInfo rptDimensionInfo : rptDimensionInfoList ) {
            list.add( rptDimensionInfoToRptDimensionInfoParamsVO( rptDimensionInfo ) );
        }

        return list;
    }

    protected RptDimensionInfoParamsVO rptDimensionInfoToRptDimensionInfoParamsVO(RptDimensionInfo rptDimensionInfo) {
        if ( rptDimensionInfo == null ) {
            return null;
        }

        RptDimensionInfoParamsVO rptDimensionInfoParamsVO = new RptDimensionInfoParamsVO();

        rptDimensionInfoParamsVO.setId( rptDimensionInfo.getId() );
        rptDimensionInfoParamsVO.setDimensionName( rptDimensionInfo.getDimensionName() );
        rptDimensionInfoParamsVO.setDimensionCode( rptDimensionInfo.getDimensionCode() );

        return rptDimensionInfoParamsVO;
    }
}
