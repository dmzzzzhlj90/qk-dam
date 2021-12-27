package com.qk.dm.reptile.mapstruct.mapper;

import com.qk.dm.reptile.entity.RptDict;
import com.qk.dm.reptile.params.vo.RptDictVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RptDictMapper {

    RptDictMapper INSTANCE = Mappers.getMapper(RptDictMapper.class);


    @Mappings({
            @Mapping(source = "code" , target = "value"),
            @Mapping(source = "name" , target = "label")
    })
    RptDictVO of(RptDict rptDict);

    List<RptDictVO> of(List<RptDict> rptDictList);
}
