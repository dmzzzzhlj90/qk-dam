package com.qk.dm.dataservice.mapstruct.mapper;

import com.qk.dm.dataservice.entity.DasApiCreate;
import com.qk.dm.dataservice.vo.DasApiCreateVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 新建API-vo转换mapper
 *
 * @author wjq
 * @date 20210823
 * @since 1.0.0
 */
@Mapper
public interface DasApiCreateMapper {
  DasApiCreateMapper INSTANCE = Mappers.getMapper(DasApiCreateMapper.class);

  DasApiCreateVO useDasApiCreateVO(DasApiCreate dasApiCreate);

  DasApiCreate useDasApiCreate(DasApiCreateVO dasApiDataSourceConfigVO);
}
