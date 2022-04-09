package com.qk.dm.indicator.mapstruct.mapper;

import com.qk.dm.indicator.entity.IdcBusiness;
import com.qk.dm.indicator.params.dto.IdcBusinessDTO;
import com.qk.dm.indicator.params.vo.IdcBusinessVO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T20:19:47+0800",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.9.1.jar, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class IdcBusinessMapperImpl implements IdcBusinessMapper {

    @Override
    public IdcBusiness useIdcBusiness(IdcBusinessDTO idcBusinessDTO) {
        if ( idcBusinessDTO == null ) {
            return null;
        }

        IdcBusiness idcBusiness = new IdcBusiness();

        idcBusiness.setBIndicatorName( idcBusinessDTO.getBIndicatorName() );
        idcBusiness.setBIndicatorCode( idcBusinessDTO.getBIndicatorCode() );
        idcBusiness.setBIndicatorAlias( idcBusinessDTO.getBIndicatorAlias() );
        idcBusiness.setSetPurpose( idcBusinessDTO.getSetPurpose() );
        idcBusiness.setIndicatorDefinition( idcBusinessDTO.getIndicatorDefinition() );
        idcBusiness.setRemarks( idcBusinessDTO.getRemarks() );
        idcBusiness.setCalculationFormula( idcBusinessDTO.getCalculationFormula() );
        idcBusiness.setStatisticalCycle( idcBusinessDTO.getStatisticalCycle() );
        idcBusiness.setDimStatistical( idcBusinessDTO.getDimStatistical() );
        idcBusiness.setIndicatorStatus( idcBusinessDTO.getIndicatorStatus() );
        idcBusiness.setCaliberModifier( idcBusinessDTO.getCaliberModifier() );
        idcBusiness.setApplicationScenario( idcBusinessDTO.getApplicationScenario() );
        idcBusiness.setTechIndicator( idcBusinessDTO.getTechIndicator() );
        idcBusiness.setMeasureObject( idcBusinessDTO.getMeasureObject() );
        idcBusiness.setMeasureUnit( idcBusinessDTO.getMeasureUnit() );
        idcBusiness.setIndicatorDepart( idcBusinessDTO.getIndicatorDepart() );
        idcBusiness.setIndicatorPersonLiable( idcBusinessDTO.getIndicatorPersonLiable() );
        try {
            if ( idcBusinessDTO.getGmtCreate() != null ) {
                idcBusiness.setGmtCreate( new SimpleDateFormat().parse( idcBusinessDTO.getGmtCreate() ) );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( idcBusinessDTO.getGmtModified() != null ) {
                idcBusiness.setGmtModified( new SimpleDateFormat().parse( idcBusinessDTO.getGmtModified() ) );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }

        return idcBusiness;
    }

    @Override
    public void useIdcBusiness(IdcBusinessDTO idcBusinessDTO, IdcBusiness idcBusiness) {
        if ( idcBusinessDTO == null ) {
            return;
        }

        if ( idcBusinessDTO.getBIndicatorName() != null ) {
            idcBusiness.setBIndicatorName( idcBusinessDTO.getBIndicatorName() );
        }
        if ( idcBusinessDTO.getBIndicatorCode() != null ) {
            idcBusiness.setBIndicatorCode( idcBusinessDTO.getBIndicatorCode() );
        }
        if ( idcBusinessDTO.getBIndicatorAlias() != null ) {
            idcBusiness.setBIndicatorAlias( idcBusinessDTO.getBIndicatorAlias() );
        }
        if ( idcBusinessDTO.getSetPurpose() != null ) {
            idcBusiness.setSetPurpose( idcBusinessDTO.getSetPurpose() );
        }
        if ( idcBusinessDTO.getIndicatorDefinition() != null ) {
            idcBusiness.setIndicatorDefinition( idcBusinessDTO.getIndicatorDefinition() );
        }
        if ( idcBusinessDTO.getRemarks() != null ) {
            idcBusiness.setRemarks( idcBusinessDTO.getRemarks() );
        }
        if ( idcBusinessDTO.getCalculationFormula() != null ) {
            idcBusiness.setCalculationFormula( idcBusinessDTO.getCalculationFormula() );
        }
        if ( idcBusinessDTO.getStatisticalCycle() != null ) {
            idcBusiness.setStatisticalCycle( idcBusinessDTO.getStatisticalCycle() );
        }
        if ( idcBusinessDTO.getDimStatistical() != null ) {
            idcBusiness.setDimStatistical( idcBusinessDTO.getDimStatistical() );
        }
        if ( idcBusinessDTO.getIndicatorStatus() != null ) {
            idcBusiness.setIndicatorStatus( idcBusinessDTO.getIndicatorStatus() );
        }
        if ( idcBusinessDTO.getCaliberModifier() != null ) {
            idcBusiness.setCaliberModifier( idcBusinessDTO.getCaliberModifier() );
        }
        if ( idcBusinessDTO.getApplicationScenario() != null ) {
            idcBusiness.setApplicationScenario( idcBusinessDTO.getApplicationScenario() );
        }
        if ( idcBusinessDTO.getTechIndicator() != null ) {
            idcBusiness.setTechIndicator( idcBusinessDTO.getTechIndicator() );
        }
        if ( idcBusinessDTO.getMeasureObject() != null ) {
            idcBusiness.setMeasureObject( idcBusinessDTO.getMeasureObject() );
        }
        if ( idcBusinessDTO.getMeasureUnit() != null ) {
            idcBusiness.setMeasureUnit( idcBusinessDTO.getMeasureUnit() );
        }
        if ( idcBusinessDTO.getIndicatorDepart() != null ) {
            idcBusiness.setIndicatorDepart( idcBusinessDTO.getIndicatorDepart() );
        }
        if ( idcBusinessDTO.getIndicatorPersonLiable() != null ) {
            idcBusiness.setIndicatorPersonLiable( idcBusinessDTO.getIndicatorPersonLiable() );
        }
        try {
            if ( idcBusinessDTO.getGmtCreate() != null ) {
                idcBusiness.setGmtCreate( new SimpleDateFormat().parse( idcBusinessDTO.getGmtCreate() ) );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
        try {
            if ( idcBusinessDTO.getGmtModified() != null ) {
                idcBusiness.setGmtModified( new SimpleDateFormat().parse( idcBusinessDTO.getGmtModified() ) );
            }
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
    }

    @Override
    public IdcBusinessVO useIdcBusinessVO(IdcBusiness idcBusiness) {
        if ( idcBusiness == null ) {
            return null;
        }

        IdcBusinessVO idcBusinessVO = new IdcBusinessVO();

        if ( idcBusiness.getId() != null ) {
            idcBusinessVO.setId( idcBusiness.getId().intValue() );
        }
        idcBusinessVO.setBIndicatorName( idcBusiness.getBIndicatorName() );
        idcBusinessVO.setBIndicatorCode( idcBusiness.getBIndicatorCode() );
        idcBusinessVO.setBIndicatorAlias( idcBusiness.getBIndicatorAlias() );
        idcBusinessVO.setSetPurpose( idcBusiness.getSetPurpose() );
        idcBusinessVO.setIndicatorDefinition( idcBusiness.getIndicatorDefinition() );
        idcBusinessVO.setRemarks( idcBusiness.getRemarks() );
        idcBusinessVO.setCalculationFormula( idcBusiness.getCalculationFormula() );
        idcBusinessVO.setStatisticalCycle( idcBusiness.getStatisticalCycle() );
        idcBusinessVO.setDimStatistical( idcBusiness.getDimStatistical() );
        idcBusinessVO.setIndicatorStatus( idcBusiness.getIndicatorStatus() );
        idcBusinessVO.setCaliberModifier( idcBusiness.getCaliberModifier() );
        idcBusinessVO.setApplicationScenario( idcBusiness.getApplicationScenario() );
        idcBusinessVO.setTechIndicator( idcBusiness.getTechIndicator() );
        idcBusinessVO.setMeasureObject( idcBusiness.getMeasureObject() );
        idcBusinessVO.setMeasureUnit( idcBusiness.getMeasureUnit() );
        idcBusinessVO.setIndicatorDepart( idcBusiness.getIndicatorDepart() );
        idcBusinessVO.setIndicatorPersonLiable( idcBusiness.getIndicatorPersonLiable() );
        idcBusinessVO.setGmtCreate( idcBusiness.getGmtCreate() );
        idcBusinessVO.setGmtModified( idcBusiness.getGmtModified() );

        return idcBusinessVO;
    }

    @Override
    public List<IdcBusinessVO> userIdcBusinessListVO(List<IdcBusiness> idcBusinessList) {
        if ( idcBusinessList == null ) {
            return null;
        }

        List<IdcBusinessVO> list = new ArrayList<IdcBusinessVO>( idcBusinessList.size() );
        for ( IdcBusiness idcBusiness : idcBusinessList ) {
            list.add( useIdcBusinessVO( idcBusiness ) );
        }

        return list;
    }
}
