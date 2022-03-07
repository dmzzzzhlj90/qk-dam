package com.qk.dm.authority.mapstruct;

import com.qk.dm.authority.vo.user.AtyUserCreateVO;
import com.qk.dm.authority.vo.user.AtyUserInfoVO;
import com.qk.dm.authority.vo.user.AtyUserKeyCloakVO;
import com.qk.dm.authority.vo.user.AtyUserUpdateVO;
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

    @Mapping(target = "createdTimestamp", expression = "java(com.qk.dm.authority.util.TimeUtil.parseLong(userRepresentation.getCreatedTimestamp()))")
    AtyUserInfoVO userInfo(UserRepresentation userRepresentation);

    @Mapping(target = "createdTimestamp", expression = "java(com.qk.dm.authority.util.TimeUtil.parseLong(userRepresentation.getCreatedTimestamp()))")
    List<AtyUserInfoVO> userInfo(List<UserRepresentation> userRepresentation);

    UserRepresentation userInfo(AtyUserKeyCloakVO userVO);

    AtyUserKeyCloakVO userInfo(AtyUserCreateVO userVO);

    AtyUserKeyCloakVO userInfo(AtyUserUpdateVO atyUserVO);

    void userUpdate(AtyUserKeyCloakVO userVO, @MappingTarget UserRepresentation userRepresentation);
}
