package com.qk.dam.authority.common.mapstruct;

import com.qk.dam.authority.common.vo.ClientVO;
import com.qk.dam.authority.common.vo.ClientVO.ClientVOBuilder;
import com.qk.dam.authority.common.vo.RealmVO;
import com.qk.dam.authority.common.vo.RealmVO.RealmVOBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T17:36:25+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class KeyCloakMapperImpl implements KeyCloakMapper {

    @Override
    public RealmVO userRealm(RealmRepresentation realmRepresentation) {
        if ( realmRepresentation == null ) {
            return null;
        }

        RealmVOBuilder realmVO = RealmVO.builder();

        if ( realmRepresentation.getId() != null ) {
            realmVO.id( realmRepresentation.getId() );
        }
        if ( realmRepresentation.getRealm() != null ) {
            realmVO.realm( realmRepresentation.getRealm() );
        }
        if ( realmRepresentation.getDisplayName() != null ) {
            realmVO.displayName( realmRepresentation.getDisplayName() );
        }
        if ( realmRepresentation.getDisplayNameHtml() != null ) {
            realmVO.displayNameHtml( realmRepresentation.getDisplayNameHtml() );
        }

        return realmVO.build();
    }

    @Override
    public List<RealmVO> userRealm(List<RealmRepresentation> realmRepresentation) {
        if ( realmRepresentation == null ) {
            return null;
        }

        List<RealmVO> list = new ArrayList<RealmVO>( realmRepresentation.size() );
        for ( RealmRepresentation realmRepresentation1 : realmRepresentation ) {
            list.add( userRealm( realmRepresentation1 ) );
        }

        return list;
    }

    @Override
    public ClientVO userClient(ClientRepresentation clientRepresentation) {
        if ( clientRepresentation == null ) {
            return null;
        }

        ClientVOBuilder clientVO = ClientVO.builder();

        if ( clientRepresentation.getId() != null ) {
            clientVO.id( clientRepresentation.getId() );
        }
        if ( clientRepresentation.getClientId() != null ) {
            clientVO.clientId( clientRepresentation.getClientId() );
        }
        if ( clientRepresentation.getName() != null ) {
            clientVO.name( clientRepresentation.getName() );
        }
        if ( clientRepresentation.getDescription() != null ) {
            clientVO.description( clientRepresentation.getDescription() );
        }
        if ( clientRepresentation.isEnabled() != null ) {
            clientVO.enabled( clientRepresentation.isEnabled() );
        }
        if ( clientRepresentation.getBaseUrl() != null ) {
            clientVO.baseUrl( clientRepresentation.getBaseUrl() );
        }

        return clientVO.build();
    }

    @Override
    public List<ClientVO> userClient(List<ClientRepresentation> clientRepresentation) {
        if ( clientRepresentation == null ) {
            return null;
        }

        List<ClientVO> list = new ArrayList<ClientVO>( clientRepresentation.size() );
        for ( ClientRepresentation clientRepresentation1 : clientRepresentation ) {
            list.add( userClient( clientRepresentation1 ) );
        }

        return list;
    }
}
