package com.qk.dm.authority.mapstruct;

import com.qk.dm.authority.entity.QxResources;
import com.qk.dm.authority.vo.powervo.*;
import org.mapstruct.*;
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

  @Mappings({
      @Mapping(source = "resourcesid", target = "value"),
      @Mapping(source = "name", target = "title"),
      @Mapping(source = "childrenList", target = "children")
  })
  ResourceQueryVO resourceOutVOof (ResourceOutVO resourceOutVO);

  List<ResourceQueryVO> resourceOutVOlist(List<ResourceOutVO> resourceOutVOList);
  @Mappings({
      @Mapping(source = "id", target = "id"),
      @Mapping(source = "name", target = "name"),
      @Mapping(source = "path", target = "path"),
      @Mapping(source = "childrenList", target = "chirdren")
  })
  EmpResourceUrlVO resourceUrl (ResourceOutVO resourceOutVO);

  List<EmpResourceUrlVO> resourceUrlOF(List<ResourceOutVO> resourceOutVOLists);
}
