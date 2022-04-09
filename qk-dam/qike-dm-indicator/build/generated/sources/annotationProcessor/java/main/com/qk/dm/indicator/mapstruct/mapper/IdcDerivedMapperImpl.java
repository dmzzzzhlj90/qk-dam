package com.qk.dm.indicator.mapstruct.mapper;

import com.qk.dm.indicator.entity.IdcDerived;
import com.qk.dm.indicator.params.dto.IdcDerivedDTO;
import com.qk.dm.indicator.params.vo.IdcDerivedVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T20:19:47+0800",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.9.1.jar, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class IdcDerivedMapperImpl implements IdcDerivedMapper {

    @Override
    public IdcDerived useIdcDerived(IdcDerivedDTO idcDerivedDTO) {
        if ( idcDerivedDTO == null ) {
            return null;
        }

        IdcDerived idcDerived = new IdcDerived();

        idcDerived.setDerivedIndicatorName( idcDerivedDTO.getDerivedIndicatorName() );
        idcDerived.setDerivedIndicatorCode( idcDerivedDTO.getDerivedIndicatorCode() );
        idcDerived.setDataSheet( idcDerivedDTO.getDataSheet() );
        idcDerived.setThemeCode( idcDerivedDTO.getThemeCode() );
        idcDerived.setAtomIndicatorCode( idcDerivedDTO.getAtomIndicatorCode() );
        idcDerived.setTimeLimit( idcDerivedDTO.getTimeLimit() );
        idcDerived.setAssociatedFields( idcDerivedDTO.getAssociatedFields() );
        idcDerived.setGeneralLimit( idcDerivedDTO.getGeneralLimit() );
        idcDerived.setIndicatorStatus( idcDerivedDTO.getIndicatorStatus() );
        idcDerived.setGmtCreate( idcDerivedDTO.getGmtCreate() );
        idcDerived.setGmtModified( idcDerivedDTO.getGmtModified() );
        idcDerived.setSqlSentence( idcDerivedDTO.getSqlSentence() );

        return idcDerived;
    }

    @Override
    public void useIdcDerived(IdcDerivedDTO idcDerivedDTO, IdcDerived idcDerived) {
        if ( idcDerivedDTO == null ) {
            return;
        }

        if ( idcDerivedDTO.getDerivedIndicatorName() != null ) {
            idcDerived.setDerivedIndicatorName( idcDerivedDTO.getDerivedIndicatorName() );
        }
        if ( idcDerivedDTO.getDerivedIndicatorCode() != null ) {
            idcDerived.setDerivedIndicatorCode( idcDerivedDTO.getDerivedIndicatorCode() );
        }
        if ( idcDerivedDTO.getDataSheet() != null ) {
            idcDerived.setDataSheet( idcDerivedDTO.getDataSheet() );
        }
        if ( idcDerivedDTO.getThemeCode() != null ) {
            idcDerived.setThemeCode( idcDerivedDTO.getThemeCode() );
        }
        if ( idcDerivedDTO.getAtomIndicatorCode() != null ) {
            idcDerived.setAtomIndicatorCode( idcDerivedDTO.getAtomIndicatorCode() );
        }
        if ( idcDerivedDTO.getTimeLimit() != null ) {
            idcDerived.setTimeLimit( idcDerivedDTO.getTimeLimit() );
        }
        if ( idcDerivedDTO.getAssociatedFields() != null ) {
            idcDerived.setAssociatedFields( idcDerivedDTO.getAssociatedFields() );
        }
        if ( idcDerivedDTO.getGeneralLimit() != null ) {
            idcDerived.setGeneralLimit( idcDerivedDTO.getGeneralLimit() );
        }
        if ( idcDerivedDTO.getIndicatorStatus() != null ) {
            idcDerived.setIndicatorStatus( idcDerivedDTO.getIndicatorStatus() );
        }
        if ( idcDerivedDTO.getGmtCreate() != null ) {
            idcDerived.setGmtCreate( idcDerivedDTO.getGmtCreate() );
        }
        if ( idcDerivedDTO.getGmtModified() != null ) {
            idcDerived.setGmtModified( idcDerivedDTO.getGmtModified() );
        }
        if ( idcDerivedDTO.getSqlSentence() != null ) {
            idcDerived.setSqlSentence( idcDerivedDTO.getSqlSentence() );
        }
    }

    @Override
    public IdcDerivedVO useIdcDerivedVO(IdcDerived idcDerived) {
        if ( idcDerived == null ) {
            return null;
        }

        IdcDerivedVO idcDerivedVO = new IdcDerivedVO();

        idcDerivedVO.setId( idcDerived.getId() );
        idcDerivedVO.setDerivedIndicatorName( idcDerived.getDerivedIndicatorName() );
        idcDerivedVO.setDerivedIndicatorCode( idcDerived.getDerivedIndicatorCode() );
        idcDerivedVO.setDataSheet( idcDerived.getDataSheet() );
        idcDerivedVO.setThemeCode( idcDerived.getThemeCode() );
        idcDerivedVO.setAtomIndicatorCode( idcDerived.getAtomIndicatorCode() );
        idcDerivedVO.setTimeLimit( idcDerived.getTimeLimit() );
        idcDerivedVO.setAssociatedFields( idcDerived.getAssociatedFields() );
        idcDerivedVO.setGeneralLimit( idcDerived.getGeneralLimit() );
        idcDerivedVO.setIndicatorStatus( idcDerived.getIndicatorStatus() );
        idcDerivedVO.setGmtCreate( idcDerived.getGmtCreate() );
        idcDerivedVO.setGmtModified( idcDerived.getGmtModified() );
        idcDerivedVO.setSqlSentence( idcDerived.getSqlSentence() );

        return idcDerivedVO;
    }

    @Override
    public List<IdcDerivedVO> userIdcDerivedListVO(List<IdcDerived> idcDerivedList) {
        if ( idcDerivedList == null ) {
            return null;
        }

        List<IdcDerivedVO> list = new ArrayList<IdcDerivedVO>( idcDerivedList.size() );
        for ( IdcDerived idcDerived : idcDerivedList ) {
            list.add( useIdcDerivedVO( idcDerived ) );
        }

        return list;
    }
}
