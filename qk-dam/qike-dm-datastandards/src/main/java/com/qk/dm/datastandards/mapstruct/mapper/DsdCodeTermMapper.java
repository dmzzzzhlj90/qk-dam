package com.qk.dm.datastandards.mapstruct.mapper;

import com.qk.dm.datastandards.entity.DsdCodeTerm;
import com.qk.dm.datastandards.vo.DsdCodeTermVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 数据标准码表信息vo转换mapper
 *
 * @author wjq
 * @date 2021/6/7
 * @since 1.0.0
 */
@Mapper
public interface DsdCodeTermMapper {
    DsdCodeTermMapper INSTANCE = Mappers.getMapper(DsdCodeTermMapper.class);

    DsdCodeTermVO usDsdCodeTermVO(DsdCodeTerm dsdCodeTerm);

    DsdCodeTerm useDsdCodeTerm(DsdCodeTermVO dsdCodeTermVO);
}
