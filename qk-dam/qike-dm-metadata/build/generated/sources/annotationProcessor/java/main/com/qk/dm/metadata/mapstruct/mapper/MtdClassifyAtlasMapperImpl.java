package com.qk.dm.metadata.mapstruct.mapper;

import com.qk.dm.metadata.entity.MtdClassifyAtlas;
import com.qk.dm.metadata.vo.MtdClassifyAtlasVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T20:19:46+0800",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.9.1.jar, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class MtdClassifyAtlasMapperImpl implements MtdClassifyAtlasMapper {

    @Override
    public MtdClassifyAtlas useMtdClassifyAtlas(MtdClassifyAtlasVO mtdClassifyAtlasVO) {
        if ( mtdClassifyAtlasVO == null ) {
            return null;
        }

        MtdClassifyAtlas mtdClassifyAtlas = new MtdClassifyAtlas();

        mtdClassifyAtlas.setGuid( mtdClassifyAtlasVO.getGuid() );
        mtdClassifyAtlas.setClassify( mtdClassifyAtlasVO.getClassify() );

        return mtdClassifyAtlas;
    }

    @Override
    public MtdClassifyAtlasVO useMtdClassifyAtlasVO(MtdClassifyAtlas mtdClassifyAtlas) {
        if ( mtdClassifyAtlas == null ) {
            return null;
        }

        MtdClassifyAtlasVO mtdClassifyAtlasVO = new MtdClassifyAtlasVO();

        mtdClassifyAtlasVO.setGuid( mtdClassifyAtlas.getGuid() );
        mtdClassifyAtlasVO.setClassify( mtdClassifyAtlas.getClassify() );

        return mtdClassifyAtlasVO;
    }

    @Override
    public void useMtdClassifyAtlas(MtdClassifyAtlasVO mtdClassifyAtlasVO, MtdClassifyAtlas mtdClassifyAtlas) {
        if ( mtdClassifyAtlasVO == null ) {
            return;
        }

        if ( mtdClassifyAtlasVO.getGuid() != null ) {
            mtdClassifyAtlas.setGuid( mtdClassifyAtlasVO.getGuid() );
        }
        if ( mtdClassifyAtlasVO.getClassify() != null ) {
            mtdClassifyAtlas.setClassify( mtdClassifyAtlasVO.getClassify() );
        }
    }

    @Override
    public List<MtdClassifyAtlasVO> useMtdClassifyAtlasListVO(List<MtdClassifyAtlas> mtdClassifyAtlass) {
        if ( mtdClassifyAtlass == null ) {
            return null;
        }

        List<MtdClassifyAtlasVO> list = new ArrayList<MtdClassifyAtlasVO>( mtdClassifyAtlass.size() );
        for ( MtdClassifyAtlas mtdClassifyAtlas : mtdClassifyAtlass ) {
            list.add( useMtdClassifyAtlasVO( mtdClassifyAtlas ) );
        }

        return list;
    }
}
