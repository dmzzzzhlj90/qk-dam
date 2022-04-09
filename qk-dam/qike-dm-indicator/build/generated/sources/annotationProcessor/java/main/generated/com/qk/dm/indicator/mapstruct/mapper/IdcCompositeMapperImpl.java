package com.qk.dm.indicator.mapstruct.mapper;

import com.qk.dm.indicator.entity.IdcComposite;
import com.qk.dm.indicator.params.dto.IdcCompositeDTO;
import com.qk.dm.indicator.params.vo.IdcCompositePageVO;
import com.qk.dm.indicator.params.vo.IdcCompositeVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T17:36:26+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class IdcCompositeMapperImpl implements IdcCompositeMapper {

    @Override
    public void useIdc(IdcCompositeDTO idcCompositeDTO, IdcComposite idcComposite) {
        if ( idcCompositeDTO == null ) {
            return;
        }

        if ( idcCompositeDTO.getCompositeIndicatorName() != null ) {
            idcComposite.setCompositeIndicatorName( idcCompositeDTO.getCompositeIndicatorName() );
        }
        if ( idcCompositeDTO.getCompositeIndicatorCode() != null ) {
            idcComposite.setCompositeIndicatorCode( idcCompositeDTO.getCompositeIndicatorCode() );
        }
        if ( idcCompositeDTO.getDimStatistics() != null ) {
            idcComposite.setDimStatistics( idcCompositeDTO.getDimStatistics() );
        }
        if ( idcCompositeDTO.getThemeCode() != null ) {
            idcComposite.setThemeCode( idcCompositeDTO.getThemeCode() );
        }
        if ( idcCompositeDTO.getDataType() != null ) {
            idcComposite.setDataType( idcCompositeDTO.getDataType() );
        }
        if ( idcCompositeDTO.getExpression() != null ) {
            idcComposite.setExpression( idcCompositeDTO.getExpression() );
        }
        if ( idcCompositeDTO.getIndicatorStatus() != null ) {
            idcComposite.setIndicatorStatus( idcCompositeDTO.getIndicatorStatus() );
        }
        if ( idcCompositeDTO.getSqlSentence() != null ) {
            idcComposite.setSqlSentence( idcCompositeDTO.getSqlSentence() );
        }
    }

    @Override
    public IdcComposite useIdc(IdcCompositeDTO idcCompositeDTO) {
        if ( idcCompositeDTO == null ) {
            return null;
        }

        IdcComposite idcComposite = new IdcComposite();

        idcComposite.setCompositeIndicatorName( idcCompositeDTO.getCompositeIndicatorName() );
        idcComposite.setCompositeIndicatorCode( idcCompositeDTO.getCompositeIndicatorCode() );
        idcComposite.setDimStatistics( idcCompositeDTO.getDimStatistics() );
        idcComposite.setThemeCode( idcCompositeDTO.getThemeCode() );
        idcComposite.setDataType( idcCompositeDTO.getDataType() );
        idcComposite.setExpression( idcCompositeDTO.getExpression() );
        idcComposite.setIndicatorStatus( idcCompositeDTO.getIndicatorStatus() );
        idcComposite.setSqlSentence( idcCompositeDTO.getSqlSentence() );

        return idcComposite;
    }

    @Override
    public IdcCompositeVO useIdcVO(IdcComposite idcComposite) {
        if ( idcComposite == null ) {
            return null;
        }

        IdcCompositeVO idcCompositeVO = new IdcCompositeVO();

        idcCompositeVO.setId( idcComposite.getId() );
        idcCompositeVO.setCompositeIndicatorName( idcComposite.getCompositeIndicatorName() );
        idcCompositeVO.setCompositeIndicatorCode( idcComposite.getCompositeIndicatorCode() );
        idcCompositeVO.setDimStatistics( idcComposite.getDimStatistics() );
        idcCompositeVO.setThemeCode( idcComposite.getThemeCode() );
        idcCompositeVO.setDataType( idcComposite.getDataType() );
        idcCompositeVO.setExpression( idcComposite.getExpression() );
        idcCompositeVO.setIndicatorStatus( idcComposite.getIndicatorStatus() );
        idcCompositeVO.setSqlSentence( idcComposite.getSqlSentence() );

        return idcCompositeVO;
    }

    @Override
    public List<IdcCompositeVO> useIdcVO(List<IdcComposite> idcComposites) {
        if ( idcComposites == null ) {
            return null;
        }

        List<IdcCompositeVO> list = new ArrayList<IdcCompositeVO>( idcComposites.size() );
        for ( IdcComposite idcComposite : idcComposites ) {
            list.add( useIdcVO( idcComposite ) );
        }

        return list;
    }

    @Override
    public List<IdcCompositePageVO> useIdcPageVO(List<IdcComposite> idcComposites) {
        if ( idcComposites == null ) {
            return null;
        }

        List<IdcCompositePageVO> list = new ArrayList<IdcCompositePageVO>( idcComposites.size() );
        for ( IdcComposite idcComposite : idcComposites ) {
            list.add( idcCompositeToIdcCompositePageVO( idcComposite ) );
        }

        return list;
    }

    protected IdcCompositePageVO idcCompositeToIdcCompositePageVO(IdcComposite idcComposite) {
        if ( idcComposite == null ) {
            return null;
        }

        IdcCompositePageVO idcCompositePageVO = new IdcCompositePageVO();

        idcCompositePageVO.setId( idcComposite.getId() );
        idcCompositePageVO.setCompositeIndicatorName( idcComposite.getCompositeIndicatorName() );
        idcCompositePageVO.setCompositeIndicatorCode( idcComposite.getCompositeIndicatorCode() );
        idcCompositePageVO.setThemeCode( idcComposite.getThemeCode() );
        idcCompositePageVO.setIndicatorStatus( idcComposite.getIndicatorStatus() );
        idcCompositePageVO.setGmtModified( idcComposite.getGmtModified() );

        return idcCompositePageVO;
    }
}
