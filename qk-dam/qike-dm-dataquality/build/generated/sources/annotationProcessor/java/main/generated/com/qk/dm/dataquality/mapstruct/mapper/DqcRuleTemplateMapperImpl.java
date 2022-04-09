package com.qk.dm.dataquality.mapstruct.mapper;

import com.qk.dm.dataquality.entity.DqcRuleTemplate;
import com.qk.dm.dataquality.vo.DqcRuleTemplateInfoVO;
import com.qk.dm.dataquality.vo.DqcRuleTemplateVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T17:36:29+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class DqcRuleTemplateMapperImpl implements DqcRuleTemplateMapper {

    @Override
    public DqcRuleTemplate userDqcRuleTemplate(DqcRuleTemplateVO dqcRuleTemplateVo) {
        if ( dqcRuleTemplateVo == null ) {
            return null;
        }

        DqcRuleTemplate dqcRuleTemplate = new DqcRuleTemplate();

        if ( dqcRuleTemplateVo.getId() != null ) {
            dqcRuleTemplate.setId( dqcRuleTemplateVo.getId() );
        }
        if ( dqcRuleTemplateVo.getTempName() != null ) {
            dqcRuleTemplate.setTempName( dqcRuleTemplateVo.getTempName() );
        }
        if ( dqcRuleTemplateVo.getTempType() != null ) {
            dqcRuleTemplate.setTempType( dqcRuleTemplateVo.getTempType() );
        }
        if ( dqcRuleTemplateVo.getDirId() != null ) {
            dqcRuleTemplate.setDirId( dqcRuleTemplateVo.getDirId() );
        }
        if ( dqcRuleTemplateVo.getDimensionType() != null ) {
            dqcRuleTemplate.setDimensionType( dqcRuleTemplateVo.getDimensionType() );
        }
        if ( dqcRuleTemplateVo.getDescription() != null ) {
            dqcRuleTemplate.setDescription( dqcRuleTemplateVo.getDescription() );
        }
        if ( dqcRuleTemplateVo.getTempSql() != null ) {
            dqcRuleTemplate.setTempSql( dqcRuleTemplateVo.getTempSql() );
        }
        if ( dqcRuleTemplateVo.getTempResult() != null ) {
            dqcRuleTemplate.setTempResult( dqcRuleTemplateVo.getTempResult() );
        }
        if ( dqcRuleTemplateVo.getRuleType() != null ) {
            dqcRuleTemplate.setRuleType( dqcRuleTemplateVo.getRuleType() );
        }

        return dqcRuleTemplate;
    }

    @Override
    public List<DqcRuleTemplateInfoVO> userDqcRuleTemplateInfoVo(List<DqcRuleTemplate> dqcRuleTemplateList) {
        if ( dqcRuleTemplateList == null ) {
            return null;
        }

        List<DqcRuleTemplateInfoVO> list = new ArrayList<DqcRuleTemplateInfoVO>( dqcRuleTemplateList.size() );
        for ( DqcRuleTemplate dqcRuleTemplate : dqcRuleTemplateList ) {
            list.add( userDqcRuleTemplateInfoVo( dqcRuleTemplate ) );
        }

        return list;
    }

    @Override
    public DqcRuleTemplateInfoVO userDqcRuleTemplateInfoVo(DqcRuleTemplate dqcRuleTemplate) {
        if ( dqcRuleTemplate == null ) {
            return null;
        }

        DqcRuleTemplateInfoVO dqcRuleTemplateInfoVO = new DqcRuleTemplateInfoVO();

        if ( dqcRuleTemplate.getId() != null ) {
            dqcRuleTemplateInfoVO.setId( dqcRuleTemplate.getId().intValue() );
        }
        if ( dqcRuleTemplate.getTempName() != null ) {
            dqcRuleTemplateInfoVO.setTempName( dqcRuleTemplate.getTempName() );
        }
        if ( dqcRuleTemplate.getTempType() != null ) {
            dqcRuleTemplateInfoVO.setTempType( dqcRuleTemplate.getTempType() );
        }
        if ( dqcRuleTemplate.getDirId() != null ) {
            dqcRuleTemplateInfoVO.setDirId( dqcRuleTemplate.getDirId() );
        }
        if ( dqcRuleTemplate.getDimensionType() != null ) {
            dqcRuleTemplateInfoVO.setDimensionType( dqcRuleTemplate.getDimensionType() );
        }
        if ( dqcRuleTemplate.getDescription() != null ) {
            dqcRuleTemplateInfoVO.setDescription( dqcRuleTemplate.getDescription() );
        }
        if ( dqcRuleTemplate.getTempSql() != null ) {
            dqcRuleTemplateInfoVO.setTempSql( dqcRuleTemplate.getTempSql() );
        }
        if ( dqcRuleTemplate.getTempResult() != null ) {
            dqcRuleTemplateInfoVO.setTempResult( dqcRuleTemplate.getTempResult() );
        }
        if ( dqcRuleTemplate.getRuleType() != null ) {
            dqcRuleTemplateInfoVO.setRuleType( dqcRuleTemplate.getRuleType() );
        }
        if ( dqcRuleTemplate.getPublishState() != null ) {
            dqcRuleTemplateInfoVO.setPublishState( dqcRuleTemplate.getPublishState() );
        }
        if ( dqcRuleTemplate.getCreateUserid() != null ) {
            dqcRuleTemplateInfoVO.setCreateUserid( dqcRuleTemplate.getCreateUserid() );
        }
        if ( dqcRuleTemplate.getGmtModified() != null ) {
            dqcRuleTemplateInfoVO.setGmtModified( dqcRuleTemplate.getGmtModified() );
        }

        return dqcRuleTemplateInfoVO;
    }

    @Override
    public void userDqcRuleTemplate(DqcRuleTemplateVO dqcRuleTemplateVo, DqcRuleTemplate dqcRuleTemplate) {
        if ( dqcRuleTemplateVo == null ) {
            return;
        }

        if ( dqcRuleTemplateVo.getId() != null ) {
            dqcRuleTemplate.setId( dqcRuleTemplateVo.getId() );
        }
        if ( dqcRuleTemplateVo.getTempName() != null ) {
            dqcRuleTemplate.setTempName( dqcRuleTemplateVo.getTempName() );
        }
        if ( dqcRuleTemplateVo.getTempType() != null ) {
            dqcRuleTemplate.setTempType( dqcRuleTemplateVo.getTempType() );
        }
        if ( dqcRuleTemplateVo.getDirId() != null ) {
            dqcRuleTemplate.setDirId( dqcRuleTemplateVo.getDirId() );
        }
        if ( dqcRuleTemplateVo.getDimensionType() != null ) {
            dqcRuleTemplate.setDimensionType( dqcRuleTemplateVo.getDimensionType() );
        }
        if ( dqcRuleTemplateVo.getDescription() != null ) {
            dqcRuleTemplate.setDescription( dqcRuleTemplateVo.getDescription() );
        }
        if ( dqcRuleTemplateVo.getTempSql() != null ) {
            dqcRuleTemplate.setTempSql( dqcRuleTemplateVo.getTempSql() );
        }
        if ( dqcRuleTemplateVo.getTempResult() != null ) {
            dqcRuleTemplate.setTempResult( dqcRuleTemplateVo.getTempResult() );
        }
        if ( dqcRuleTemplateVo.getRuleType() != null ) {
            dqcRuleTemplate.setRuleType( dqcRuleTemplateVo.getRuleType() );
        }
    }
}
