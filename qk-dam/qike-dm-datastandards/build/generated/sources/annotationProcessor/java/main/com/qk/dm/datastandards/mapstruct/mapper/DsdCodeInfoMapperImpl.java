package com.qk.dm.datastandards.mapstruct.mapper;

import com.qk.dm.datastandards.entity.DsdCodeInfo;
import com.qk.dm.datastandards.vo.DsdCodeInfoVO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T20:19:46+0800",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.9.1.jar, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class DsdCodeInfoMapperImpl implements DsdCodeInfoMapper {

    @Override
    public DsdCodeInfoVO useDsdCodeInfoVO(DsdCodeInfo dsdCodeInfo) {
        if ( dsdCodeInfo == null ) {
            return null;
        }

        DsdCodeInfoVO dsdCodeInfoVO = new DsdCodeInfoVO();

        dsdCodeInfoVO.setId( dsdCodeInfo.getId() );
        dsdCodeInfoVO.setCodeDirId( dsdCodeInfo.getCodeDirId() );
        dsdCodeInfoVO.setCodeDirLevel( dsdCodeInfo.getCodeDirLevel() );
        dsdCodeInfoVO.setTableName( dsdCodeInfo.getTableName() );
        dsdCodeInfoVO.setTableCode( dsdCodeInfo.getTableCode() );
        dsdCodeInfoVO.setTableDesc( dsdCodeInfo.getTableDesc() );
        dsdCodeInfoVO.setGmtModified( dsdCodeInfo.getGmtModified() );

        return dsdCodeInfoVO;
    }

    @Override
    public DsdCodeInfo useDsdCodeInfo(DsdCodeInfoVO dsdCodeInfoVO) {
        if ( dsdCodeInfoVO == null ) {
            return null;
        }

        DsdCodeInfo dsdCodeInfo = new DsdCodeInfo();

        dsdCodeInfo.setId( dsdCodeInfoVO.getId() );
        dsdCodeInfo.setTableName( dsdCodeInfoVO.getTableName() );
        dsdCodeInfo.setTableCode( dsdCodeInfoVO.getTableCode() );
        dsdCodeInfo.setTableDesc( dsdCodeInfoVO.getTableDesc() );
        dsdCodeInfo.setCodeDirId( dsdCodeInfoVO.getCodeDirId() );
        dsdCodeInfo.setCodeDirLevel( dsdCodeInfoVO.getCodeDirLevel() );
        dsdCodeInfo.setGmtModified( dsdCodeInfoVO.getGmtModified() );

        return dsdCodeInfo;
    }
}
