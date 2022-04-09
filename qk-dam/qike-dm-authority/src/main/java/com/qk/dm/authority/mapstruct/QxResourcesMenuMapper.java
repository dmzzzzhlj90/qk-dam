package com.qk.dm.authority.mapstruct;

import com.qk.dm.authority.entity.QkQxResourcesMenu;
import com.qk.dm.authority.vo.powervo.EmpResourceUrlVO;
import com.qk.dm.authority.vo.powervo.ResourceMenuExcelVO;
import com.qk.dm.authority.vo.powervo.ResourceMenuQueryVO;
import com.qk.dm.authority.vo.powervo.ResourceMenuVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface QxResourcesMenuMapper {
  QxResourcesMenuMapper INSTANCE = Mappers.getMapper(QxResourcesMenuMapper.class);

  QkQxResourcesMenu qxResourcesMenu(ResourceMenuVO resourceMenuVO);

  @Mappings({
      @Mapping(source = "resourcesId", target = "value"),
      @Mapping(source = "name", target = "title")
  })
  ResourceMenuQueryVO ResourceMenuQueryVO(QkQxResourcesMenu qkQxResourcesMenu);

  List<ResourceMenuQueryVO> ResourceMenuQueryVOof(List<QkQxResourcesMenu> qkQxResourcesMenuList);

  QkQxResourcesMenu qxResourcesMenuExcel(ResourceMenuExcelVO resourceMenuExcelVO);

  ResourceMenuExcelVO ResourceExcelVO(QkQxResourcesMenu qkQxResourcesMenu);

  List<ResourceMenuExcelVO> ofResourceExcelVO(List<QkQxResourcesMenu> qxResourcesMenuList);

  void from(ResourceMenuVO resourceMenuVO, @MappingTarget QkQxResourcesMenu qkQxResourcesMenuOne);

  @Mappings({
      @Mapping(source = "title", target = "name"),
      @Mapping(source = "children", target = "route")
  })
  EmpResourceUrlVO empResourceUrlVO(ResourceMenuQueryVO resourceMenuQueryVO);

  List<EmpResourceUrlVO> ofEmpResourceUrlVO(List<ResourceMenuQueryVO> trees);
}
