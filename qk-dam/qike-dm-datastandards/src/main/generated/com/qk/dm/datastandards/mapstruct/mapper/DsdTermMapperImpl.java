package com.qk.dm.datastandards.mapstruct.mapper;

import com.qk.dm.datastandards.entity.DsdTerm;
import com.qk.dm.datastandards.vo.DsdTermVO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-07-12T02:36:43+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.5 (Oracle Corporation)"
)
public class DsdTermMapperImpl implements DsdTermMapper {

    @Override
    public DsdTermVO useDsdTermVO(DsdTerm dsdTerm) {
        if ( dsdTerm == null ) {
            return null;
        }

        DsdTermVO dsdTermVO = new DsdTermVO();

        dsdTermVO.setId( dsdTerm.getId() );
        dsdTermVO.setChineseName( dsdTerm.getChineseName() );
        dsdTermVO.setEnglishName( dsdTerm.getEnglishName() );
        dsdTermVO.setShortEnglishName( dsdTerm.getShortEnglishName() );
        dsdTermVO.setRootName( dsdTerm.getRootName() );
        dsdTermVO.setState( dsdTerm.getState() );

        return dsdTermVO;
    }

    @Override
    public DsdTerm useDsdTerm(DsdTermVO dsdTermVO) {
        if ( dsdTermVO == null ) {
            return null;
        }

        DsdTerm dsdTerm = new DsdTerm();

        dsdTerm.setId( dsdTermVO.getId() );
        dsdTerm.setChineseName( dsdTermVO.getChineseName() );
        dsdTerm.setEnglishName( dsdTermVO.getEnglishName() );
        dsdTerm.setShortEnglishName( dsdTermVO.getShortEnglishName() );
        dsdTerm.setRootName( dsdTermVO.getRootName() );
        dsdTerm.setState( dsdTermVO.getState() );

        return dsdTerm;
    }
}
