package com.qk.dm.authority.mapstruct;

import com.qk.dm.authority.vo.*;
import org.keycloak.representations.idm.*;
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
public interface KeyCloakMapper {
    KeyCloakMapper INSTANCE = Mappers.getMapper(KeyCloakMapper.class);

    RoleVO userRole(RoleRepresentation roleRepresentation);

    List<RoleVO> userRole(List<RoleRepresentation> roleRepresentation);

    GroupVO userGroup(GroupRepresentation groupRepresentation);

    List<GroupVO> userGroup(List<GroupRepresentation> groupRepresentation);

    UserInfoVO userInfo(UserRepresentation userRepresentation);

    List<UserInfoVO> userInfo(List<UserRepresentation> userRepresentation);

    UserRepresentation userInfo(UserVO userVO);

    void userUpdate(UserVO userVO, @MappingTarget UserRepresentation userRepresentation);

    RealmVO userRealm(RealmRepresentation realmRepresentation);

    List<RealmVO> userRealm(List<RealmRepresentation> realmRepresentation);

    ClientVO userClient(ClientRepresentation clientRepresentation);

    List<ClientVO> userClient(List<ClientRepresentation> clientRepresentation);
}
