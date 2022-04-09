package com.qk.dm.reptile.mapstruct.mapper;

import com.qk.dm.reptile.entity.RptConfigInfo;
import com.qk.dm.reptile.params.dto.RptConfigInfoDTO;
import com.qk.dm.reptile.params.vo.RptConfigInfoVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T17:36:27+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class RptConfigInfoMapperImpl implements RptConfigInfoMapper {

    @Override
    public RptConfigInfo useRptConfigInfo(RptConfigInfoDTO rptConfigInfoDTO) {
        if ( rptConfigInfoDTO == null ) {
            return null;
        }

        RptConfigInfo rptConfigInfo = new RptConfigInfo();

        rptConfigInfo.setId( rptConfigInfoDTO.getId() );
        rptConfigInfo.setBaseInfoId( rptConfigInfoDTO.getBaseInfoId() );
        rptConfigInfo.setParentId( rptConfigInfoDTO.getParentId() );
        rptConfigInfo.setDimensionId( rptConfigInfoDTO.getDimensionId() );
        rptConfigInfo.setDimensionName( rptConfigInfoDTO.getDimensionName() );
        rptConfigInfo.setDimensionCode( rptConfigInfoDTO.getDimensionCode() );
        rptConfigInfo.setStartoverJs( rptConfigInfoDTO.getStartoverJs() );
        rptConfigInfo.setStartoverIp( rptConfigInfoDTO.getStartoverIp() );
        rptConfigInfo.setRequestUrl( rptConfigInfoDTO.getRequestUrl() );
        rptConfigInfo.setRequestType( rptConfigInfoDTO.getRequestType() );

        return rptConfigInfo;
    }

    @Override
    public RptConfigInfoVO useRptConfigInfoVO(RptConfigInfo rptConfigInfo) {
        if ( rptConfigInfo == null ) {
            return null;
        }

        RptConfigInfoVO rptConfigInfoVO = new RptConfigInfoVO();

        rptConfigInfoVO.setId( rptConfigInfo.getId() );
        rptConfigInfoVO.setBaseInfoId( rptConfigInfo.getBaseInfoId() );
        rptConfigInfoVO.setParentId( rptConfigInfo.getParentId() );
        rptConfigInfoVO.setDimensionId( rptConfigInfo.getDimensionId() );
        rptConfigInfoVO.setDimensionName( rptConfigInfo.getDimensionName() );
        rptConfigInfoVO.setDimensionCode( rptConfigInfo.getDimensionCode() );
        rptConfigInfoVO.setStartoverJs( rptConfigInfo.getStartoverJs() );
        rptConfigInfoVO.setStartoverIp( rptConfigInfo.getStartoverIp() );
        rptConfigInfoVO.setRequestUrl( rptConfigInfo.getRequestUrl() );
        rptConfigInfoVO.setRequestType( rptConfigInfo.getRequestType() );

        return rptConfigInfoVO;
    }

    @Override
    public void of(RptConfigInfoDTO rptConfigInfoDTO, RptConfigInfo rptConfigInfo) {
        if ( rptConfigInfoDTO == null ) {
            return;
        }

        if ( rptConfigInfoDTO.getId() != null ) {
            rptConfigInfo.setId( rptConfigInfoDTO.getId() );
        }
        if ( rptConfigInfoDTO.getBaseInfoId() != null ) {
            rptConfigInfo.setBaseInfoId( rptConfigInfoDTO.getBaseInfoId() );
        }
        if ( rptConfigInfoDTO.getParentId() != null ) {
            rptConfigInfo.setParentId( rptConfigInfoDTO.getParentId() );
        }
        if ( rptConfigInfoDTO.getDimensionId() != null ) {
            rptConfigInfo.setDimensionId( rptConfigInfoDTO.getDimensionId() );
        }
        if ( rptConfigInfoDTO.getDimensionName() != null ) {
            rptConfigInfo.setDimensionName( rptConfigInfoDTO.getDimensionName() );
        }
        if ( rptConfigInfoDTO.getDimensionCode() != null ) {
            rptConfigInfo.setDimensionCode( rptConfigInfoDTO.getDimensionCode() );
        }
        if ( rptConfigInfoDTO.getStartoverJs() != null ) {
            rptConfigInfo.setStartoverJs( rptConfigInfoDTO.getStartoverJs() );
        }
        if ( rptConfigInfoDTO.getStartoverIp() != null ) {
            rptConfigInfo.setStartoverIp( rptConfigInfoDTO.getStartoverIp() );
        }
        if ( rptConfigInfoDTO.getRequestUrl() != null ) {
            rptConfigInfo.setRequestUrl( rptConfigInfoDTO.getRequestUrl() );
        }
        if ( rptConfigInfoDTO.getRequestType() != null ) {
            rptConfigInfo.setRequestType( rptConfigInfoDTO.getRequestType() );
        }
    }

    @Override
    public List<RptConfigInfoVO> of(List<RptConfigInfo> rptConfigInfoList) {
        if ( rptConfigInfoList == null ) {
            return null;
        }

        List<RptConfigInfoVO> list = new ArrayList<RptConfigInfoVO>( rptConfigInfoList.size() );
        for ( RptConfigInfo rptConfigInfo : rptConfigInfoList ) {
            list.add( useRptConfigInfoVO( rptConfigInfo ) );
        }

        return list;
    }

    @Override
    public void of(RptConfigInfo info, RptConfigInfo rptConfigInfo) {
        if ( info == null ) {
            return;
        }

        if ( info.getBaseInfoId() != null ) {
            rptConfigInfo.setBaseInfoId( info.getBaseInfoId() );
        }
        if ( info.getParentId() != null ) {
            rptConfigInfo.setParentId( info.getParentId() );
        }
        if ( info.getRaw() != null ) {
            rptConfigInfo.setRaw( info.getRaw() );
        }
        if ( info.getFormUrlencoded() != null ) {
            rptConfigInfo.setFormUrlencoded( info.getFormUrlencoded() );
        }
        if ( info.getFormData() != null ) {
            rptConfigInfo.setFormData( info.getFormData() );
        }
        if ( info.getCookies() != null ) {
            rptConfigInfo.setCookies( info.getCookies() );
        }
        if ( info.getHeaders() != null ) {
            rptConfigInfo.setHeaders( info.getHeaders() );
        }
        if ( info.getDescription() != null ) {
            rptConfigInfo.setDescription( info.getDescription() );
        }
        if ( info.getDimensionId() != null ) {
            rptConfigInfo.setDimensionId( info.getDimensionId() );
        }
        if ( info.getDimensionName() != null ) {
            rptConfigInfo.setDimensionName( info.getDimensionName() );
        }
        if ( info.getDimensionCode() != null ) {
            rptConfigInfo.setDimensionCode( info.getDimensionCode() );
        }
        if ( info.getStartoverJs() != null ) {
            rptConfigInfo.setStartoverJs( info.getStartoverJs() );
        }
        if ( info.getStartoverIp() != null ) {
            rptConfigInfo.setStartoverIp( info.getStartoverIp() );
        }
        if ( info.getRequestUrl() != null ) {
            rptConfigInfo.setRequestUrl( info.getRequestUrl() );
        }
        if ( info.getRequestType() != null ) {
            rptConfigInfo.setRequestType( info.getRequestType() );
        }
        if ( info.getCreateUserid() != null ) {
            rptConfigInfo.setCreateUserid( info.getCreateUserid() );
        }
        if ( info.getUpdateUserid() != null ) {
            rptConfigInfo.setUpdateUserid( info.getUpdateUserid() );
        }
        if ( info.getCreateUsername() != null ) {
            rptConfigInfo.setCreateUsername( info.getCreateUsername() );
        }
        if ( info.getUpdateUsername() != null ) {
            rptConfigInfo.setUpdateUsername( info.getUpdateUsername() );
        }
        if ( info.getGmtModified() != null ) {
            rptConfigInfo.setGmtModified( info.getGmtModified() );
        }
        if ( info.getGmtCreate() != null ) {
            rptConfigInfo.setGmtCreate( info.getGmtCreate() );
        }
    }
}
