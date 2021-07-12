package com.qk.dm.datastandards.mapstruct.mapper;

import com.qk.dm.datastandards.entity.DsdBasicinfo;
import com.qk.dm.datastandards.vo.DsdBasicinfoVO;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-07-12T02:36:43+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.5 (Oracle Corporation)"
)
public class DsdBasicInfoMapperImpl implements DsdBasicInfoMapper {

    @Override
    public DsdBasicinfoVO useDsdBasicInfoVO(DsdBasicinfo dsdBasicinfo) {
        if ( dsdBasicinfo == null ) {
            return null;
        }

        DsdBasicinfoVO dsdBasicinfoVO = new DsdBasicinfoVO();

        dsdBasicinfoVO.setId( dsdBasicinfo.getId() );
        dsdBasicinfoVO.setDsdName( dsdBasicinfo.getDsdName() );
        dsdBasicinfoVO.setDsdCode( dsdBasicinfo.getDsdCode() );
        dsdBasicinfoVO.setColName( dsdBasicinfo.getColName() );
        dsdBasicinfoVO.setDataType( dsdBasicinfo.getDataType() );
        dsdBasicinfoVO.setDataCapacity( dsdBasicinfo.getDataCapacity() );
        dsdBasicinfoVO.setUseCodeId( dsdBasicinfo.getUseCodeId() );
        dsdBasicinfoVO.setCodeCol( dsdBasicinfo.getCodeCol() );
        dsdBasicinfoVO.setDsdLevel( dsdBasicinfo.getDsdLevel() );
        dsdBasicinfoVO.setDsdLevelId( dsdBasicinfo.getDsdLevelId() );
        dsdBasicinfoVO.setDescription( dsdBasicinfo.getDescription() );

        return dsdBasicinfoVO;
    }

    @Override
    public DsdBasicinfo useDsdBasicInfo(DsdBasicinfoVO dsdBasicinfoVO) {
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
        dsdBasicinfo.setUseCodeId( dsdBasicinfoVO.getUseCodeId() );
        dsdBasicinfo.setCodeCol( dsdBasicinfoVO.getCodeCol() );
        dsdBasicinfo.setDsdLevel( dsdBasicinfoVO.getDsdLevel() );
        dsdBasicinfo.setDsdLevelId( dsdBasicinfoVO.getDsdLevelId() );
        dsdBasicinfo.setDescription( dsdBasicinfoVO.getDescription() );

        return dsdBasicinfo;
    }
}
