package com.qk.dm.indicator.mapstruct.mapper;

import com.qk.dm.indicator.entity.IdcTimeLimit;
import com.qk.dm.indicator.params.dto.IdcTimeLimitDTO;
import com.qk.dm.indicator.params.vo.IdcTimeLimitVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T17:36:26+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class IdcTimeLimitMapperImpl implements IdcTimeLimitMapper {

    @Override
    public IdcTimeLimit useIdcTimeLimit(IdcTimeLimitDTO idcTimeLimitDTO) {
        if ( idcTimeLimitDTO == null ) {
            return null;
        }

        IdcTimeLimit idcTimeLimit = new IdcTimeLimit();

        idcTimeLimit.setLimitName( idcTimeLimitDTO.getLimitName() );
        idcTimeLimit.setLimitType( idcTimeLimitDTO.getLimitType() );
        idcTimeLimit.setBaseTime( idcTimeLimitDTO.getBaseTime() );
        idcTimeLimit.setQuickStart( idcTimeLimitDTO.getQuickStart() );
        idcTimeLimit.setStart( idcTimeLimitDTO.getStart() );
        idcTimeLimit.setEnd( idcTimeLimitDTO.getEnd() );
        idcTimeLimit.setDescribe( idcTimeLimitDTO.getDescribe() );

        return idcTimeLimit;
    }

    @Override
    public void useIdcTimeLimit(IdcTimeLimitDTO idcTimeLimitDTO, IdcTimeLimit idcTimeLimit) {
        if ( idcTimeLimitDTO == null ) {
            return;
        }

        if ( idcTimeLimitDTO.getLimitName() != null ) {
            idcTimeLimit.setLimitName( idcTimeLimitDTO.getLimitName() );
        }
        if ( idcTimeLimitDTO.getLimitType() != null ) {
            idcTimeLimit.setLimitType( idcTimeLimitDTO.getLimitType() );
        }
        if ( idcTimeLimitDTO.getBaseTime() != null ) {
            idcTimeLimit.setBaseTime( idcTimeLimitDTO.getBaseTime() );
        }
        if ( idcTimeLimitDTO.getQuickStart() != null ) {
            idcTimeLimit.setQuickStart( idcTimeLimitDTO.getQuickStart() );
        }
        if ( idcTimeLimitDTO.getStart() != null ) {
            idcTimeLimit.setStart( idcTimeLimitDTO.getStart() );
        }
        if ( idcTimeLimitDTO.getEnd() != null ) {
            idcTimeLimit.setEnd( idcTimeLimitDTO.getEnd() );
        }
        if ( idcTimeLimitDTO.getDescribe() != null ) {
            idcTimeLimit.setDescribe( idcTimeLimitDTO.getDescribe() );
        }
    }

    @Override
    public IdcTimeLimitVO useIdcTimeLimitVO(IdcTimeLimit idcTimeLimit) {
        if ( idcTimeLimit == null ) {
            return null;
        }

        IdcTimeLimitVO idcTimeLimitVO = new IdcTimeLimitVO();

        idcTimeLimitVO.setId( idcTimeLimit.getId() );
        idcTimeLimitVO.setLimitName( idcTimeLimit.getLimitName() );
        idcTimeLimitVO.setLimitType( idcTimeLimit.getLimitType() );
        idcTimeLimitVO.setBaseTime( idcTimeLimit.getBaseTime() );
        idcTimeLimitVO.setQuickStart( idcTimeLimit.getQuickStart() );
        idcTimeLimitVO.setStart( idcTimeLimit.getStart() );
        idcTimeLimitVO.setEnd( idcTimeLimit.getEnd() );
        idcTimeLimitVO.setDescribe( idcTimeLimit.getDescribe() );
        idcTimeLimitVO.setGmtCreate( idcTimeLimit.getGmtCreate() );
        idcTimeLimitVO.setGmtModified( idcTimeLimit.getGmtModified() );

        return idcTimeLimitVO;
    }

    @Override
    public List<IdcTimeLimitVO> userIdcTimeLimitListVO(List<IdcTimeLimit> idcTimeLimitList) {
        if ( idcTimeLimitList == null ) {
            return null;
        }

        List<IdcTimeLimitVO> list = new ArrayList<IdcTimeLimitVO>( idcTimeLimitList.size() );
        for ( IdcTimeLimit idcTimeLimit : idcTimeLimitList ) {
            list.add( useIdcTimeLimitVO( idcTimeLimit ) );
        }

        return list;
    }
}
