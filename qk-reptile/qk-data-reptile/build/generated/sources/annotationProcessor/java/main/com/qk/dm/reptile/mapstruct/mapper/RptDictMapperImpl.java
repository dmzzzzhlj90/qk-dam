package com.qk.dm.reptile.mapstruct.mapper;

import com.qk.dm.reptile.entity.RptDict;
import com.qk.dm.reptile.params.vo.RptDictVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T20:19:49+0800",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.9.1.jar, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class RptDictMapperImpl implements RptDictMapper {

    @Override
    public RptDictVO of(RptDict rptDict) {
        if ( rptDict == null ) {
            return null;
        }

        RptDictVO rptDictVO = new RptDictVO();

        rptDictVO.setValue( rptDict.getCode() );
        rptDictVO.setLabel( rptDict.getName() );
        rptDictVO.setId( rptDict.getId() );
        rptDictVO.setPid( rptDict.getPid() );

        return rptDictVO;
    }

    @Override
    public List<RptDictVO> of(List<RptDict> rptDictList) {
        if ( rptDictList == null ) {
            return null;
        }

        List<RptDictVO> list = new ArrayList<RptDictVO>( rptDictList.size() );
        for ( RptDict rptDict : rptDictList ) {
            list.add( of( rptDict ) );
        }

        return list;
    }
}
