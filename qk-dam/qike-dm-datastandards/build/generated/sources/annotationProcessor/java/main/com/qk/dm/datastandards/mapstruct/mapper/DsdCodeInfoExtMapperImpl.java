package com.qk.dm.datastandards.mapstruct.mapper;

import com.qk.dm.datastandards.entity.DsdCodeInfoExt;
import com.qk.dm.datastandards.vo.DsdCodeInfoExtVO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T20:19:46+0800",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.9.1.jar, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class DsdCodeInfoExtMapperImpl implements DsdCodeInfoExtMapper {

    @Override
    public DsdCodeInfoExtVO useDsdCodeInfoExtVO(DsdCodeInfoExt dsdCodeInfoExt) {
        if ( dsdCodeInfoExt == null ) {
            return null;
        }

        DsdCodeInfoExtVO dsdCodeInfoExtVO = new DsdCodeInfoExtVO();

        dsdCodeInfoExtVO.setId( dsdCodeInfoExt.getId() );
        if ( dsdCodeInfoExt.getDsdCodeInfoId() != null ) {
            dsdCodeInfoExtVO.setDsdCodeInfoId( String.valueOf( dsdCodeInfoExt.getDsdCodeInfoId() ) );
        }
        dsdCodeInfoExtVO.setSearchCode( dsdCodeInfoExt.getSearchCode() );
        dsdCodeInfoExtVO.setSearchValue( dsdCodeInfoExt.getSearchValue() );
        dsdCodeInfoExtVO.setGmtModified( dsdCodeInfoExt.getGmtModified() );

        return dsdCodeInfoExtVO;
    }

    @Override
    public DsdCodeInfoExt useDsdCodeInfoExt(DsdCodeInfoExtVO dsdCodeInfoExtVO) {
        if ( dsdCodeInfoExtVO == null ) {
            return null;
        }

        DsdCodeInfoExt dsdCodeInfoExt = new DsdCodeInfoExt();

        dsdCodeInfoExt.setId( dsdCodeInfoExtVO.getId() );
        if ( dsdCodeInfoExtVO.getDsdCodeInfoId() != null ) {
            dsdCodeInfoExt.setDsdCodeInfoId( Long.parseLong( dsdCodeInfoExtVO.getDsdCodeInfoId() ) );
        }
        dsdCodeInfoExt.setSearchCode( dsdCodeInfoExtVO.getSearchCode() );
        dsdCodeInfoExt.setSearchValue( dsdCodeInfoExtVO.getSearchValue() );
        dsdCodeInfoExt.setGmtModified( dsdCodeInfoExtVO.getGmtModified() );

        return dsdCodeInfoExt;
    }
}
