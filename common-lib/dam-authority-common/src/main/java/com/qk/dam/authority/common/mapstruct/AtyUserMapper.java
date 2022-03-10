package com.qk.dam.authority.common.mapstruct;

import com.qk.dam.authority.common.vo.user.AtyUserInfoVO;
import com.qk.dam.authority.common.vo.user.AtyUserInputExceVO;
import com.qk.dam.authority.common.vo.user.AtyUserKeyCloakVO;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author shenpj
 * @date 2022/2/23 16:35
 * @since 1.0.0
 */
@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface AtyUserMapper {
    AtyUserMapper INSTANCE = Mappers.getMapper(AtyUserMapper.class);

    @Mapping(target = "createdTimestamp", expression = "java(com.qk.dam.authority.common.utils.TimeUtil.parseLong(userRepresentation.getCreatedTimestamp()))")
    AtyUserInfoVO userInfo(UserRepresentation userRepresentation);

    @Mapping(target = "createdTimestamp", expression = "java(com.qk.dam.authority.common.utils.TimeUtil.parseLong(userRepresentation.getCreatedTimestamp()))")
    List<AtyUserInfoVO> userInfo(List<UserRepresentation> userRepresentation);

    UserRepresentation userInfo(AtyUserKeyCloakVO userVO);

    UserRepresentation userExcelInfo(AtyUserInputExceVO atyUserInputExceVO);
}
