package com.qk.dm.metadata.mapstruct.mapper;

import com.qk.dm.metadata.entity.MtdLabelsAtlas;
import com.qk.dm.metadata.vo.MtdLabelsAtlasVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-20T14:27:54+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.2 (Oracle Corporation)"
)
public class MtdLabelsAtlasMapperImpl implements MtdLabelsAtlasMapper {

    @Override
    public MtdLabelsAtlas useMtdLabelsAtlas(MtdLabelsAtlasVO mtdLabelsAtlasVO) {
        if ( mtdLabelsAtlasVO == null ) {
            return null;
        }

        MtdLabelsAtlas mtdLabelsAtlas = new MtdLabelsAtlas();

        mtdLabelsAtlas.setGuid( mtdLabelsAtlasVO.getGuid() );
        mtdLabelsAtlas.setLabels( mtdLabelsAtlasVO.getLabels() );

        return mtdLabelsAtlas;
    }

    @Override
    public MtdLabelsAtlasVO useMtdLabelsAtlasVO(MtdLabelsAtlas mtdLabelsAtlas) {
        if ( mtdLabelsAtlas == null ) {
            return null;
        }

        MtdLabelsAtlasVO mtdLabelsAtlasVO = new MtdLabelsAtlasVO();

        mtdLabelsAtlasVO.setGuid( mtdLabelsAtlas.getGuid() );
        mtdLabelsAtlasVO.setLabels( mtdLabelsAtlas.getLabels() );

        return mtdLabelsAtlasVO;
    }

    @Override
    public void useMtdLabelsAtlas(MtdLabelsAtlasVO mtdLabelsAtlasVO, MtdLabelsAtlas mtdLabelsAtlas) {
        if ( mtdLabelsAtlasVO == null ) {
            return;
        }

        if ( mtdLabelsAtlasVO.getGuid() != null ) {
            mtdLabelsAtlas.setGuid( mtdLabelsAtlasVO.getGuid() );
        }
        if ( mtdLabelsAtlasVO.getLabels() != null ) {
            mtdLabelsAtlas.setLabels( mtdLabelsAtlasVO.getLabels() );
        }
    }

    @Override
    public List<MtdLabelsAtlasVO> useMtdLabelsAtlasListVO(List<MtdLabelsAtlas> mtdLabelsAtlass) {
        if ( mtdLabelsAtlass == null ) {
            return null;
        }

        List<MtdLabelsAtlasVO> list = new ArrayList<MtdLabelsAtlasVO>( mtdLabelsAtlass.size() );
        for ( MtdLabelsAtlas mtdLabelsAtlas : mtdLabelsAtlass ) {
            list.add( useMtdLabelsAtlasVO( mtdLabelsAtlas ) );
        }

        return list;
    }
}
