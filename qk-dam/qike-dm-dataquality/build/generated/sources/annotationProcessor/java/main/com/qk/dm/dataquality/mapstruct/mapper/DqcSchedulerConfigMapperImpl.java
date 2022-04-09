package com.qk.dm.dataquality.mapstruct.mapper;

import com.qk.dm.dataquality.entity.DqcSchedulerConfig;
import com.qk.dm.dataquality.vo.DqcSchedulerConfigVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T20:19:50+0800",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.9.1.jar, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class DqcSchedulerConfigMapperImpl implements DqcSchedulerConfigMapper {

    @Override
    public DqcSchedulerConfigVO userDqcSchedulerConfigVO(DqcSchedulerConfig dqcSchedulerConfig) {
        if ( dqcSchedulerConfig == null ) {
            return null;
        }

        DqcSchedulerConfigVO dqcSchedulerConfigVO = new DqcSchedulerConfigVO();

        if ( dqcSchedulerConfig.getId() != null ) {
            dqcSchedulerConfigVO.setId( dqcSchedulerConfig.getId() );
        }
        if ( dqcSchedulerConfig.getJobId() != null ) {
            dqcSchedulerConfigVO.setJobId( dqcSchedulerConfig.getJobId() );
        }
        if ( dqcSchedulerConfig.getSchedulerType() != null ) {
            dqcSchedulerConfigVO.setSchedulerType( dqcSchedulerConfig.getSchedulerType() );
        }
        if ( dqcSchedulerConfig.getEffectiveTimeStart() != null ) {
            dqcSchedulerConfigVO.setEffectiveTimeStart( dqcSchedulerConfig.getEffectiveTimeStart() );
        }
        if ( dqcSchedulerConfig.getEffectiveTimeEnt() != null ) {
            dqcSchedulerConfigVO.setEffectiveTimeEnt( dqcSchedulerConfig.getEffectiveTimeEnt() );
        }
        if ( dqcSchedulerConfig.getSchedulerCycle() != null ) {
            dqcSchedulerConfigVO.setSchedulerCycle( dqcSchedulerConfig.getSchedulerCycle() );
        }
        if ( dqcSchedulerConfig.getSchedulerIntervalTime() != null ) {
            dqcSchedulerConfigVO.setSchedulerIntervalTime( dqcSchedulerConfig.getSchedulerIntervalTime() );
        }
        if ( dqcSchedulerConfig.getSchedulerTime() != null ) {
            dqcSchedulerConfigVO.setSchedulerTime( dqcSchedulerConfig.getSchedulerTime() );
        }
        if ( dqcSchedulerConfig.getCron() != null ) {
            dqcSchedulerConfigVO.setCron( dqcSchedulerConfig.getCron() );
        }
        if ( dqcSchedulerConfig.getSchedulerId() != null ) {
            dqcSchedulerConfigVO.setSchedulerId( dqcSchedulerConfig.getSchedulerId() );
        }
        if ( dqcSchedulerConfig.getCreateUserid() != null ) {
            dqcSchedulerConfigVO.setCreateUserid( dqcSchedulerConfig.getCreateUserid() );
        }
        if ( dqcSchedulerConfig.getUpdateUserid() != null ) {
            dqcSchedulerConfigVO.setUpdateUserid( dqcSchedulerConfig.getUpdateUserid() );
        }
        if ( dqcSchedulerConfig.getGmtCreate() != null ) {
            dqcSchedulerConfigVO.setGmtCreate( dqcSchedulerConfig.getGmtCreate() );
        }
        if ( dqcSchedulerConfig.getGmtModified() != null ) {
            dqcSchedulerConfigVO.setGmtModified( dqcSchedulerConfig.getGmtModified() );
        }

        return dqcSchedulerConfigVO;
    }

    @Override
    public List<DqcSchedulerConfigVO> userDqcSchedulerConfigVO(List<DqcSchedulerConfig> dqcSchedulerConfig) {
        if ( dqcSchedulerConfig == null ) {
            return null;
        }

        List<DqcSchedulerConfigVO> list = new ArrayList<DqcSchedulerConfigVO>( dqcSchedulerConfig.size() );
        for ( DqcSchedulerConfig dqcSchedulerConfig1 : dqcSchedulerConfig ) {
            list.add( userDqcSchedulerConfigVO( dqcSchedulerConfig1 ) );
        }

        return list;
    }

    @Override
    public DqcSchedulerConfig userDqcSchedulerConfig(DqcSchedulerConfigVO dqcSchedulerConfig) {
        if ( dqcSchedulerConfig == null ) {
            return null;
        }

        DqcSchedulerConfig dqcSchedulerConfig1 = new DqcSchedulerConfig();

        if ( dqcSchedulerConfig.getId() != null ) {
            dqcSchedulerConfig1.setId( dqcSchedulerConfig.getId() );
        }
        if ( dqcSchedulerConfig.getJobId() != null ) {
            dqcSchedulerConfig1.setJobId( dqcSchedulerConfig.getJobId() );
        }
        if ( dqcSchedulerConfig.getSchedulerType() != null ) {
            dqcSchedulerConfig1.setSchedulerType( dqcSchedulerConfig.getSchedulerType() );
        }
        if ( dqcSchedulerConfig.getSchedulerCycle() != null ) {
            dqcSchedulerConfig1.setSchedulerCycle( dqcSchedulerConfig.getSchedulerCycle() );
        }
        if ( dqcSchedulerConfig.getSchedulerIntervalTime() != null ) {
            dqcSchedulerConfig1.setSchedulerIntervalTime( dqcSchedulerConfig.getSchedulerIntervalTime() );
        }
        if ( dqcSchedulerConfig.getEffectiveTimeStart() != null ) {
            dqcSchedulerConfig1.setEffectiveTimeStart( dqcSchedulerConfig.getEffectiveTimeStart() );
        }
        if ( dqcSchedulerConfig.getEffectiveTimeEnt() != null ) {
            dqcSchedulerConfig1.setEffectiveTimeEnt( dqcSchedulerConfig.getEffectiveTimeEnt() );
        }
        if ( dqcSchedulerConfig.getSchedulerTime() != null ) {
            dqcSchedulerConfig1.setSchedulerTime( dqcSchedulerConfig.getSchedulerTime() );
        }
        if ( dqcSchedulerConfig.getCron() != null ) {
            dqcSchedulerConfig1.setCron( dqcSchedulerConfig.getCron() );
        }
        if ( dqcSchedulerConfig.getSchedulerId() != null ) {
            dqcSchedulerConfig1.setSchedulerId( dqcSchedulerConfig.getSchedulerId() );
        }
        if ( dqcSchedulerConfig.getCreateUserid() != null ) {
            dqcSchedulerConfig1.setCreateUserid( dqcSchedulerConfig.getCreateUserid() );
        }
        if ( dqcSchedulerConfig.getUpdateUserid() != null ) {
            dqcSchedulerConfig1.setUpdateUserid( dqcSchedulerConfig.getUpdateUserid() );
        }
        if ( dqcSchedulerConfig.getGmtCreate() != null ) {
            dqcSchedulerConfig1.setGmtCreate( dqcSchedulerConfig.getGmtCreate() );
        }
        if ( dqcSchedulerConfig.getGmtModified() != null ) {
            dqcSchedulerConfig1.setGmtModified( dqcSchedulerConfig.getGmtModified() );
        }

        return dqcSchedulerConfig1;
    }

    @Override
    public void userDqcSchedulerConfig(DqcSchedulerConfigVO dqcSchedulerConfigVO, DqcSchedulerConfig dqcSchedulerConfig) {
        if ( dqcSchedulerConfigVO == null ) {
            return;
        }

        if ( dqcSchedulerConfigVO.getId() != null ) {
            dqcSchedulerConfig.setId( dqcSchedulerConfigVO.getId() );
        }
        if ( dqcSchedulerConfigVO.getJobId() != null ) {
            dqcSchedulerConfig.setJobId( dqcSchedulerConfigVO.getJobId() );
        }
        if ( dqcSchedulerConfigVO.getSchedulerType() != null ) {
            dqcSchedulerConfig.setSchedulerType( dqcSchedulerConfigVO.getSchedulerType() );
        }
        if ( dqcSchedulerConfigVO.getSchedulerCycle() != null ) {
            dqcSchedulerConfig.setSchedulerCycle( dqcSchedulerConfigVO.getSchedulerCycle() );
        }
        if ( dqcSchedulerConfigVO.getSchedulerIntervalTime() != null ) {
            dqcSchedulerConfig.setSchedulerIntervalTime( dqcSchedulerConfigVO.getSchedulerIntervalTime() );
        }
        if ( dqcSchedulerConfigVO.getEffectiveTimeStart() != null ) {
            dqcSchedulerConfig.setEffectiveTimeStart( dqcSchedulerConfigVO.getEffectiveTimeStart() );
        }
        if ( dqcSchedulerConfigVO.getEffectiveTimeEnt() != null ) {
            dqcSchedulerConfig.setEffectiveTimeEnt( dqcSchedulerConfigVO.getEffectiveTimeEnt() );
        }
        if ( dqcSchedulerConfigVO.getSchedulerTime() != null ) {
            dqcSchedulerConfig.setSchedulerTime( dqcSchedulerConfigVO.getSchedulerTime() );
        }
        if ( dqcSchedulerConfigVO.getCron() != null ) {
            dqcSchedulerConfig.setCron( dqcSchedulerConfigVO.getCron() );
        }
        if ( dqcSchedulerConfigVO.getSchedulerId() != null ) {
            dqcSchedulerConfig.setSchedulerId( dqcSchedulerConfigVO.getSchedulerId() );
        }
        if ( dqcSchedulerConfigVO.getCreateUserid() != null ) {
            dqcSchedulerConfig.setCreateUserid( dqcSchedulerConfigVO.getCreateUserid() );
        }
        if ( dqcSchedulerConfigVO.getUpdateUserid() != null ) {
            dqcSchedulerConfig.setUpdateUserid( dqcSchedulerConfigVO.getUpdateUserid() );
        }
        if ( dqcSchedulerConfigVO.getGmtCreate() != null ) {
            dqcSchedulerConfig.setGmtCreate( dqcSchedulerConfigVO.getGmtCreate() );
        }
        if ( dqcSchedulerConfigVO.getGmtModified() != null ) {
            dqcSchedulerConfig.setGmtModified( dqcSchedulerConfigVO.getGmtModified() );
        }
    }
}
