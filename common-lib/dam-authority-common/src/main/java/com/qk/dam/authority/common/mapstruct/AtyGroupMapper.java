package com.qk.dam.authority.common.mapstruct;

import com.qk.dam.authority.common.vo.group.AtyGroupInfoVO;
import org.keycloak.representations.idm.GroupRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author shenpj
 * @date 2022/3/3 14:28
 * @since 1.0.0
 */
@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface AtyGroupMapper {
    AtyGroupMapper INSTANCE = Mappers.getMapper(AtyGroupMapper.class);

    AtyGroupInfoVO userGroup(GroupRepresentation groupRepresentation);

    List<AtyGroupInfoVO> userGroup(List<GroupRepresentation> groupRepresentation);
}
