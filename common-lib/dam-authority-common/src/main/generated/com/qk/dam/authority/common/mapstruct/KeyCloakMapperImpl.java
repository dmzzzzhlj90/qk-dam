package com.qk.dam.authority.common.mapstruct;

import com.qk.dam.authority.common.vo.ClientVO;
import com.qk.dam.authority.common.vo.RealmVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-14T15:31:12+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class KeyCloakMapperImpl implements KeyCloakMapper {

    @Override
    public RealmVO userRealm(RealmRepresentation realmRepresentation) {
        if ( realmRepresentation == null ) {
            return null;
        }

        RealmVO realmVO = new RealmVO();

        if ( realmRepresentation.getId() != null ) {
            realmVO.setId( realmRepresentation.getId() );
        }
        if ( realmRepresentation.getRealm() != null ) {
            realmVO.setRealm( realmRepresentation.getRealm() );
        }
        if ( realmRepresentation.getDisplayName() != null ) {
            realmVO.setDisplayName( realmRepresentation.getDisplayName() );
        }
        if ( realmRepresentation.getDisplayNameHtml() != null ) {
            realmVO.setDisplayNameHtml( realmRepresentation.getDisplayNameHtml() );
        }

        return realmVO;
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

        ClientVO clientVO = new ClientVO();

        if ( clientRepresentation.getId() != null ) {
            clientVO.setId( clientRepresentation.getId() );
        }
        if ( clientRepresentation.getClientId() != null ) {
            clientVO.setClientId( clientRepresentation.getClientId() );
        }
        if ( clientRepresentation.getName() != null ) {
            clientVO.setName( clientRepresentation.getName() );
        }
        if ( clientRepresentation.getDescription() != null ) {
            clientVO.setDescription( clientRepresentation.getDescription() );
        }
        if ( clientRepresentation.isEnabled() != null ) {
            clientVO.setEnabled( clientRepresentation.isEnabled() );
        }
        if ( clientRepresentation.getBaseUrl() != null ) {
            clientVO.setBaseUrl( clientRepresentation.getBaseUrl() );
        }

        return clientVO;
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
