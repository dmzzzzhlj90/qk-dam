package com.qk.dm.dataingestion.mapstruct.mapper;

import com.qk.dm.dataingestion.entity.DisColumnInfo;
import com.qk.dm.dataingestion.vo.DisColumnInfoVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface DisColumnInfoMapper {
    DisColumnInfoMapper INSTANCE = Mappers.getMapper(DisColumnInfoMapper.class);
    @Mappings({
            @Mapping(target = "id",ignore = true),
            @Mapping(target = "gmtCreate",ignore = true),
            @Mapping(target = "gmtModified",ignore = true),
            @Mapping(target = "delFlag",ignore = true)

    })
    DisColumnInfo of(DisColumnInfoVO disColumnInfoVO);

    List<DisColumnInfo> list(List<DisColumnInfoVO> disColumnInfoVOList);

    DisColumnInfoVO of(DisColumnInfo disColumnInfo);

    List<DisColumnInfoVO> listVO(List<DisColumnInfo> disColumnInfoList);
}
