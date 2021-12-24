package com.qk.dm.reptile.mapstruct.mapper;

import com.qk.dm.reptile.entity.RptDict;
import com.qk.dm.reptile.params.vo.RptDictVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RptDictMapper {

    RptDictMapper INSTANCE = Mappers.getMapper(RptDictMapper.class);

    List<RptDictVO> of(List<RptDict> rptDictList);
}
