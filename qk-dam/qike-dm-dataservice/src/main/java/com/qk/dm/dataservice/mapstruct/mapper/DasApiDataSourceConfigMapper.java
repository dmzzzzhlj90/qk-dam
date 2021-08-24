package com.qk.dm.dataservice.mapstruct.mapper;

import com.qk.dm.dataservice.entity.DasApiDatasourceConfig;
import com.qk.dm.dataservice.vo.DasApiDataSourceConfigVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 新建-vo转换mapper
 *
 * @author wjq
 * @date 20210823
 * @since 1.0.0
 */
@Mapper
public interface DasApiDataSourceConfigMapper {
  DasApiDataSourceConfigMapper INSTANCE = Mappers.getMapper(DasApiDataSourceConfigMapper.class);

  DasApiDataSourceConfigVO useDasApiDataSourceConfigVO(
      DasApiDatasourceConfig dasApiDatasourceConfig);

  DasApiDatasourceConfig useDasApiDatasourceConfig(
      DasApiDataSourceConfigVO dasApiDataSourceConfigVO);
}
