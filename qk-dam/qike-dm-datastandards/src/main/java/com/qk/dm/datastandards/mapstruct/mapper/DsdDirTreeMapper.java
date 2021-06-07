package com.qk.dm.datastandards.mapstruct.mapper;

import com.qk.dm.datastandards.entity.DsdDir;
import com.qk.dm.datastandards.vo.DataStandardTreeVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 数据标准分类目录vo转换mapper
 *
 * @author wjq
 * @date 2021/6/4
 * @since 1.0.0
 */
@Mapper
public interface DsdDirTreeMapper {
    DsdDirTreeMapper INSTANCE = Mappers.getMapper(DsdDirTreeMapper.class);

    DataStandardTreeVO useDirTreeVO(DsdDir dsdDir);

}
