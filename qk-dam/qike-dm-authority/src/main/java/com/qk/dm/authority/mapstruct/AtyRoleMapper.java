package com.qk.dm.authority.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

/**
 * @author shenpj
 * @date 2022/2/23 16:35
 * @since 1.0.0
 */
@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface AtyRoleMapper {
    AtyRoleMapper INSTANCE = Mappers.getMapper(AtyRoleMapper.class);

//    AtyClientRoleInfoVO userRole(RoleRepresentation roleRepresentation);
//
//    List<AtyClientRoleInfoVO> userRole(List<RoleRepresentation> roleRepresentation);
}
