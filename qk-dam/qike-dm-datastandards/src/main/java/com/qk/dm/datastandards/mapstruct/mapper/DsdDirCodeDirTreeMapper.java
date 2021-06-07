package com.qk.dm.datastandards.mapstruct.mapper;

import com.qk.dm.datastandards.entity.DsdCodeDir;
import com.qk.dm.datastandards.entity.DsdDir;
import com.qk.dm.datastandards.vo.DsdCodeDirVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 数据标准码表vo转换mapper
 *
 * @author wjq
 * @date 2021/6/4
 * @since 1.0.0
 */
@Mapper
public interface DsdDirCodeDirTreeMapper {
    DsdDirCodeDirTreeMapper INSTANCE = Mappers.getMapper(DsdDirCodeDirTreeMapper.class);

    DsdCodeDirVO useDsdCodeDirVO(DsdCodeDir dsdCodeDir);

    DsdCodeDir useDsdCodeDir(DsdCodeDirVO dsdDirVO);
}
