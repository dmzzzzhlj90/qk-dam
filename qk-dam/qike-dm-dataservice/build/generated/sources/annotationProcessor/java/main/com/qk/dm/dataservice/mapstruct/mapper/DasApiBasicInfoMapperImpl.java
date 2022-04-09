package com.qk.dm.dataservice.mapstruct.mapper;

import com.qk.dm.dataservice.entity.DasApiBasicInfo;
import com.qk.dm.dataservice.vo.DasApiBasicInfoVO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T20:19:53+0800",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.9.1.jar, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class DasApiBasicInfoMapperImpl implements DasApiBasicInfoMapper {

    @Override
    public DasApiBasicInfoVO useDasApiBasicInfoVO(DasApiBasicInfo dasApiBasicinfo) {
        if ( dasApiBasicinfo == null ) {
            return null;
        }

        DasApiBasicInfoVO dasApiBasicInfoVO = new DasApiBasicInfoVO();

        dasApiBasicInfoVO.setId( dasApiBasicinfo.getId() );
        dasApiBasicInfoVO.setApiId( dasApiBasicinfo.getApiId() );
        dasApiBasicInfoVO.setDirId( dasApiBasicinfo.getDirId() );
        dasApiBasicInfoVO.setDirName( dasApiBasicinfo.getDirName() );
        dasApiBasicInfoVO.setApiName( dasApiBasicinfo.getApiName() );
        dasApiBasicInfoVO.setApiPath( dasApiBasicinfo.getApiPath() );
        dasApiBasicInfoVO.setProtocolType( dasApiBasicinfo.getProtocolType() );
        dasApiBasicInfoVO.setRequestType( dasApiBasicinfo.getRequestType() );
        dasApiBasicInfoVO.setApiType( dasApiBasicinfo.getApiType() );
        dasApiBasicInfoVO.setStatus( dasApiBasicinfo.getStatus() );
        dasApiBasicInfoVO.setDescription( dasApiBasicinfo.getDescription() );
        dasApiBasicInfoVO.setCreateUserid( dasApiBasicinfo.getCreateUserid() );
        dasApiBasicInfoVO.setUpdateUserid( dasApiBasicinfo.getUpdateUserid() );
        dasApiBasicInfoVO.setGmtCreate( dasApiBasicinfo.getGmtCreate() );
        dasApiBasicInfoVO.setGmtModified( dasApiBasicinfo.getGmtModified() );

        return dasApiBasicInfoVO;
    }

    @Override
    public DasApiBasicInfo useDasApiBasicInfo(DasApiBasicInfoVO dasApiBasicinfoVO) {
        if ( dasApiBasicinfoVO == null ) {
            return null;
        }

        DasApiBasicInfo dasApiBasicInfo = new DasApiBasicInfo();

        dasApiBasicInfo.setId( dasApiBasicinfoVO.getId() );
        dasApiBasicInfo.setApiId( dasApiBasicinfoVO.getApiId() );
        dasApiBasicInfo.setDirId( dasApiBasicinfoVO.getDirId() );
        dasApiBasicInfo.setDirName( dasApiBasicinfoVO.getDirName() );
        dasApiBasicInfo.setApiName( dasApiBasicinfoVO.getApiName() );
        dasApiBasicInfo.setApiPath( dasApiBasicinfoVO.getApiPath() );
        dasApiBasicInfo.setProtocolType( dasApiBasicinfoVO.getProtocolType() );
        dasApiBasicInfo.setRequestType( dasApiBasicinfoVO.getRequestType() );
        dasApiBasicInfo.setApiType( dasApiBasicinfoVO.getApiType() );
        dasApiBasicInfo.setStatus( dasApiBasicinfoVO.getStatus() );
        dasApiBasicInfo.setDescription( dasApiBasicinfoVO.getDescription() );
        dasApiBasicInfo.setCreateUserid( dasApiBasicinfoVO.getCreateUserid() );
        dasApiBasicInfo.setUpdateUserid( dasApiBasicinfoVO.getUpdateUserid() );
        dasApiBasicInfo.setGmtCreate( dasApiBasicinfoVO.getGmtCreate() );
        dasApiBasicInfo.setGmtModified( dasApiBasicinfoVO.getGmtModified() );

        return dasApiBasicInfo;
    }
}
