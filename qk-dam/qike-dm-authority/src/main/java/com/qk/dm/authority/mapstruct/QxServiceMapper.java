package com.qk.dm.authority.mapstruct;

import com.qk.dm.authority.entity.QxService;
import com.qk.dm.authority.vo.powervo.ServiceQueryVO;
import com.qk.dm.authority.vo.powervo.ServiceVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 权限管理-服务实体和VO转换
 */
@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface QxServiceMapper {
  QxServiceMapper INSTANCE =Mappers.getMapper(QxServiceMapper.class);

  QxService qxService(ServiceVO serviceVO);

  @Mappings({
      @Mapping(source = "serviceId", target = "value"),
      @Mapping(source = "serviceName", target = "title")
  })
  ServiceVO qxServiceVO(QxService qxService);

  List<ServiceVO> of(List<QxService> list);

  @Mappings({
      @Mapping(source = "serviceName", target = "name"),
      @Mapping(source = "serviceId", target = "id")
  })
  ServiceQueryVO serviceQueryVO (QxService qxService);

  List<ServiceQueryVO> serviceQueryVOof(List<QxService> qxServiceList);
}
