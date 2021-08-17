package com.qk.dm.datasource.mapstruct.mapper;

import com.qk.dm.datasource.entity.DsDirectory;
import com.qk.dm.datasource.vo.DsDirectoryVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 应用系统录入vo和实体相互转换mapper
 *
 * @author zys
 * @date 20210729
 * @since 1.0.0
 */
@Mapper
public interface DsDirectoryMapper {
  DsDirectoryMapper INSTANCE = Mappers.getMapper(DsDirectoryMapper.class);

  DsDirectoryVO useDsDirectoryVO(DsDirectory dsDirectory);

  DsDirectory useDsDirectory(DsDirectoryVO dsDirectoryVO);
}
