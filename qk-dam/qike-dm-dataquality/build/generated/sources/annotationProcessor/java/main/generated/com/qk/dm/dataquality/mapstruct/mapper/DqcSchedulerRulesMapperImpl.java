package com.qk.dm.dataquality.mapstruct.mapper;

import com.qk.dm.dataquality.entity.DqcSchedulerRules;
import com.qk.dm.dataquality.vo.DqcSchedulerRulesVO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T17:36:29+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class DqcSchedulerRulesMapperImpl implements DqcSchedulerRulesMapper {

    @Override
    public DqcSchedulerRulesVO userDqcSchedulerRulesVO(DqcSchedulerRules dqcSchedulerRules) {
        if ( dqcSchedulerRules == null ) {
            return null;
        }

        DqcSchedulerRulesVO dqcSchedulerRulesVO = new DqcSchedulerRulesVO();

        dqcSchedulerRulesVO.setId( dqcSchedulerRules.getId() );
        dqcSchedulerRulesVO.setRuleId( dqcSchedulerRules.getRuleId() );
        dqcSchedulerRulesVO.setRuleName( dqcSchedulerRules.getRuleName() );
        dqcSchedulerRulesVO.setJobId( dqcSchedulerRules.getJobId() );
        dqcSchedulerRulesVO.setRuleTempId( dqcSchedulerRules.getRuleTempId() );
        dqcSchedulerRulesVO.setRuleType( dqcSchedulerRules.getRuleType() );
        dqcSchedulerRulesVO.setTaskCode( dqcSchedulerRules.getTaskCode() );
        dqcSchedulerRulesVO.setEngineType( dqcSchedulerRules.getEngineType() );
        dqcSchedulerRulesVO.setDataSourceName( dqcSchedulerRules.getDataSourceName() );
        dqcSchedulerRulesVO.setDatabaseName( dqcSchedulerRules.getDatabaseName() );
        dqcSchedulerRulesVO.setScanType( dqcSchedulerRules.getScanType() );
        dqcSchedulerRulesVO.setScanSql( dqcSchedulerRules.getScanSql() );
        dqcSchedulerRulesVO.setExecuteSql( dqcSchedulerRules.getExecuteSql() );
        dqcSchedulerRulesVO.setWarnExpression( dqcSchedulerRules.getWarnExpression() );
        dqcSchedulerRulesVO.setCreateUserid( dqcSchedulerRules.getCreateUserid() );
        dqcSchedulerRulesVO.setUpdateUserid( dqcSchedulerRules.getUpdateUserid() );
        dqcSchedulerRulesVO.setDelFlag( dqcSchedulerRules.getDelFlag() );
        dqcSchedulerRulesVO.setGmtCreate( dqcSchedulerRules.getGmtCreate() );
        dqcSchedulerRulesVO.setGmtModified( dqcSchedulerRules.getGmtModified() );

        return dqcSchedulerRulesVO;
    }

    @Override
    public DqcSchedulerRules userDqcSchedulerRules(DqcSchedulerRulesVO dqcSchedulerRulesVO) {
        if ( dqcSchedulerRulesVO == null ) {
            return null;
        }

        DqcSchedulerRules dqcSchedulerRules = new DqcSchedulerRules();

        dqcSchedulerRules.setId( dqcSchedulerRulesVO.getId() );
        dqcSchedulerRules.setRuleId( dqcSchedulerRulesVO.getRuleId() );
        dqcSchedulerRules.setRuleName( dqcSchedulerRulesVO.getRuleName() );
        dqcSchedulerRules.setJobId( dqcSchedulerRulesVO.getJobId() );
        dqcSchedulerRules.setRuleTempId( dqcSchedulerRulesVO.getRuleTempId() );
        dqcSchedulerRules.setRuleType( dqcSchedulerRulesVO.getRuleType() );
        dqcSchedulerRules.setTaskCode( dqcSchedulerRulesVO.getTaskCode() );
        dqcSchedulerRules.setEngineType( dqcSchedulerRulesVO.getEngineType() );
        dqcSchedulerRules.setDataSourceName( dqcSchedulerRulesVO.getDataSourceName() );
        dqcSchedulerRules.setDatabaseName( dqcSchedulerRulesVO.getDatabaseName() );
        dqcSchedulerRules.setScanType( dqcSchedulerRulesVO.getScanType() );
        dqcSchedulerRules.setWarnExpression( dqcSchedulerRulesVO.getWarnExpression() );
        dqcSchedulerRules.setCreateUserid( dqcSchedulerRulesVO.getCreateUserid() );
        dqcSchedulerRules.setUpdateUserid( dqcSchedulerRulesVO.getUpdateUserid() );
        dqcSchedulerRules.setDelFlag( dqcSchedulerRulesVO.getDelFlag() );
        dqcSchedulerRules.setGmtCreate( dqcSchedulerRulesVO.getGmtCreate() );
        dqcSchedulerRules.setGmtModified( dqcSchedulerRulesVO.getGmtModified() );
        dqcSchedulerRules.setScanSql( dqcSchedulerRulesVO.getScanSql() );
        dqcSchedulerRules.setExecuteSql( dqcSchedulerRulesVO.getExecuteSql() );

        return dqcSchedulerRules;
    }
}
