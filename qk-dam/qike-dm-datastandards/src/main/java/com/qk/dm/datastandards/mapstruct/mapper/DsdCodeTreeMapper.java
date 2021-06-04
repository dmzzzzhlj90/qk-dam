package com.qk.dm.datastandards.mapstruct.mapper;

import com.qk.dm.datastandards.entity.DsdCodeDir;
import com.qk.dm.datastandards.vo.DataStandardCodeTreeVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author wjq
 * @date 2021/6/4 16:52
 * @since 1.0.0
 * 数据标准__码表目录vo转换mapper
 */
@Mapper
public interface DsdCodeTreeMapper {
    DsdCodeTreeMapper INSTANCE = Mappers.getMapper(DsdCodeTreeMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "codeDirId", target = "codeDirId")
    @Mapping(source = "codeDirName", target = "codeDirName")
    @Mapping(source = "parentId", target = "parentId")
    DataStandardCodeTreeVO useCodeTreeVO(DsdCodeDir dsdCodeDir);

}
