package com.qk.dm.datastandards.mapstruct.mapper;

import com.qk.dm.datastandards.entity.DsdDir;
import com.qk.dm.datastandards.vo.DataStandardTreeVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author wjq
 * @date 2021/6/4 16:52
 * @since 1.0.0
 * 数据标准__分类目录vo转换mapper
 */
@Mapper
public interface DsdDirTreeMapper {
    DsdDirTreeMapper INSTANCE = Mappers.getMapper(DsdDirTreeMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "dirDsdId", target = "dirDsdId")
    @Mapping(source = "dirDsdName", target = "dirDsdName")
    @Mapping(source = "parentId", target = "parentId")
    DataStandardTreeVO useDirTreeVO(DsdDir dsdDir);

}
