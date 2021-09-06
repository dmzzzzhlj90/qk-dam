package com.qk.dm.indicator.mapstruct.mapper;

import com.qk.dm.indicator.entity.IdcComposite;
import com.qk.dm.indicator.params.dto.IdcCompositeDTO;
import com.qk.dm.indicator.params.vo.IdcCompositePageVO;
import com.qk.dm.indicator.params.vo.IdcCompositeVO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author shenpj
 * @date 2021/9/1 2:48 下午
 * @since 1.0.0
 */
@Mapper
//        (nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
//                nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface IdcCompositeMapper {
  IdcCompositeMapper INSTANCE = Mappers.getMapper(IdcCompositeMapper.class);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void useIdc(IdcCompositeDTO idcCompositeDTO, @MappingTarget IdcComposite idcComposite);

  IdcComposite useIdc(IdcCompositeDTO idcCompositeDTO);

  IdcCompositeVO useIdcVO(IdcComposite idcComposite);

  List<IdcCompositeVO> useIdcVO(List<IdcComposite> idcComposites);

  List<IdcCompositePageVO> useIdcPageVO(List<IdcComposite> idcComposites);
}
