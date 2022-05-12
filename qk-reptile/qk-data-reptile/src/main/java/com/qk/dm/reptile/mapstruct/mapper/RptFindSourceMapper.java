package com.qk.dm.reptile.mapstruct.mapper;

import com.qk.dm.reptile.entity.RptFindSource;
import com.qk.dm.reptile.params.dto.RptFindSourceDTO;
import com.qk.dm.reptile.params.vo.RptFindSourceVO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RptFindSourceMapper {
    RptFindSourceMapper INSTANCE = Mappers.getMapper(RptFindSourceMapper.class);

    RptFindSource of(RptFindSourceDTO rptFindSourceDTO);

    RptFindSourceVO of(RptFindSource rptFindSource);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void of(RptFindSourceDTO rptFindSourceDTO, @MappingTarget RptFindSource rptFindSource);

    List<RptFindSourceVO> of(List<RptFindSource> rptFindSourceList);
}
