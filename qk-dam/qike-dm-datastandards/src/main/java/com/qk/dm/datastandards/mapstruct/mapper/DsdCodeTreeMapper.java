package com.qk.dm.datastandards.mapstruct.mapper;

import com.qk.dm.datastandards.entity.DsdCodeDir;
import com.qk.dm.datastandards.vo.DataStandardCodeTreeVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 数据标准码表目录vo转换mapper
 *
 * @author wjq
 * @date 2021/6/4
 * @since 1.0.0
 */
@Mapper
public interface DsdCodeTreeMapper {
    DsdCodeTreeMapper INSTANCE = Mappers.getMapper(DsdCodeTreeMapper.class);

    DataStandardCodeTreeVO useCodeTreeVO(DsdCodeDir dsdCodeDir);

}
