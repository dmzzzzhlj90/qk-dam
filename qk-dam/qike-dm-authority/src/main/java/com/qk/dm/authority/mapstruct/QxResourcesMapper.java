package com.qk.dm.authority.mapstruct;

import com.qk.dm.authority.entity.QxResources;
import com.qk.dm.authority.vo.powervo.ResourceExcelVO;
import com.qk.dm.authority.vo.powervo.ResourceOutVO;
import com.qk.dm.authority.vo.powervo.ResourceVO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 权限管理-资源实体和VO转换
 */
@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface QxResourcesMapper {
  QxResourcesMapper INSTANCE = Mappers.getMapper(QxResourcesMapper.class);

  QxResources qxResources(ResourceVO resourceVO);

  List<ResourceOutVO> of(List<QxResources> list);

  List<ResourceVO> qxResourcesOf(List<QxResources> list);

  ResourceVO qxResourceVO(QxResources qxResources);

  QxResources qxResourcesExcel(ResourceExcelVO resourceExcelVO);

  List<QxResources> ofResourcesVO(List<ResourceVO> qxResourcesList);

  List<ResourceExcelVO> ofExcelVO(List<QxResources> list);
}
