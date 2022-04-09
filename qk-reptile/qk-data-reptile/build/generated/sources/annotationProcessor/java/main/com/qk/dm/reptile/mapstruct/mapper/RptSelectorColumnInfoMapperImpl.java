package com.qk.dm.reptile.mapstruct.mapper;

import com.qk.dm.reptile.entity.RptSelectorColumnInfo;
import com.qk.dm.reptile.params.dto.RptSelectorColumnInfoDTO;
import com.qk.dm.reptile.params.vo.RptSelectorColumnInfoVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T20:19:49+0800",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.9.1.jar, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class RptSelectorColumnInfoMapperImpl implements RptSelectorColumnInfoMapper {

    @Override
    public RptSelectorColumnInfo useRptSelectorColumnInfo(RptSelectorColumnInfoDTO rptSelectorColumnInfoDTO) {
        if ( rptSelectorColumnInfoDTO == null ) {
            return null;
        }

        RptSelectorColumnInfo rptSelectorColumnInfo = new RptSelectorColumnInfo();

        rptSelectorColumnInfo.setConfigId( rptSelectorColumnInfoDTO.getConfigId() );
        rptSelectorColumnInfo.setColumnName( rptSelectorColumnInfoDTO.getColumnName() );
        rptSelectorColumnInfo.setColumnCode( rptSelectorColumnInfoDTO.getColumnCode() );
        rptSelectorColumnInfo.setSelector( rptSelectorColumnInfoDTO.getSelector() );
        rptSelectorColumnInfo.setSelectorVal( rptSelectorColumnInfoDTO.getSelectorVal() );
        rptSelectorColumnInfo.setElementType( rptSelectorColumnInfoDTO.getElementType() );
        rptSelectorColumnInfo.setRequestBeforePrefix( rptSelectorColumnInfoDTO.getRequestBeforePrefix() );
        rptSelectorColumnInfo.setRequestAfterPrefix( rptSelectorColumnInfoDTO.getRequestAfterPrefix() );
        rptSelectorColumnInfo.setSourceBeforePrefix( rptSelectorColumnInfoDTO.getSourceBeforePrefix() );
        rptSelectorColumnInfo.setSourceAfterPrefix( rptSelectorColumnInfoDTO.getSourceAfterPrefix() );
        rptSelectorColumnInfo.setBeforePrefix( rptSelectorColumnInfoDTO.getBeforePrefix() );
        rptSelectorColumnInfo.setAfterPrefix( rptSelectorColumnInfoDTO.getAfterPrefix() );

        return rptSelectorColumnInfo;
    }

    @Override
    public RptSelectorColumnInfoVO useRptSelectorColumnInfoVO(RptSelectorColumnInfo rptSelectorColumnInfo) {
        if ( rptSelectorColumnInfo == null ) {
            return null;
        }

        RptSelectorColumnInfoVO rptSelectorColumnInfoVO = new RptSelectorColumnInfoVO();

        rptSelectorColumnInfoVO.setId( rptSelectorColumnInfo.getId() );
        rptSelectorColumnInfoVO.setConfigId( rptSelectorColumnInfo.getConfigId() );
        rptSelectorColumnInfoVO.setColumnName( rptSelectorColumnInfo.getColumnName() );
        rptSelectorColumnInfoVO.setColumnCode( rptSelectorColumnInfo.getColumnCode() );
        rptSelectorColumnInfoVO.setSelector( rptSelectorColumnInfo.getSelector() );
        rptSelectorColumnInfoVO.setSelectorVal( rptSelectorColumnInfo.getSelectorVal() );
        rptSelectorColumnInfoVO.setElementType( rptSelectorColumnInfo.getElementType() );
        rptSelectorColumnInfoVO.setRequestBeforePrefix( rptSelectorColumnInfo.getRequestBeforePrefix() );
        rptSelectorColumnInfoVO.setRequestAfterPrefix( rptSelectorColumnInfo.getRequestAfterPrefix() );
        rptSelectorColumnInfoVO.setSourceBeforePrefix( rptSelectorColumnInfo.getSourceBeforePrefix() );
        rptSelectorColumnInfoVO.setSourceAfterPrefix( rptSelectorColumnInfo.getSourceAfterPrefix() );
        rptSelectorColumnInfoVO.setBeforePrefix( rptSelectorColumnInfo.getBeforePrefix() );
        rptSelectorColumnInfoVO.setAfterPrefix( rptSelectorColumnInfo.getAfterPrefix() );

        return rptSelectorColumnInfoVO;
    }

    @Override
    public void of(RptSelectorColumnInfoDTO rptSelectorColumnInfoDTO, RptSelectorColumnInfo rptSelectorColumnInfo) {
        if ( rptSelectorColumnInfoDTO == null ) {
            return;
        }

        if ( rptSelectorColumnInfoDTO.getConfigId() != null ) {
            rptSelectorColumnInfo.setConfigId( rptSelectorColumnInfoDTO.getConfigId() );
        }
        if ( rptSelectorColumnInfoDTO.getColumnName() != null ) {
            rptSelectorColumnInfo.setColumnName( rptSelectorColumnInfoDTO.getColumnName() );
        }
        if ( rptSelectorColumnInfoDTO.getColumnCode() != null ) {
            rptSelectorColumnInfo.setColumnCode( rptSelectorColumnInfoDTO.getColumnCode() );
        }
        if ( rptSelectorColumnInfoDTO.getSelector() != null ) {
            rptSelectorColumnInfo.setSelector( rptSelectorColumnInfoDTO.getSelector() );
        }
        if ( rptSelectorColumnInfoDTO.getSelectorVal() != null ) {
            rptSelectorColumnInfo.setSelectorVal( rptSelectorColumnInfoDTO.getSelectorVal() );
        }
        if ( rptSelectorColumnInfoDTO.getElementType() != null ) {
            rptSelectorColumnInfo.setElementType( rptSelectorColumnInfoDTO.getElementType() );
        }
        if ( rptSelectorColumnInfoDTO.getRequestBeforePrefix() != null ) {
            rptSelectorColumnInfo.setRequestBeforePrefix( rptSelectorColumnInfoDTO.getRequestBeforePrefix() );
        }
        if ( rptSelectorColumnInfoDTO.getRequestAfterPrefix() != null ) {
            rptSelectorColumnInfo.setRequestAfterPrefix( rptSelectorColumnInfoDTO.getRequestAfterPrefix() );
        }
        if ( rptSelectorColumnInfoDTO.getSourceBeforePrefix() != null ) {
            rptSelectorColumnInfo.setSourceBeforePrefix( rptSelectorColumnInfoDTO.getSourceBeforePrefix() );
        }
        if ( rptSelectorColumnInfoDTO.getSourceAfterPrefix() != null ) {
            rptSelectorColumnInfo.setSourceAfterPrefix( rptSelectorColumnInfoDTO.getSourceAfterPrefix() );
        }
        if ( rptSelectorColumnInfoDTO.getBeforePrefix() != null ) {
            rptSelectorColumnInfo.setBeforePrefix( rptSelectorColumnInfoDTO.getBeforePrefix() );
        }
        if ( rptSelectorColumnInfoDTO.getAfterPrefix() != null ) {
            rptSelectorColumnInfo.setAfterPrefix( rptSelectorColumnInfoDTO.getAfterPrefix() );
        }
    }

    @Override
    public List<RptSelectorColumnInfoVO> of(List<RptSelectorColumnInfo> rptSelectorColumnInfoList) {
        if ( rptSelectorColumnInfoList == null ) {
            return null;
        }

        List<RptSelectorColumnInfoVO> list = new ArrayList<RptSelectorColumnInfoVO>( rptSelectorColumnInfoList.size() );
        for ( RptSelectorColumnInfo rptSelectorColumnInfo : rptSelectorColumnInfoList ) {
            list.add( useRptSelectorColumnInfoVO( rptSelectorColumnInfo ) );
        }

        return list;
    }

    @Override
    public List<RptSelectorColumnInfo> ofList(List<RptSelectorColumnInfoDTO> rptSelectorColumnInfoDTOList) {
        if ( rptSelectorColumnInfoDTOList == null ) {
            return null;
        }

        List<RptSelectorColumnInfo> list = new ArrayList<RptSelectorColumnInfo>( rptSelectorColumnInfoDTOList.size() );
        for ( RptSelectorColumnInfoDTO rptSelectorColumnInfoDTO : rptSelectorColumnInfoDTOList ) {
            list.add( useRptSelectorColumnInfo( rptSelectorColumnInfoDTO ) );
        }

        return list;
    }

    @Override
    public void of(RptSelectorColumnInfo source, RptSelectorColumnInfo target) {
        if ( source == null ) {
            return;
        }

        if ( source.getConfigId() != null ) {
            target.setConfigId( source.getConfigId() );
        }
        if ( source.getColumnName() != null ) {
            target.setColumnName( source.getColumnName() );
        }
        if ( source.getColumnCode() != null ) {
            target.setColumnCode( source.getColumnCode() );
        }
        if ( source.getSelector() != null ) {
            target.setSelector( source.getSelector() );
        }
        if ( source.getSelectorVal() != null ) {
            target.setSelectorVal( source.getSelectorVal() );
        }
        if ( source.getElementType() != null ) {
            target.setElementType( source.getElementType() );
        }
        if ( source.getRequestBeforePrefix() != null ) {
            target.setRequestBeforePrefix( source.getRequestBeforePrefix() );
        }
        if ( source.getRequestAfterPrefix() != null ) {
            target.setRequestAfterPrefix( source.getRequestAfterPrefix() );
        }
        if ( source.getSourceBeforePrefix() != null ) {
            target.setSourceBeforePrefix( source.getSourceBeforePrefix() );
        }
        if ( source.getSourceAfterPrefix() != null ) {
            target.setSourceAfterPrefix( source.getSourceAfterPrefix() );
        }
        if ( source.getBeforePrefix() != null ) {
            target.setBeforePrefix( source.getBeforePrefix() );
        }
        if ( source.getAfterPrefix() != null ) {
            target.setAfterPrefix( source.getAfterPrefix() );
        }
        if ( source.getGmtCreate() != null ) {
            target.setGmtCreate( source.getGmtCreate() );
        }
        if ( source.getGmtModified() != null ) {
            target.setGmtModified( source.getGmtModified() );
        }
    }
}
