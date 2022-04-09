package com.qk.dm.reptile.mapstruct.mapper;

import com.qk.dm.reptile.entity.RptSelectorColumnInfo;
import com.qk.dm.reptile.params.dto.RptSelectorColumnInfoDTO;
import com.qk.dm.reptile.params.vo.RptSelectorColumnInfoVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
@Mapper
public interface RptSelectorColumnInfoMapper {
    RptSelectorColumnInfoMapper INSTANCE = Mappers.getMapper(RptSelectorColumnInfoMapper.class);

    RptSelectorColumnInfo useRptSelectorColumnInfo(RptSelectorColumnInfoDTO rptSelectorColumnInfoDTO);

    RptSelectorColumnInfoVO useRptSelectorColumnInfoVO(RptSelectorColumnInfo rptSelectorColumnInfo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void of(RptSelectorColumnInfoDTO rptSelectorColumnInfoDTO, @MappingTarget RptSelectorColumnInfo rptSelectorColumnInfo);

    List<RptSelectorColumnInfoVO> of(List<RptSelectorColumnInfo> rptSelectorColumnInfoList);

    List<RptSelectorColumnInfo> ofList(List<RptSelectorColumnInfoDTO> rptSelectorColumnInfoDTOList);

    @Mapping(target = "id",ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void of(RptSelectorColumnInfo source, @MappingTarget RptSelectorColumnInfo target);
}
