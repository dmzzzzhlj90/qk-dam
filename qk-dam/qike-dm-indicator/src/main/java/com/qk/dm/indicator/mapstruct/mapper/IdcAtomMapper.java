package com.qk.dm.indicator.mapstruct.mapper;

import com.qk.dm.indicator.entity.IdcAtom;
import com.qk.dm.indicator.params.dto.IdcAtomDTO;
import com.qk.dm.indicator.params.vo.IdcAtomPageVO;
import com.qk.dm.indicator.params.vo.IdcAtomVO;
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
public interface IdcAtomMapper {
    IdcAtomMapper INSTANCE = Mappers.getMapper(IdcAtomMapper.class);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void useIdcAtom(IdcAtomDTO idcAtomDTO, @MappingTarget IdcAtom idcAtom);

    IdcAtom useIdcAtom(IdcAtomDTO idcAtomDTO);

    IdcAtomVO useIdcAtomVO(IdcAtom idcAtom);

    List<IdcAtomVO> useIdcAtomVO(List<IdcAtom> idcAtom);

    List<IdcAtomPageVO> useIdcAtomPageVO(List<IdcAtom> idcAtom);
}
