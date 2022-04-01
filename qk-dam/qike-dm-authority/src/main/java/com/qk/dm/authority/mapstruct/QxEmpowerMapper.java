package com.qk.dm.authority.mapstruct;

import com.qk.dm.authority.entity.QxEmpower;
import com.qk.dm.authority.vo.powervo.EmpowerAllVO;
import com.qk.dm.authority.vo.powervo.EmpowerVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 权限管理-授权信息实体和VO转换
 */
@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface QxEmpowerMapper {
  QxEmpowerMapper INSTANCE = Mappers.getMapper(QxEmpowerMapper.class);

  QxEmpower qxEmpower(EmpowerVO empowerVO);

  EmpowerVO qxEmpowerVO(QxEmpower qxEmpower);

  List<EmpowerVO> of(List<QxEmpower> list);

  List<EmpowerAllVO> ofEmpowerAllVO(List<QxEmpower> qxEmpoerList);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void from(EmpowerVO empowerVO, @MappingTarget QxEmpower qxEmpower);
}
