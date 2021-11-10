package com.qk.dm.metadata.mapstruct.mapper;

import com.qk.dm.metadata.entity.MtdClassify;
import com.qk.dm.metadata.vo.MtdClassifyInfoVO;
import com.qk.dm.metadata.vo.MtdClassifyListVO;
import com.qk.dm.metadata.vo.MtdClassifyVO;
import java.util.Date;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-10T15:00:28+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class MtdClassifyMapperImpl implements MtdClassifyMapper {

    @Override
    public MtdClassify useMtdClassify(MtdClassifyVO mtdClassifyVO) {
        if ( mtdClassifyVO == null ) {
            return null;
        }

        MtdClassify mtdClassify = new MtdClassify();

        mtdClassify.setName( mtdClassifyVO.getName() );
        mtdClassify.setDescription( mtdClassifyVO.getDescription() );

        return mtdClassify;
    }

    @Override
    public MtdClassifyVO useMtdClassifyVO(MtdClassify mtdClassify) {
        if ( mtdClassify == null ) {
            return null;
        }

        MtdClassifyVO mtdClassifyVO = new MtdClassifyVO();

        mtdClassifyVO.setName( mtdClassify.getName() );
        mtdClassifyVO.setDescription( mtdClassify.getDescription() );

        return mtdClassifyVO;
    }

    @Override
    public MtdClassifyInfoVO useMtdClassifyInfoVO(MtdClassify mtdClassify) {
        if ( mtdClassify == null ) {
            return null;
        }

        Long id = null;
        Date gmtCreate = null;

        id = mtdClassify.getId();
        gmtCreate = mtdClassify.getGmtCreate();

        MtdClassifyInfoVO mtdClassifyInfoVO = new MtdClassifyInfoVO( id, gmtCreate );

        mtdClassifyInfoVO.setName( mtdClassify.getName() );
        mtdClassifyInfoVO.setDescription( mtdClassify.getDescription() );

        return mtdClassifyInfoVO;
    }

    @Override
    public MtdClassifyListVO useMtdClassifyListVO(MtdClassifyVO mtdClassifyVO) {
        if ( mtdClassifyVO == null ) {
            return null;
        }

        MtdClassifyListVO mtdClassifyListVO = new MtdClassifyListVO();

        mtdClassifyListVO.setName( mtdClassifyVO.getName() );

        return mtdClassifyListVO;
    }

    @Override
    public void updateMtdClassifyVO(MtdClassifyVO mtdClassifyVO, MtdClassify mtdClassify) {
        if ( mtdClassifyVO == null ) {
            return;
        }

        mtdClassify.setName( mtdClassifyVO.getName() );
        mtdClassify.setDescription( mtdClassifyVO.getDescription() );
    }
}
