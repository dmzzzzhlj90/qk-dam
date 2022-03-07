package com.qk.dm.authority.mapstruct;

import com.qk.dm.authority.entity.QxService;
import com.qk.dm.authority.vo.powervo.ServiceVO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
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

  ServiceVO qxServiceVO(QxService qxService);

  List<ServiceVO> of(List<QxService> list);
}
