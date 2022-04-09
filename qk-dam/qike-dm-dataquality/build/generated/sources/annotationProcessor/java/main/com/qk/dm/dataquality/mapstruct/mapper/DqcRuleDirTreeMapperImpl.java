package com.qk.dm.dataquality.mapstruct.mapper;

import com.qk.dm.dataquality.entity.DqcRuleDir;
import com.qk.dm.dataquality.vo.DqcRuleDirTreeVO;
import com.qk.dm.dataquality.vo.DqcRuleDirVO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T20:19:50+0800",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.9.1.jar, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class DqcRuleDirTreeMapperImpl implements DqcRuleDirTreeMapper {

    @Override
    public DqcRuleDirTreeVO useDqcRuleDirTreeVO(DqcRuleDir dqcRuleDir) {
        if ( dqcRuleDir == null ) {
            return null;
        }

        DqcRuleDirTreeVO dqcRuleDirTreeVO = new DqcRuleDirTreeVO();

        dqcRuleDirTreeVO.setId( dqcRuleDir.getId() );
        dqcRuleDirTreeVO.setDirId( dqcRuleDir.getRuleDirId() );
        dqcRuleDirTreeVO.setTitle( dqcRuleDir.getRuleDirName() );
        dqcRuleDirTreeVO.setValue( dqcRuleDir.getRuleDirId() );
        dqcRuleDirTreeVO.setParentId( dqcRuleDir.getParentId() );

        return dqcRuleDirTreeVO;
    }

    @Override
    public DqcRuleDir useDqcRuleDir(DqcRuleDirVO dqcRuleDirVO) {
        if ( dqcRuleDirVO == null ) {
            return null;
        }

        DqcRuleDir dqcRuleDir = new DqcRuleDir();

        dqcRuleDir.setRuleDirId( dqcRuleDirVO.getDirId() );
        dqcRuleDir.setRuleDirName( dqcRuleDirVO.getTitle() );
        dqcRuleDir.setParentId( dqcRuleDirVO.getParentId() );
        dqcRuleDir.setId( dqcRuleDirVO.getId() );
        dqcRuleDir.setDescription( dqcRuleDirVO.getDescription() );
        dqcRuleDir.setGmtCreate( dqcRuleDirVO.getGmtCreate() );
        dqcRuleDir.setGmtModified( dqcRuleDirVO.getGmtModified() );
        dqcRuleDir.setDelFlag( dqcRuleDirVO.getDelFlag() );

        return dqcRuleDir;
    }
}
