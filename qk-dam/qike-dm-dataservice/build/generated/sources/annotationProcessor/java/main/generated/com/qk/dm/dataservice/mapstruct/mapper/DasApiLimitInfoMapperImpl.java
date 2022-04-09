package com.qk.dm.dataservice.mapstruct.mapper;

import com.qk.dm.dataservice.entity.DasApiLimitBindInfo;
import com.qk.dm.dataservice.entity.DasApiLimitInfo;
import com.qk.dm.dataservice.vo.DasApiLimitBindInfoVO;
import com.qk.dm.dataservice.vo.DasApiLimitInfoVO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T17:36:28+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class DasApiLimitInfoMapperImpl implements DasApiLimitInfoMapper {

    @Override
    public DasApiLimitInfoVO useDasApiLimitInfoVO(DasApiLimitInfo dasApiLimitInfo) {
        if ( dasApiLimitInfo == null ) {
            return null;
        }

        DasApiLimitInfoVO dasApiLimitInfoVO = new DasApiLimitInfoVO();

        dasApiLimitInfoVO.setId( dasApiLimitInfo.getId() );
        dasApiLimitInfoVO.setLimitName( dasApiLimitInfo.getLimitName() );
        dasApiLimitInfoVO.setLimitTime( dasApiLimitInfo.getLimitTime() );
        dasApiLimitInfoVO.setLimitTimeUnit( dasApiLimitInfo.getLimitTimeUnit() );
        dasApiLimitInfoVO.setApiLimitCount( dasApiLimitInfo.getApiLimitCount() );
        dasApiLimitInfoVO.setUserLimitCount( dasApiLimitInfo.getUserLimitCount() );
        dasApiLimitInfoVO.setAppLimitCount( dasApiLimitInfo.getAppLimitCount() );
        dasApiLimitInfoVO.setDescription( dasApiLimitInfo.getDescription() );
        dasApiLimitInfoVO.setGmtCreate( dasApiLimitInfo.getGmtCreate() );
        dasApiLimitInfoVO.setGmtModified( dasApiLimitInfo.getGmtModified() );
        dasApiLimitInfoVO.setDelFlag( dasApiLimitInfo.getDelFlag() );

        return dasApiLimitInfoVO;
    }

    @Override
    public DasApiLimitInfo useDasApiLimitInfo(DasApiLimitInfoVO dasApiLimitInfoVO) {
        if ( dasApiLimitInfoVO == null ) {
            return null;
        }

        DasApiLimitInfo dasApiLimitInfo = new DasApiLimitInfo();

        dasApiLimitInfo.setId( dasApiLimitInfoVO.getId() );
        dasApiLimitInfo.setLimitName( dasApiLimitInfoVO.getLimitName() );
        dasApiLimitInfo.setLimitTime( dasApiLimitInfoVO.getLimitTime() );
        dasApiLimitInfo.setLimitTimeUnit( dasApiLimitInfoVO.getLimitTimeUnit() );
        dasApiLimitInfo.setApiLimitCount( dasApiLimitInfoVO.getApiLimitCount() );
        dasApiLimitInfo.setUserLimitCount( dasApiLimitInfoVO.getUserLimitCount() );
        dasApiLimitInfo.setAppLimitCount( dasApiLimitInfoVO.getAppLimitCount() );
        dasApiLimitInfo.setDescription( dasApiLimitInfoVO.getDescription() );
        dasApiLimitInfo.setGmtCreate( dasApiLimitInfoVO.getGmtCreate() );
        dasApiLimitInfo.setGmtModified( dasApiLimitInfoVO.getGmtModified() );
        dasApiLimitInfo.setDelFlag( dasApiLimitInfoVO.getDelFlag() );

        return dasApiLimitInfo;
    }

    @Override
    public DasApiLimitBindInfoVO useDasApiLimitBindInfoVO(DasApiLimitBindInfo bindInfo) {
        if ( bindInfo == null ) {
            return null;
        }

        DasApiLimitBindInfoVO dasApiLimitBindInfoVO = new DasApiLimitBindInfoVO();

        dasApiLimitBindInfoVO.setId( bindInfo.getId() );
        dasApiLimitBindInfoVO.setLimitId( bindInfo.getLimitId() );
        dasApiLimitBindInfoVO.setRouteId( bindInfo.getRouteId() );
        dasApiLimitBindInfoVO.setApiGroupRouteName( bindInfo.getApiGroupRouteName() );
        dasApiLimitBindInfoVO.setApiGroupRoutePath( bindInfo.getApiGroupRoutePath() );
        dasApiLimitBindInfoVO.setDescription( bindInfo.getDescription() );
        dasApiLimitBindInfoVO.setDelFlag( bindInfo.getDelFlag() );

        return dasApiLimitBindInfoVO;
    }
}
