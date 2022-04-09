package com.qk.dm.reptile.mapstruct.mapper;

import com.qk.dm.reptile.entity.RptConfigInfo;
import com.qk.dm.reptile.params.dto.RptConfigInfoDTO;
import com.qk.dm.reptile.params.vo.RptConfigInfoVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RptConfigInfoMapper {

    RptConfigInfoMapper INSTANCE = Mappers.getMapper(RptConfigInfoMapper.class);

    @Mappings({
            @Mapping(target = "raw",ignore = true),
            @Mapping(target = "formUrlencoded",ignore = true),
            @Mapping(target = "formData",ignore = true),
            @Mapping(target = "cookies",ignore = true),
            @Mapping(target = "headers",ignore = true)
    })
    RptConfigInfo useRptConfigInfo(RptConfigInfoDTO rptConfigInfoDTO);

    @Mappings({
            @Mapping(target = "raw",ignore = true),
            @Mapping(target = "formUrlencoded",ignore = true),
            @Mapping(target = "formData",ignore = true),
            @Mapping(target = "cookies",ignore = true),
            @Mapping(target = "headers",ignore = true)
    })
    RptConfigInfoVO useRptConfigInfoVO(RptConfigInfo rptConfigInfo);

    @Mappings({
            @Mapping(target = "raw",ignore = true),
            @Mapping(target = "formUrlencoded",ignore = true),
            @Mapping(target = "formData",ignore = true),
            @Mapping(target = "cookies",ignore = true),
            @Mapping(target = "headers",ignore = true)
    })
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void of(RptConfigInfoDTO rptConfigInfoDTO, @MappingTarget RptConfigInfo rptConfigInfo);

    List<RptConfigInfoVO> of(List<RptConfigInfo> rptConfigInfoList);

    @Mapping(target = "id",ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void of(RptConfigInfo info, @MappingTarget RptConfigInfo rptConfigInfo);



}
