package com.qk.dm.dataservice.mapstruct.mapper;

import com.qk.dm.dataservice.entity.DasApiAppManageInfo;
import com.qk.dm.dataservice.vo.DasApiAppManageInfoVO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T17:36:28+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class DasApiAppManageInfoMapperImpl implements DasApiAppManageInfoMapper {

    @Override
    public DasApiAppManageInfoVO useDasApiAppManageInfoVO(DasApiAppManageInfo dasApiAppManageInfo) {
        if ( dasApiAppManageInfo == null ) {
            return null;
        }

        DasApiAppManageInfoVO dasApiAppManageInfoVO = new DasApiAppManageInfoVO();

        dasApiAppManageInfoVO.setId( dasApiAppManageInfo.getId() );
        dasApiAppManageInfoVO.setAppName( dasApiAppManageInfo.getAppName() );
        dasApiAppManageInfoVO.setAppId( dasApiAppManageInfo.getAppId() );
        dasApiAppManageInfoVO.setRouteId( dasApiAppManageInfo.getRouteId() );
        dasApiAppManageInfoVO.setApiGroupRoutePath( dasApiAppManageInfo.getApiGroupRoutePath() );
        dasApiAppManageInfoVO.setApiType( dasApiAppManageInfo.getApiType() );
        dasApiAppManageInfoVO.setUpstreamId( dasApiAppManageInfo.getUpstreamId() );
        dasApiAppManageInfoVO.setServiceId( dasApiAppManageInfo.getServiceId() );
        dasApiAppManageInfoVO.setDescription( dasApiAppManageInfo.getDescription() );
        dasApiAppManageInfoVO.setUpdateUserid( dasApiAppManageInfo.getUpdateUserid() );
        dasApiAppManageInfoVO.setGmtModified( dasApiAppManageInfo.getGmtModified() );
        dasApiAppManageInfoVO.setDelFlag( dasApiAppManageInfo.getDelFlag() );

        return dasApiAppManageInfoVO;
    }

    @Override
    public DasApiAppManageInfo useDasApiAppManageInfo(DasApiAppManageInfoVO dasApiAppManageInfoVO) {
        if ( dasApiAppManageInfoVO == null ) {
            return null;
        }

        DasApiAppManageInfo dasApiAppManageInfo = new DasApiAppManageInfo();

        dasApiAppManageInfo.setId( dasApiAppManageInfoVO.getId() );
        dasApiAppManageInfo.setAppName( dasApiAppManageInfoVO.getAppName() );
        dasApiAppManageInfo.setAppId( dasApiAppManageInfoVO.getAppId() );
        dasApiAppManageInfo.setRouteId( dasApiAppManageInfoVO.getRouteId() );
        dasApiAppManageInfo.setApiGroupRoutePath( dasApiAppManageInfoVO.getApiGroupRoutePath() );
        dasApiAppManageInfo.setApiType( dasApiAppManageInfoVO.getApiType() );
        dasApiAppManageInfo.setUpstreamId( dasApiAppManageInfoVO.getUpstreamId() );
        dasApiAppManageInfo.setServiceId( dasApiAppManageInfoVO.getServiceId() );
        dasApiAppManageInfo.setDescription( dasApiAppManageInfoVO.getDescription() );
        dasApiAppManageInfo.setUpdateUserid( dasApiAppManageInfoVO.getUpdateUserid() );
        dasApiAppManageInfo.setGmtModified( dasApiAppManageInfoVO.getGmtModified() );
        dasApiAppManageInfo.setDelFlag( dasApiAppManageInfoVO.getDelFlag() );

        return dasApiAppManageInfo;
    }
}
