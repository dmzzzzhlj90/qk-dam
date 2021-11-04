package com.qk.dm.indicator.mapstruct.mapper;

import com.qk.dm.indicator.entity.IdcBusiness;
import com.qk.dm.indicator.params.dto.IdcBusinessDTO;
import com.qk.dm.indicator.params.vo.IdcBusinessVO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface IdcBusinessMapper {

    IdcBusinessMapper INSTANCE = Mappers.getMapper(IdcBusinessMapper.class);

    IdcBusiness useIdcBusiness(IdcBusinessDTO idcBusinessDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void useIdcBusiness(IdcBusinessDTO idcBusinessDTO, @MappingTarget IdcBusiness idcBusiness);

    IdcBusinessVO useIdcBusinessVO(IdcBusiness idcBusiness);

    List<IdcBusinessVO> userIdcBusinessListVO(List<IdcBusiness> idcBusinessList);
}
