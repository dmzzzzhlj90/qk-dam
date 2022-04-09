package com.qk.dm.datastandards.mapstruct.mapper;

import com.qk.dm.datastandards.entity.DsdBasicinfo;
import com.qk.dm.datastandards.vo.DsdBasicInfoVO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T20:19:46+0800",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.9.1.jar, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class DsdBasicInfoMapperImpl implements DsdBasicInfoMapper {

    @Override
    public DsdBasicInfoVO useDsdBasicInfoVO(DsdBasicinfo dsdBasicinfo) {
        if ( dsdBasicinfo == null ) {
            return null;
        }

        DsdBasicInfoVO dsdBasicInfoVO = new DsdBasicInfoVO();

        dsdBasicInfoVO.setId( dsdBasicinfo.getId() );
        dsdBasicInfoVO.setDsdName( dsdBasicinfo.getDsdName() );
        dsdBasicInfoVO.setDsdCode( dsdBasicinfo.getDsdCode() );
        dsdBasicInfoVO.setColName( dsdBasicinfo.getColName() );
        dsdBasicInfoVO.setDataType( dsdBasicinfo.getDataType() );
        dsdBasicInfoVO.setDataCapacity( dsdBasicinfo.getDataCapacity() );
        dsdBasicInfoVO.setCodeDirId( dsdBasicinfo.getCodeDirId() );
        dsdBasicInfoVO.setUseCodeLevel( dsdBasicinfo.getUseCodeLevel() );
        dsdBasicInfoVO.setCodeCol( dsdBasicinfo.getCodeCol() );
        dsdBasicInfoVO.setDsdLevel( dsdBasicinfo.getDsdLevel() );
        dsdBasicInfoVO.setDsdLevelId( dsdBasicinfo.getDsdLevelId() );
        dsdBasicInfoVO.setSortField( dsdBasicinfo.getSortField() );
        dsdBasicInfoVO.setDescription( dsdBasicinfo.getDescription() );
        dsdBasicInfoVO.setGmtModified( dsdBasicinfo.getGmtModified() );

        return dsdBasicInfoVO;
    }

    @Override
    public DsdBasicinfo useDsdBasicInfo(DsdBasicInfoVO dsdBasicinfoVO) {
        if ( dsdBasicinfoVO == null ) {
            return null;
        }

        DsdBasicinfo dsdBasicinfo = new DsdBasicinfo();

        dsdBasicinfo.setId( dsdBasicinfoVO.getId() );
        dsdBasicinfo.setDsdName( dsdBasicinfoVO.getDsdName() );
        dsdBasicinfo.setDsdCode( dsdBasicinfoVO.getDsdCode() );
        dsdBasicinfo.setColName( dsdBasicinfoVO.getColName() );
        dsdBasicinfo.setDataType( dsdBasicinfoVO.getDataType() );
        dsdBasicinfo.setDataCapacity( dsdBasicinfoVO.getDataCapacity() );
        dsdBasicinfo.setCodeDirId( dsdBasicinfoVO.getCodeDirId() );
        dsdBasicinfo.setUseCodeLevel( dsdBasicinfoVO.getUseCodeLevel() );
        dsdBasicinfo.setCodeCol( dsdBasicinfoVO.getCodeCol() );
        dsdBasicinfo.setDsdLevel( dsdBasicinfoVO.getDsdLevel() );
        dsdBasicinfo.setDsdLevelId( dsdBasicinfoVO.getDsdLevelId() );
        dsdBasicinfo.setSortField( dsdBasicinfoVO.getSortField() );
        dsdBasicinfo.setDescription( dsdBasicinfoVO.getDescription() );
        dsdBasicinfo.setGmtModified( dsdBasicinfoVO.getGmtModified() );

        return dsdBasicinfo;
    }
}
