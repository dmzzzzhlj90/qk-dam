package com.qk.dm.indicator.mapstruct.mapper;

import com.qk.dm.indicator.entity.IdcDerived;
import com.qk.dm.indicator.params.dto.IdcDerivedDTO;
import com.qk.dm.indicator.params.vo.IdcDerivedVO;
import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IdcDerivedMapper {

  IdcDerivedMapper INSTANCE = Mappers.getMapper(IdcDerivedMapper.class);

  IdcDerived useIdcDerived(IdcDerivedDTO idcDerivedDTO);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void useIdcDerived(IdcDerivedDTO idcDerivedDTO, @MappingTarget IdcDerived idcDerived);

  IdcDerivedVO useIdcDerivedVO(IdcDerived idcDerived);

  List<IdcDerivedVO> userIdcDerivedListVO(List<IdcDerived> idcDerivedList);
}
