package com.qk.dm.authority.mapstruct;

import com.qk.dm.authority.entity.QkQxResourcesApi;
import com.qk.dm.authority.vo.powervo.ResourceApiVO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface QxResourcesApiMapper {
  QxResourcesApiMapper INSTANCE = Mappers.getMapper(QxResourcesApiMapper.class);

  QkQxResourcesApi qxApiResources(ResourceApiVO resourceApiVO);

  ResourceApiVO qxApiResourcesVO(QkQxResourcesApi qkQxResourcesApi);

  List<ResourceApiVO> qxApiResourcesOf(List<QkQxResourcesApi> qxApiResourcesList);

  List<QkQxResourcesApi> ofResourcesApi(List<ResourceApiVO> qxResourcesList);
}
