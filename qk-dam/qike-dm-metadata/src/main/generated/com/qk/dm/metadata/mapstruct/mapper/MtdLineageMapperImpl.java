package com.qk.dm.metadata.mapstruct.mapper;

import com.qk.dm.metadata.vo.MtdLineageDetailVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.apache.atlas.model.instance.AtlasEntityHeader;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-10T15:00:28+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class MtdLineageMapperImpl implements MtdLineageMapper {

    @Override
    public List<MtdLineageDetailVO> userMtdLineageDetailVO(List<AtlasEntityHeader> atlasEntityHeaderList) {
        if ( atlasEntityHeaderList == null ) {
            return null;
        }

        List<MtdLineageDetailVO> list = new ArrayList<MtdLineageDetailVO>( atlasEntityHeaderList.size() );
        for ( AtlasEntityHeader atlasEntityHeader : atlasEntityHeaderList ) {
            list.add( atlasEntityHeaderToMtdLineageDetailVO( atlasEntityHeader ) );
        }

        return list;
    }

    protected String[] stringSetToStringArray(Set<String> set) {
        if ( set == null ) {
            return null;
        }

        String[] stringTmp = new String[set.size()];
        int i = 0;
        for ( String string : set ) {
            stringTmp[i] = string;
            i++;
        }

        return stringTmp;
    }

    protected MtdLineageDetailVO atlasEntityHeaderToMtdLineageDetailVO(AtlasEntityHeader atlasEntityHeader) {
        if ( atlasEntityHeader == null ) {
            return null;
        }

        MtdLineageDetailVO mtdLineageDetailVO = new MtdLineageDetailVO();

        mtdLineageDetailVO.setGuid( atlasEntityHeader.getGuid() );
        mtdLineageDetailVO.setDisplayText( atlasEntityHeader.getDisplayText() );
        mtdLineageDetailVO.setTypeName( atlasEntityHeader.getTypeName() );
        if ( atlasEntityHeader.getStatus() != null ) {
            mtdLineageDetailVO.setStatus( atlasEntityHeader.getStatus().name() );
        }
        mtdLineageDetailVO.setLabels( stringSetToStringArray( atlasEntityHeader.getLabels() ) );

        return mtdLineageDetailVO;
    }
}
