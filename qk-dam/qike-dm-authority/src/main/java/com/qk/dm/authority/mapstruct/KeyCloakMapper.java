package com.qk.dm.authority.mapstruct;

import com.qk.dm.authority.vo.ClientVO;
import com.qk.dm.authority.vo.RealmVO;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
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

    RealmVO userRealm(RealmRepresentation realmRepresentation);

    List<RealmVO> userRealm(List<RealmRepresentation> realmRepresentation);

    ClientVO userClient(ClientRepresentation clientRepresentation);

    List<ClientVO> userClient(List<ClientRepresentation> clientRepresentation);
}
