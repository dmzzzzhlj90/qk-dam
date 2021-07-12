package com.qk.dm.datastandards.mapstruct.mapper;

import com.qk.dm.datastandards.entity.DsdCodeTerm;
import com.qk.dm.datastandards.vo.DsdCodeTermVO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-07-12T02:36:42+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.5 (Oracle Corporation)"
)
public class DsdCodeTermMapperImpl implements DsdCodeTermMapper {

    @Override
    public DsdCodeTermVO usDsdCodeTermVO(DsdCodeTerm dsdCodeTerm) {
        if ( dsdCodeTerm == null ) {
            return null;
        }

        DsdCodeTermVO dsdCodeTermVO = new DsdCodeTermVO();

        dsdCodeTermVO.setId( dsdCodeTerm.getId() );
        dsdCodeTermVO.setCodeDirId( dsdCodeTerm.getCodeDirId() );
        dsdCodeTermVO.setCodeId( dsdCodeTerm.getCodeId() );
        dsdCodeTermVO.setCodeName( dsdCodeTerm.getCodeName() );
        dsdCodeTermVO.setTermId( dsdCodeTerm.getTermId() );
        dsdCodeTermVO.setDescription( dsdCodeTerm.getDescription() );

        return dsdCodeTermVO;
    }

    @Override
    public DsdCodeTerm useDsdCodeTerm(DsdCodeTermVO dsdCodeTermVO) {
        if ( dsdCodeTermVO == null ) {
            return null;
        }

        DsdCodeTerm dsdCodeTerm = new DsdCodeTerm();

        dsdCodeTerm.setId( dsdCodeTermVO.getId() );
        dsdCodeTerm.setCodeDirId( dsdCodeTermVO.getCodeDirId() );
        dsdCodeTerm.setCodeId( dsdCodeTermVO.getCodeId() );
        dsdCodeTerm.setCodeName( dsdCodeTermVO.getCodeName() );
        dsdCodeTerm.setTermId( dsdCodeTermVO.getTermId() );
        dsdCodeTerm.setDescription( dsdCodeTermVO.getDescription() );

        return dsdCodeTerm;
    }
}
