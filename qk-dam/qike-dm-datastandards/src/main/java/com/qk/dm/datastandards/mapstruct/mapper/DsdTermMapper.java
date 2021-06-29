package com.qk.dm.datastandards.mapstruct.mapper;

import com.qk.dm.datastandards.entity.DsdTerm;
import com.qk.dm.datastandards.vo.DsdTermVO;
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
public interface DsdTermMapper {
  DsdTermMapper INSTANCE = Mappers.getMapper(DsdTermMapper.class);

  DsdTermVO useDsdTermVO(DsdTerm dsdTerm);

  DsdTerm useDsdTerm(DsdTermVO dsdTermVO);
}
