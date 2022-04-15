package com.qk.dm.dataingestion.mapstruct.mapper;

import com.qk.dm.dataingestion.entity.DisMigrationBaseInfo;
import com.qk.dm.dataingestion.vo.DisMigrationBaseInfoVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface DisBaseInfoMapper {
    DisBaseInfoMapper INSTANCE = Mappers.getMapper(DisBaseInfoMapper.class);
    @Mappings({
            @Mapping(target = "delFlag",ignore = true)
    })
    DisMigrationBaseInfo of(DisMigrationBaseInfoVO disMigrationBaseInfoVO);

    List<DisMigrationBaseInfo> list(List<DisMigrationBaseInfoVO> baseInfoVOList);

    DisMigrationBaseInfoVO of(DisMigrationBaseInfo baseInfo);

    List<DisMigrationBaseInfoVO> listVO(List<DisMigrationBaseInfo> disMigrationBaseInfoList);
}
