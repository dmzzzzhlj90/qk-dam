package com.qk.dm.datasource.mapstruct.mapper;

import com.qk.dm.datasource.entity.DsDir;
import com.qk.dm.datasource.vo.DsDirReturnVO;
import com.qk.dm.datasource.vo.DsDirVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 数据源连接目录vo和实体相互转换mapper
 *
 * @author zys
 * @date 20210729
 * @since 1.0.0
 */
@Mapper
public interface DsDirMapper {
  DsDirMapper INSTANCE = Mappers.getMapper(DsDirMapper.class);

  DsDirReturnVO useDsDirVO(DsDir dsDir);

  DsDir useDsDir(DsDirVO dsDirVO);
}
