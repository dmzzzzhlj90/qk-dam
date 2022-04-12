package com.qk.dm.dataingestion.mapstruct.mapper;

import com.qk.dm.dataingestion.entity.DisMigrationBaseInfo;
import com.qk.dm.dataingestion.vo.DisMigrationBaseInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface DisBaseInfoMapper {
    DisBaseInfoMapper INSTANCE = Mappers.getMapper(DisBaseInfoMapper.class);

    DisMigrationBaseInfo of(DisMigrationBaseInfoVO disMigrationBaseInfoVO);

    List<DisMigrationBaseInfo> list(List<DisMigrationBaseInfoVO> baseInfoVOList);

    DisMigrationBaseInfoVO of(DisMigrationBaseInfo baseInfo);

    List<DisMigrationBaseInfoVO> listVO(List<DisMigrationBaseInfo> disMigrationBaseInfoList);
}
