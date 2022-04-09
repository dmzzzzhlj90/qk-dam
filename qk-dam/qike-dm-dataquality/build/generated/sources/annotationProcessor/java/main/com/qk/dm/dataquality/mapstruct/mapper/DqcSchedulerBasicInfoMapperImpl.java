package com.qk.dm.dataquality.mapstruct.mapper;

import com.qk.dm.dataquality.constant.NotifyStateEnum;
import com.qk.dm.dataquality.entity.DqcSchedulerBasicInfo;
import com.qk.dm.dataquality.vo.DqcSchedulerBasicInfoVO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T20:19:50+0800",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.9.1.jar, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class DqcSchedulerBasicInfoMapperImpl implements DqcSchedulerBasicInfoMapper {

    @Override
    public DqcSchedulerBasicInfoVO userDqcSchedulerBasicInfoVO(DqcSchedulerBasicInfo dqcSchedulerBasicInfo) {
        if ( dqcSchedulerBasicInfo == null ) {
            return null;
        }

        DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO = new DqcSchedulerBasicInfoVO();

        if ( dqcSchedulerBasicInfo.getId() != null ) {
            dqcSchedulerBasicInfoVO.setId( dqcSchedulerBasicInfo.getId() );
        }
        if ( dqcSchedulerBasicInfo.getJobId() != null ) {
            dqcSchedulerBasicInfoVO.setJobId( dqcSchedulerBasicInfo.getJobId() );
        }
        if ( dqcSchedulerBasicInfo.getJobName() != null ) {
            dqcSchedulerBasicInfoVO.setJobName( dqcSchedulerBasicInfo.getJobName() );
        }
        if ( dqcSchedulerBasicInfo.getDirId() != null ) {
            dqcSchedulerBasicInfoVO.setDirId( dqcSchedulerBasicInfo.getDirId() );
        }
        if ( dqcSchedulerBasicInfo.getProcessDefinitionCode() != null ) {
            dqcSchedulerBasicInfoVO.setProcessDefinitionCode( dqcSchedulerBasicInfo.getProcessDefinitionCode() );
        }
        if ( dqcSchedulerBasicInfo.getNotifyLevel() != null ) {
            dqcSchedulerBasicInfoVO.setNotifyLevel( dqcSchedulerBasicInfo.getNotifyLevel() );
        }
        if ( dqcSchedulerBasicInfo.getNotifyType() != null ) {
            dqcSchedulerBasicInfoVO.setNotifyType( dqcSchedulerBasicInfo.getNotifyType() );
        }
        if ( dqcSchedulerBasicInfo.getSchedulerState() != null ) {
            dqcSchedulerBasicInfoVO.setSchedulerState( dqcSchedulerBasicInfo.getSchedulerState() );
        }
        if ( dqcSchedulerBasicInfo.getRunInstanceState() != null ) {
            dqcSchedulerBasicInfoVO.setRunInstanceState( dqcSchedulerBasicInfo.getRunInstanceState() );
        }
        if ( dqcSchedulerBasicInfo.getCreateUserid() != null ) {
            dqcSchedulerBasicInfoVO.setCreateUserid( dqcSchedulerBasicInfo.getCreateUserid() );
        }
        if ( dqcSchedulerBasicInfo.getUpdateUserid() != null ) {
            dqcSchedulerBasicInfoVO.setUpdateUserid( dqcSchedulerBasicInfo.getUpdateUserid() );
        }
        if ( dqcSchedulerBasicInfo.getGmtCreate() != null ) {
            dqcSchedulerBasicInfoVO.setGmtCreate( dqcSchedulerBasicInfo.getGmtCreate() );
        }
        if ( dqcSchedulerBasicInfo.getGmtModified() != null ) {
            dqcSchedulerBasicInfoVO.setGmtModified( dqcSchedulerBasicInfo.getGmtModified() );
        }

        dqcSchedulerBasicInfoVO.setNotifyState( NotifyStateEnum.conversionType(dqcSchedulerBasicInfo.getNotifyState()) );

        return dqcSchedulerBasicInfoVO;
    }

    @Override
    public DqcSchedulerBasicInfo userDqcSchedulerBasicInfo(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO) {
        if ( dqcSchedulerBasicInfoVO == null ) {
            return null;
        }

        DqcSchedulerBasicInfo dqcSchedulerBasicInfo = new DqcSchedulerBasicInfo();

        if ( dqcSchedulerBasicInfoVO.getId() != null ) {
            dqcSchedulerBasicInfo.setId( dqcSchedulerBasicInfoVO.getId() );
        }
        if ( dqcSchedulerBasicInfoVO.getJobId() != null ) {
            dqcSchedulerBasicInfo.setJobId( dqcSchedulerBasicInfoVO.getJobId() );
        }
        if ( dqcSchedulerBasicInfoVO.getJobName() != null ) {
            dqcSchedulerBasicInfo.setJobName( dqcSchedulerBasicInfoVO.getJobName() );
        }
        if ( dqcSchedulerBasicInfoVO.getDirId() != null ) {
            dqcSchedulerBasicInfo.setDirId( dqcSchedulerBasicInfoVO.getDirId() );
        }
        if ( dqcSchedulerBasicInfoVO.getProcessDefinitionCode() != null ) {
            dqcSchedulerBasicInfo.setProcessDefinitionCode( dqcSchedulerBasicInfoVO.getProcessDefinitionCode() );
        }
        if ( dqcSchedulerBasicInfoVO.getNotifyLevel() != null ) {
            dqcSchedulerBasicInfo.setNotifyLevel( dqcSchedulerBasicInfoVO.getNotifyLevel() );
        }
        if ( dqcSchedulerBasicInfoVO.getNotifyType() != null ) {
            dqcSchedulerBasicInfo.setNotifyType( dqcSchedulerBasicInfoVO.getNotifyType() );
        }
        if ( dqcSchedulerBasicInfoVO.getSchedulerState() != null ) {
            dqcSchedulerBasicInfo.setSchedulerState( dqcSchedulerBasicInfoVO.getSchedulerState() );
        }
        if ( dqcSchedulerBasicInfoVO.getRunInstanceState() != null ) {
            dqcSchedulerBasicInfo.setRunInstanceState( dqcSchedulerBasicInfoVO.getRunInstanceState() );
        }
        if ( dqcSchedulerBasicInfoVO.getCreateUserid() != null ) {
            dqcSchedulerBasicInfo.setCreateUserid( dqcSchedulerBasicInfoVO.getCreateUserid() );
        }
        if ( dqcSchedulerBasicInfoVO.getUpdateUserid() != null ) {
            dqcSchedulerBasicInfo.setUpdateUserid( dqcSchedulerBasicInfoVO.getUpdateUserid() );
        }
        if ( dqcSchedulerBasicInfoVO.getGmtCreate() != null ) {
            dqcSchedulerBasicInfo.setGmtCreate( dqcSchedulerBasicInfoVO.getGmtCreate() );
        }
        if ( dqcSchedulerBasicInfoVO.getGmtModified() != null ) {
            dqcSchedulerBasicInfo.setGmtModified( dqcSchedulerBasicInfoVO.getGmtModified() );
        }

        dqcSchedulerBasicInfo.setNotifyState( NotifyStateEnum.conversionType(dqcSchedulerBasicInfoVO.getNotifyState()) );

        return dqcSchedulerBasicInfo;
    }

    @Override
    public void toDqcSchedulerBasicInfo(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO, DqcSchedulerBasicInfo dqcSchedulerBasicInfo) {
        if ( dqcSchedulerBasicInfoVO == null ) {
            return;
        }

        if ( dqcSchedulerBasicInfoVO.getId() != null ) {
            dqcSchedulerBasicInfo.setId( dqcSchedulerBasicInfoVO.getId() );
        }
        if ( dqcSchedulerBasicInfoVO.getJobId() != null ) {
            dqcSchedulerBasicInfo.setJobId( dqcSchedulerBasicInfoVO.getJobId() );
        }
        if ( dqcSchedulerBasicInfoVO.getJobName() != null ) {
            dqcSchedulerBasicInfo.setJobName( dqcSchedulerBasicInfoVO.getJobName() );
        }
        if ( dqcSchedulerBasicInfoVO.getDirId() != null ) {
            dqcSchedulerBasicInfo.setDirId( dqcSchedulerBasicInfoVO.getDirId() );
        }
        if ( dqcSchedulerBasicInfoVO.getProcessDefinitionCode() != null ) {
            dqcSchedulerBasicInfo.setProcessDefinitionCode( dqcSchedulerBasicInfoVO.getProcessDefinitionCode() );
        }
        if ( dqcSchedulerBasicInfoVO.getNotifyLevel() != null ) {
            dqcSchedulerBasicInfo.setNotifyLevel( dqcSchedulerBasicInfoVO.getNotifyLevel() );
        }
        if ( dqcSchedulerBasicInfoVO.getNotifyType() != null ) {
            dqcSchedulerBasicInfo.setNotifyType( dqcSchedulerBasicInfoVO.getNotifyType() );
        }
        if ( dqcSchedulerBasicInfoVO.getSchedulerState() != null ) {
            dqcSchedulerBasicInfo.setSchedulerState( dqcSchedulerBasicInfoVO.getSchedulerState() );
        }
        if ( dqcSchedulerBasicInfoVO.getRunInstanceState() != null ) {
            dqcSchedulerBasicInfo.setRunInstanceState( dqcSchedulerBasicInfoVO.getRunInstanceState() );
        }
        if ( dqcSchedulerBasicInfoVO.getCreateUserid() != null ) {
            dqcSchedulerBasicInfo.setCreateUserid( dqcSchedulerBasicInfoVO.getCreateUserid() );
        }
        if ( dqcSchedulerBasicInfoVO.getUpdateUserid() != null ) {
            dqcSchedulerBasicInfo.setUpdateUserid( dqcSchedulerBasicInfoVO.getUpdateUserid() );
        }
        if ( dqcSchedulerBasicInfoVO.getGmtCreate() != null ) {
            dqcSchedulerBasicInfo.setGmtCreate( dqcSchedulerBasicInfoVO.getGmtCreate() );
        }
        if ( dqcSchedulerBasicInfoVO.getGmtModified() != null ) {
            dqcSchedulerBasicInfo.setGmtModified( dqcSchedulerBasicInfoVO.getGmtModified() );
        }

        dqcSchedulerBasicInfo.setNotifyState( NotifyStateEnum.conversionType(dqcSchedulerBasicInfoVO.getNotifyState()) );
    }
}
