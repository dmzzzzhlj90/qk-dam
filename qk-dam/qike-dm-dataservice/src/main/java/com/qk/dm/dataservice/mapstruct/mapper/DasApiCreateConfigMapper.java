package com.qk.dm.dataservice.mapstruct.mapper;

import com.qk.dm.dataservice.entity.DasApiCreateConfig;
import com.qk.dm.dataservice.vo.DasApiCreateConfigVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 新建API脚本方式-vo转换mapper
 *
 * @author wjq
 * @date 20210823
 * @since 1.0.0
 */
@Mapper
public interface DasApiCreateConfigMapper {
  DasApiCreateConfigMapper INSTANCE = Mappers.getMapper(DasApiCreateConfigMapper.class);

  DasApiCreateConfigVO useDasApiCreateConfigVO(DasApiCreateConfig dasApiCreateConfig);

  DasApiCreateConfig useDasApiCreateConfig(DasApiCreateConfigVO dasApiDataSourceConfigVO);
}
