package com.qk.dm.indicator.mapstruct.mapper;

import com.qk.dm.indicator.entity.IdcAtom;
import com.qk.dm.indicator.params.dto.IdcAtomDTO;
import com.qk.dm.indicator.params.vo.IdcAtomPageVO;
import com.qk.dm.indicator.params.vo.IdcAtomVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T20:19:47+0800",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.9.1.jar, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class IdcAtomMapperImpl implements IdcAtomMapper {

    @Override
    public void useIdcAtom(IdcAtomDTO idcAtomDTO, IdcAtom idcAtom) {
        if ( idcAtomDTO == null ) {
            return;
        }

        if ( idcAtomDTO.getAtomIndicatorName() != null ) {
            idcAtom.setAtomIndicatorName( idcAtomDTO.getAtomIndicatorName() );
        }
        if ( idcAtomDTO.getAtomIndicatorCode() != null ) {
            idcAtom.setAtomIndicatorCode( idcAtomDTO.getAtomIndicatorCode() );
        }
        if ( idcAtomDTO.getDataSheet() != null ) {
            idcAtom.setDataSheet( idcAtomDTO.getDataSheet() );
        }
        if ( idcAtomDTO.getThemeCode() != null ) {
            idcAtom.setThemeCode( idcAtomDTO.getThemeCode() );
        }
        if ( idcAtomDTO.getExpression() != null ) {
            idcAtom.setExpression( idcAtomDTO.getExpression() );
        }
        if ( idcAtomDTO.getIndicatorStatus() != null ) {
            idcAtom.setIndicatorStatus( idcAtomDTO.getIndicatorStatus() );
        }
        if ( idcAtomDTO.getDescribeInfo() != null ) {
            idcAtom.setDescribeInfo( idcAtomDTO.getDescribeInfo() );
        }
        if ( idcAtomDTO.getSqlSentence() != null ) {
            idcAtom.setSqlSentence( idcAtomDTO.getSqlSentence() );
        }
    }

    @Override
    public IdcAtom useIdcAtom(IdcAtomDTO idcAtomDTO) {
        if ( idcAtomDTO == null ) {
            return null;
        }

        IdcAtom idcAtom = new IdcAtom();

        idcAtom.setAtomIndicatorName( idcAtomDTO.getAtomIndicatorName() );
        idcAtom.setAtomIndicatorCode( idcAtomDTO.getAtomIndicatorCode() );
        idcAtom.setDataSheet( idcAtomDTO.getDataSheet() );
        idcAtom.setThemeCode( idcAtomDTO.getThemeCode() );
        idcAtom.setExpression( idcAtomDTO.getExpression() );
        idcAtom.setIndicatorStatus( idcAtomDTO.getIndicatorStatus() );
        idcAtom.setDescribeInfo( idcAtomDTO.getDescribeInfo() );
        idcAtom.setSqlSentence( idcAtomDTO.getSqlSentence() );

        return idcAtom;
    }

    @Override
    public IdcAtomVO useIdcAtomVO(IdcAtom idcAtom) {
        if ( idcAtom == null ) {
            return null;
        }

        IdcAtomVO idcAtomVO = new IdcAtomVO();

        idcAtomVO.setId( idcAtom.getId() );
        idcAtomVO.setAtomIndicatorName( idcAtom.getAtomIndicatorName() );
        idcAtomVO.setAtomIndicatorCode( idcAtom.getAtomIndicatorCode() );
        idcAtomVO.setDataSheet( idcAtom.getDataSheet() );
        idcAtomVO.setThemeCode( idcAtom.getThemeCode() );
        idcAtomVO.setExpression( idcAtom.getExpression() );
        idcAtomVO.setIndicatorStatus( idcAtom.getIndicatorStatus() );
        idcAtomVO.setDescribeInfo( idcAtom.getDescribeInfo() );
        idcAtomVO.setSqlSentence( idcAtom.getSqlSentence() );

        return idcAtomVO;
    }

    @Override
    public List<IdcAtomVO> useIdcAtomVO(List<IdcAtom> idcAtom) {
        if ( idcAtom == null ) {
            return null;
        }

        List<IdcAtomVO> list = new ArrayList<IdcAtomVO>( idcAtom.size() );
        for ( IdcAtom idcAtom1 : idcAtom ) {
            list.add( useIdcAtomVO( idcAtom1 ) );
        }

        return list;
    }

    @Override
    public List<IdcAtomPageVO> useIdcAtomPageVO(List<IdcAtom> idcAtom) {
        if ( idcAtom == null ) {
            return null;
        }

        List<IdcAtomPageVO> list = new ArrayList<IdcAtomPageVO>( idcAtom.size() );
        for ( IdcAtom idcAtom1 : idcAtom ) {
            list.add( idcAtomToIdcAtomPageVO( idcAtom1 ) );
        }

        return list;
    }

    protected IdcAtomPageVO idcAtomToIdcAtomPageVO(IdcAtom idcAtom) {
        if ( idcAtom == null ) {
            return null;
        }

        IdcAtomPageVO idcAtomPageVO = new IdcAtomPageVO();

        idcAtomPageVO.setId( idcAtom.getId() );
        idcAtomPageVO.setAtomIndicatorName( idcAtom.getAtomIndicatorName() );
        idcAtomPageVO.setAtomIndicatorCode( idcAtom.getAtomIndicatorCode() );
        idcAtomPageVO.setThemeCode( idcAtom.getThemeCode() );
        idcAtomPageVO.setIndicatorStatus( idcAtom.getIndicatorStatus() );
        idcAtomPageVO.setGmtModified( idcAtom.getGmtModified() );

        return idcAtomPageVO;
    }
}
