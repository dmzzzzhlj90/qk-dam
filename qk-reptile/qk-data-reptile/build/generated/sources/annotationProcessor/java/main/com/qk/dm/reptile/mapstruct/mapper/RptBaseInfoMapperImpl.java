package com.qk.dm.reptile.mapstruct.mapper;

import com.qk.dm.reptile.entity.RptBaseInfo;
import com.qk.dm.reptile.params.dto.RptBaseInfoBatchDTO;
import com.qk.dm.reptile.params.dto.RptBaseInfoDTO;
import com.qk.dm.reptile.params.vo.RptBaseInfoVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T20:19:49+0800",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.9.1.jar, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class RptBaseInfoMapperImpl implements RptBaseInfoMapper {

    @Override
    public RptBaseInfo userRtpBaseInfo(RptBaseInfoDTO rtpBaseInfoDTO) {
        if ( rtpBaseInfoDTO == null ) {
            return null;
        }

        RptBaseInfo rptBaseInfo = new RptBaseInfo();

        rptBaseInfo.setId( rtpBaseInfoDTO.getId() );
        rptBaseInfo.setWebsiteName( rtpBaseInfoDTO.getWebsiteName() );
        rptBaseInfo.setWebsiteUrl( rtpBaseInfoDTO.getWebsiteUrl() );
        rptBaseInfo.setRunPeriod( rtpBaseInfoDTO.getRunPeriod() );
        rptBaseInfo.setStatus( rtpBaseInfoDTO.getStatus() );
        rptBaseInfo.setRunStatus( rtpBaseInfoDTO.getRunStatus() );
        rptBaseInfo.setConfigId( rtpBaseInfoDTO.getConfigId() );
        rptBaseInfo.setConfigName( rtpBaseInfoDTO.getConfigName() );
        rptBaseInfo.setGmtFunction( rtpBaseInfoDTO.getGmtFunction() );
        rptBaseInfo.setGmtCreate( rtpBaseInfoDTO.getGmtCreate() );
        rptBaseInfo.setCreateUsername( rtpBaseInfoDTO.getCreateUsername() );
        rptBaseInfo.setJobId( rtpBaseInfoDTO.getJobId() );
        rptBaseInfo.setDistributionDate( rtpBaseInfoDTO.getDistributionDate() );
        rptBaseInfo.setDeliveryDate( rtpBaseInfoDTO.getDeliveryDate() );
        rptBaseInfo.setSecondSiteType( rtpBaseInfoDTO.getSecondSiteType() );
        rptBaseInfo.setListPageAddress( rtpBaseInfoDTO.getListPageAddress() );
        rptBaseInfo.setDifferentTypeMixed( rtpBaseInfoDTO.getDifferentTypeMixed() );
        rptBaseInfo.setInfoReleaseLevel( rtpBaseInfoDTO.getInfoReleaseLevel() );
        rptBaseInfo.setProvinceCode( rtpBaseInfoDTO.getProvinceCode() );
        rptBaseInfo.setCityCode( rtpBaseInfoDTO.getCityCode() );
        rptBaseInfo.setAreaCode( rtpBaseInfoDTO.getAreaCode() );
        rptBaseInfo.setWebsiteNameCorrection( rtpBaseInfoDTO.getWebsiteNameCorrection() );
        rptBaseInfo.setWebsiteUrlCorrection( rtpBaseInfoDTO.getWebsiteUrlCorrection() );
        rptBaseInfo.setRegionCode( rtpBaseInfoDTO.getRegionCode() );
        rptBaseInfo.setExecutor( rtpBaseInfoDTO.getExecutor() );
        rptBaseInfo.setExecutorId( rtpBaseInfoDTO.getExecutorId() );
        rptBaseInfo.setTimeInterval( rtpBaseInfoDTO.getTimeInterval() );
        rptBaseInfo.setAssignedPersonName( rtpBaseInfoDTO.getAssignedPersonName() );
        rptBaseInfo.setResponsiblePersonName( rtpBaseInfoDTO.getResponsiblePersonName() );
        rptBaseInfo.setConfigStatus( rtpBaseInfoDTO.getConfigStatus() );
        rptBaseInfo.setDelFlag( rtpBaseInfoDTO.getDelFlag() );
        rptBaseInfo.setDelUserName( rtpBaseInfoDTO.getDelUserName() );
        rptBaseInfo.setDelDate( rtpBaseInfoDTO.getDelDate() );

        return rptBaseInfo;
    }

    @Override
    public RptBaseInfoVO userRtpBaseInfoVO(RptBaseInfo rptBaseInfo) {
        if ( rptBaseInfo == null ) {
            return null;
        }

        RptBaseInfoVO rptBaseInfoVO = new RptBaseInfoVO();

        rptBaseInfoVO.setId( rptBaseInfo.getId() );
        rptBaseInfoVO.setWebsiteName( rptBaseInfo.getWebsiteName() );
        rptBaseInfoVO.setWebsiteUrl( rptBaseInfo.getWebsiteUrl() );
        rptBaseInfoVO.setRunPeriod( rptBaseInfo.getRunPeriod() );
        rptBaseInfoVO.setStatus( rptBaseInfo.getStatus() );
        rptBaseInfoVO.setRunStatus( rptBaseInfo.getRunStatus() );
        rptBaseInfoVO.setConfigId( rptBaseInfo.getConfigId() );
        rptBaseInfoVO.setConfigName( rptBaseInfo.getConfigName() );
        rptBaseInfoVO.setJobId( rptBaseInfo.getJobId() );
        rptBaseInfoVO.setGmtFunction( rptBaseInfo.getGmtFunction() );
        rptBaseInfoVO.setCreateUsername( rptBaseInfo.getCreateUsername() );
        rptBaseInfoVO.setGmtCreate( rptBaseInfo.getGmtCreate() );
        rptBaseInfoVO.setDistributionDate( rptBaseInfo.getDistributionDate() );
        rptBaseInfoVO.setDeliveryDate( rptBaseInfo.getDeliveryDate() );
        rptBaseInfoVO.setSecondSiteType( rptBaseInfo.getSecondSiteType() );
        rptBaseInfoVO.setListPageAddress( rptBaseInfo.getListPageAddress() );
        rptBaseInfoVO.setDifferentTypeMixed( rptBaseInfo.getDifferentTypeMixed() );
        rptBaseInfoVO.setInfoReleaseLevel( rptBaseInfo.getInfoReleaseLevel() );
        rptBaseInfoVO.setProvinceCode( rptBaseInfo.getProvinceCode() );
        rptBaseInfoVO.setCityCode( rptBaseInfo.getCityCode() );
        rptBaseInfoVO.setAreaCode( rptBaseInfo.getAreaCode() );
        rptBaseInfoVO.setWebsiteNameCorrection( rptBaseInfo.getWebsiteNameCorrection() );
        rptBaseInfoVO.setWebsiteUrlCorrection( rptBaseInfo.getWebsiteUrlCorrection() );
        rptBaseInfoVO.setRegionCode( rptBaseInfo.getRegionCode() );
        rptBaseInfoVO.setExecutor( rptBaseInfo.getExecutor() );
        rptBaseInfoVO.setExecutorId( rptBaseInfo.getExecutorId() );
        rptBaseInfoVO.setConfigStatus( rptBaseInfo.getConfigStatus() );
        rptBaseInfoVO.setTimeInterval( rptBaseInfo.getTimeInterval() );
        rptBaseInfoVO.setAssignedPersonName( rptBaseInfo.getAssignedPersonName() );
        rptBaseInfoVO.setResponsiblePersonName( rptBaseInfo.getResponsiblePersonName() );
        rptBaseInfoVO.setConfigDate( rptBaseInfo.getConfigDate() );
        rptBaseInfoVO.setDelUserName( rptBaseInfo.getDelUserName() );
        rptBaseInfoVO.setDelDate( rptBaseInfo.getDelDate() );

        return rptBaseInfoVO;
    }

    @Override
    public void of(RptBaseInfoDTO rtpBaseInfoDTO, RptBaseInfo rptBaseInfo) {
        if ( rtpBaseInfoDTO == null ) {
            return;
        }

        if ( rtpBaseInfoDTO.getId() != null ) {
            rptBaseInfo.setId( rtpBaseInfoDTO.getId() );
        }
        if ( rtpBaseInfoDTO.getWebsiteName() != null ) {
            rptBaseInfo.setWebsiteName( rtpBaseInfoDTO.getWebsiteName() );
        }
        if ( rtpBaseInfoDTO.getWebsiteUrl() != null ) {
            rptBaseInfo.setWebsiteUrl( rtpBaseInfoDTO.getWebsiteUrl() );
        }
        if ( rtpBaseInfoDTO.getRunPeriod() != null ) {
            rptBaseInfo.setRunPeriod( rtpBaseInfoDTO.getRunPeriod() );
        }
        if ( rtpBaseInfoDTO.getStatus() != null ) {
            rptBaseInfo.setStatus( rtpBaseInfoDTO.getStatus() );
        }
        if ( rtpBaseInfoDTO.getRunStatus() != null ) {
            rptBaseInfo.setRunStatus( rtpBaseInfoDTO.getRunStatus() );
        }
        if ( rtpBaseInfoDTO.getConfigId() != null ) {
            rptBaseInfo.setConfigId( rtpBaseInfoDTO.getConfigId() );
        }
        if ( rtpBaseInfoDTO.getConfigName() != null ) {
            rptBaseInfo.setConfigName( rtpBaseInfoDTO.getConfigName() );
        }
        if ( rtpBaseInfoDTO.getGmtFunction() != null ) {
            rptBaseInfo.setGmtFunction( rtpBaseInfoDTO.getGmtFunction() );
        }
        if ( rtpBaseInfoDTO.getGmtCreate() != null ) {
            rptBaseInfo.setGmtCreate( rtpBaseInfoDTO.getGmtCreate() );
        }
        if ( rtpBaseInfoDTO.getCreateUsername() != null ) {
            rptBaseInfo.setCreateUsername( rtpBaseInfoDTO.getCreateUsername() );
        }
        if ( rtpBaseInfoDTO.getJobId() != null ) {
            rptBaseInfo.setJobId( rtpBaseInfoDTO.getJobId() );
        }
        if ( rtpBaseInfoDTO.getDistributionDate() != null ) {
            rptBaseInfo.setDistributionDate( rtpBaseInfoDTO.getDistributionDate() );
        }
        if ( rtpBaseInfoDTO.getDeliveryDate() != null ) {
            rptBaseInfo.setDeliveryDate( rtpBaseInfoDTO.getDeliveryDate() );
        }
        if ( rtpBaseInfoDTO.getSecondSiteType() != null ) {
            rptBaseInfo.setSecondSiteType( rtpBaseInfoDTO.getSecondSiteType() );
        }
        if ( rtpBaseInfoDTO.getListPageAddress() != null ) {
            rptBaseInfo.setListPageAddress( rtpBaseInfoDTO.getListPageAddress() );
        }
        if ( rtpBaseInfoDTO.getDifferentTypeMixed() != null ) {
            rptBaseInfo.setDifferentTypeMixed( rtpBaseInfoDTO.getDifferentTypeMixed() );
        }
        if ( rtpBaseInfoDTO.getInfoReleaseLevel() != null ) {
            rptBaseInfo.setInfoReleaseLevel( rtpBaseInfoDTO.getInfoReleaseLevel() );
        }
        if ( rtpBaseInfoDTO.getProvinceCode() != null ) {
            rptBaseInfo.setProvinceCode( rtpBaseInfoDTO.getProvinceCode() );
        }
        if ( rtpBaseInfoDTO.getCityCode() != null ) {
            rptBaseInfo.setCityCode( rtpBaseInfoDTO.getCityCode() );
        }
        if ( rtpBaseInfoDTO.getAreaCode() != null ) {
            rptBaseInfo.setAreaCode( rtpBaseInfoDTO.getAreaCode() );
        }
        if ( rtpBaseInfoDTO.getWebsiteNameCorrection() != null ) {
            rptBaseInfo.setWebsiteNameCorrection( rtpBaseInfoDTO.getWebsiteNameCorrection() );
        }
        if ( rtpBaseInfoDTO.getWebsiteUrlCorrection() != null ) {
            rptBaseInfo.setWebsiteUrlCorrection( rtpBaseInfoDTO.getWebsiteUrlCorrection() );
        }
        if ( rtpBaseInfoDTO.getRegionCode() != null ) {
            rptBaseInfo.setRegionCode( rtpBaseInfoDTO.getRegionCode() );
        }
        if ( rtpBaseInfoDTO.getExecutor() != null ) {
            rptBaseInfo.setExecutor( rtpBaseInfoDTO.getExecutor() );
        }
        if ( rtpBaseInfoDTO.getExecutorId() != null ) {
            rptBaseInfo.setExecutorId( rtpBaseInfoDTO.getExecutorId() );
        }
        if ( rtpBaseInfoDTO.getTimeInterval() != null ) {
            rptBaseInfo.setTimeInterval( rtpBaseInfoDTO.getTimeInterval() );
        }
        if ( rtpBaseInfoDTO.getAssignedPersonName() != null ) {
            rptBaseInfo.setAssignedPersonName( rtpBaseInfoDTO.getAssignedPersonName() );
        }
        if ( rtpBaseInfoDTO.getResponsiblePersonName() != null ) {
            rptBaseInfo.setResponsiblePersonName( rtpBaseInfoDTO.getResponsiblePersonName() );
        }
        if ( rtpBaseInfoDTO.getConfigStatus() != null ) {
            rptBaseInfo.setConfigStatus( rtpBaseInfoDTO.getConfigStatus() );
        }
        if ( rtpBaseInfoDTO.getDelFlag() != null ) {
            rptBaseInfo.setDelFlag( rtpBaseInfoDTO.getDelFlag() );
        }
        if ( rtpBaseInfoDTO.getDelUserName() != null ) {
            rptBaseInfo.setDelUserName( rtpBaseInfoDTO.getDelUserName() );
        }
        if ( rtpBaseInfoDTO.getDelDate() != null ) {
            rptBaseInfo.setDelDate( rtpBaseInfoDTO.getDelDate() );
        }
    }

    @Override
    public List<RptBaseInfoVO> of(List<RptBaseInfo> rptBaseInfoList) {
        if ( rptBaseInfoList == null ) {
            return null;
        }

        List<RptBaseInfoVO> list = new ArrayList<RptBaseInfoVO>( rptBaseInfoList.size() );
        for ( RptBaseInfo rptBaseInfo : rptBaseInfoList ) {
            list.add( userRtpBaseInfoVO( rptBaseInfo ) );
        }

        return list;
    }

    @Override
    public RptBaseInfo userVoToRtpBaseInfo(RptBaseInfoVO rptBaseInfoVO) {
        if ( rptBaseInfoVO == null ) {
            return null;
        }

        RptBaseInfo rptBaseInfo = new RptBaseInfo();

        rptBaseInfo.setId( rptBaseInfoVO.getId() );
        rptBaseInfo.setWebsiteName( rptBaseInfoVO.getWebsiteName() );
        rptBaseInfo.setWebsiteUrl( rptBaseInfoVO.getWebsiteUrl() );
        rptBaseInfo.setRunPeriod( rptBaseInfoVO.getRunPeriod() );
        rptBaseInfo.setStatus( rptBaseInfoVO.getStatus() );
        rptBaseInfo.setRunStatus( rptBaseInfoVO.getRunStatus() );
        rptBaseInfo.setConfigId( rptBaseInfoVO.getConfigId() );
        rptBaseInfo.setConfigName( rptBaseInfoVO.getConfigName() );
        rptBaseInfo.setGmtFunction( rptBaseInfoVO.getGmtFunction() );
        rptBaseInfo.setGmtCreate( rptBaseInfoVO.getGmtCreate() );
        rptBaseInfo.setCreateUsername( rptBaseInfoVO.getCreateUsername() );
        rptBaseInfo.setJobId( rptBaseInfoVO.getJobId() );
        rptBaseInfo.setDistributionDate( rptBaseInfoVO.getDistributionDate() );
        rptBaseInfo.setDeliveryDate( rptBaseInfoVO.getDeliveryDate() );
        rptBaseInfo.setSecondSiteType( rptBaseInfoVO.getSecondSiteType() );
        rptBaseInfo.setListPageAddress( rptBaseInfoVO.getListPageAddress() );
        rptBaseInfo.setDifferentTypeMixed( rptBaseInfoVO.getDifferentTypeMixed() );
        rptBaseInfo.setInfoReleaseLevel( rptBaseInfoVO.getInfoReleaseLevel() );
        rptBaseInfo.setProvinceCode( rptBaseInfoVO.getProvinceCode() );
        rptBaseInfo.setCityCode( rptBaseInfoVO.getCityCode() );
        rptBaseInfo.setAreaCode( rptBaseInfoVO.getAreaCode() );
        rptBaseInfo.setWebsiteNameCorrection( rptBaseInfoVO.getWebsiteNameCorrection() );
        rptBaseInfo.setWebsiteUrlCorrection( rptBaseInfoVO.getWebsiteUrlCorrection() );
        rptBaseInfo.setRegionCode( rptBaseInfoVO.getRegionCode() );
        rptBaseInfo.setExecutor( rptBaseInfoVO.getExecutor() );
        rptBaseInfo.setExecutorId( rptBaseInfoVO.getExecutorId() );
        rptBaseInfo.setTimeInterval( rptBaseInfoVO.getTimeInterval() );
        rptBaseInfo.setConfigDate( rptBaseInfoVO.getConfigDate() );
        rptBaseInfo.setAssignedPersonName( rptBaseInfoVO.getAssignedPersonName() );
        rptBaseInfo.setResponsiblePersonName( rptBaseInfoVO.getResponsiblePersonName() );
        rptBaseInfo.setConfigStatus( rptBaseInfoVO.getConfigStatus() );
        rptBaseInfo.setDelUserName( rptBaseInfoVO.getDelUserName() );
        rptBaseInfo.setDelDate( rptBaseInfoVO.getDelDate() );

        return rptBaseInfo;
    }

    @Override
    public RptBaseInfo of(RptBaseInfoBatchDTO rptBaseInfoBatchDTO) {
        if ( rptBaseInfoBatchDTO == null ) {
            return null;
        }

        RptBaseInfo rptBaseInfo = new RptBaseInfo();

        rptBaseInfo.setId( rptBaseInfoBatchDTO.getId() );
        rptBaseInfo.setWebsiteName( rptBaseInfoBatchDTO.getWebsiteName() );
        rptBaseInfo.setWebsiteUrl( rptBaseInfoBatchDTO.getWebsiteUrl() );
        rptBaseInfo.setRunPeriod( rptBaseInfoBatchDTO.getRunPeriod() );
        rptBaseInfo.setStatus( rptBaseInfoBatchDTO.getStatus() );
        rptBaseInfo.setRunStatus( rptBaseInfoBatchDTO.getRunStatus() );
        rptBaseInfo.setConfigId( rptBaseInfoBatchDTO.getConfigId() );
        rptBaseInfo.setConfigName( rptBaseInfoBatchDTO.getConfigName() );
        rptBaseInfo.setGmtFunction( rptBaseInfoBatchDTO.getGmtFunction() );
        rptBaseInfo.setGmtCreate( rptBaseInfoBatchDTO.getGmtCreate() );
        rptBaseInfo.setCreateUsername( rptBaseInfoBatchDTO.getCreateUsername() );
        rptBaseInfo.setJobId( rptBaseInfoBatchDTO.getJobId() );
        rptBaseInfo.setDistributionDate( rptBaseInfoBatchDTO.getDistributionDate() );
        rptBaseInfo.setDeliveryDate( rptBaseInfoBatchDTO.getDeliveryDate() );
        rptBaseInfo.setSecondSiteType( rptBaseInfoBatchDTO.getSecondSiteType() );
        rptBaseInfo.setListPageAddress( rptBaseInfoBatchDTO.getListPageAddress() );
        rptBaseInfo.setDifferentTypeMixed( rptBaseInfoBatchDTO.getDifferentTypeMixed() );
        rptBaseInfo.setInfoReleaseLevel( rptBaseInfoBatchDTO.getInfoReleaseLevel() );
        rptBaseInfo.setProvinceCode( rptBaseInfoBatchDTO.getProvinceCode() );
        rptBaseInfo.setCityCode( rptBaseInfoBatchDTO.getCityCode() );
        rptBaseInfo.setAreaCode( rptBaseInfoBatchDTO.getAreaCode() );
        rptBaseInfo.setWebsiteNameCorrection( rptBaseInfoBatchDTO.getWebsiteNameCorrection() );
        rptBaseInfo.setWebsiteUrlCorrection( rptBaseInfoBatchDTO.getWebsiteUrlCorrection() );
        rptBaseInfo.setRegionCode( rptBaseInfoBatchDTO.getRegionCode() );
        rptBaseInfo.setExecutor( rptBaseInfoBatchDTO.getExecutor() );
        rptBaseInfo.setExecutorId( rptBaseInfoBatchDTO.getExecutorId() );
        rptBaseInfo.setTimeInterval( rptBaseInfoBatchDTO.getTimeInterval() );
        rptBaseInfo.setAssignedPersonName( rptBaseInfoBatchDTO.getAssignedPersonName() );
        rptBaseInfo.setResponsiblePersonName( rptBaseInfoBatchDTO.getResponsiblePersonName() );
        rptBaseInfo.setConfigStatus( rptBaseInfoBatchDTO.getConfigStatus() );
        rptBaseInfo.setDelFlag( rptBaseInfoBatchDTO.getDelFlag() );
        rptBaseInfo.setDelUserName( rptBaseInfoBatchDTO.getDelUserName() );
        rptBaseInfo.setDelDate( rptBaseInfoBatchDTO.getDelDate() );

        return rptBaseInfo;
    }
}
