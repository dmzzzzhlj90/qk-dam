package com.qk.dm.reptile.mapstruct.mapper;

import com.qk.dm.reptile.entity.RptConfigInfo;
import com.qk.dm.reptile.params.dto.RptConfigInfoDTO;
import com.qk.dm.reptile.params.vo.RptConfigInfoVO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RptConfigInfoMapper {

    RptConfigInfoMapper INSTANCE = Mappers.getMapper(RptConfigInfoMapper.class);

    RptConfigInfo useRptConfigInfo(RptConfigInfoDTO rptConfigInfoDTO);

    RptConfigInfoVO useRptConfigInfoVO(RptConfigInfo rptConfigInfo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void of(RptConfigInfoDTO rptConfigInfoDTO, @MappingTarget RptConfigInfo rptConfigInfo);

    List<RptConfigInfoVO> of(List<RptConfigInfo> rptConfigInfoList);
}
