package com.qk.dam.authority.common.mapstruct;

import com.qk.dam.authority.common.vo.user.AtyUserInfoVO;
import com.qk.dam.authority.common.vo.user.AtyUserInfoVO.AtyUserInfoVOBuilder;
import com.qk.dam.authority.common.vo.user.AtyUserInputExceVO;
import com.qk.dam.authority.common.vo.user.AtyUserKeyCloakVO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.FederatedIdentityRepresentation;
import org.keycloak.representations.idm.SocialLinkRepresentation;
import org.keycloak.representations.idm.UserConsentRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-14T15:32:22+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class AtyUserMapperImpl implements AtyUserMapper {

    @Override
    public AtyUserInfoVO userInfo(UserRepresentation userRepresentation) {
        if ( userRepresentation == null ) {
            return null;
        }

        AtyUserInfoVOBuilder atyUserInfoVO = AtyUserInfoVO.builder();

        if ( userRepresentation.getId() != null ) {
            atyUserInfoVO.id( userRepresentation.getId() );
        }
        if ( userRepresentation.getUsername() != null ) {
            atyUserInfoVO.username( userRepresentation.getUsername() );
        }
        if ( userRepresentation.isEnabled() != null ) {
            atyUserInfoVO.enabled( userRepresentation.isEnabled() );
        }
        if ( userRepresentation.getFirstName() != null ) {
            atyUserInfoVO.firstName( userRepresentation.getFirstName() );
        }
        if ( userRepresentation.getLastName() != null ) {
            atyUserInfoVO.lastName( userRepresentation.getLastName() );
        }
        if ( userRepresentation.getEmail() != null ) {
            atyUserInfoVO.email( userRepresentation.getEmail() );
        }
        Map<String, List<String>> map = userRepresentation.getAttributes();
        if ( map != null ) {
            atyUserInfoVO.attributes( new HashMap<String, List<String>>( map ) );
        }

        atyUserInfoVO.createdTimestamp( com.qk.dam.authority.common.mapstruct.AtyUserMapper.parseLong(userRepresentation.getCreatedTimestamp()) );

        return atyUserInfoVO.build();
    }

    @Override
    public List<AtyUserInfoVO> userInfo(List<UserRepresentation> userRepresentation) {
        if ( userRepresentation == null ) {
            return null;
        }

        List<AtyUserInfoVO> list = new ArrayList<AtyUserInfoVO>( userRepresentation.size() );
        for ( UserRepresentation userRepresentation1 : userRepresentation ) {
            list.add( userInfo( userRepresentation1 ) );
        }

        return list;
    }

    @Override
    public UserRepresentation userInfo(AtyUserKeyCloakVO userVO) {
        if ( userVO == null ) {
            return null;
        }

        UserRepresentation userRepresentation = new UserRepresentation();

        if ( userVO.getId() != null ) {
            userRepresentation.setId( userVO.getId() );
        }
        if ( userVO.getFirstName() != null ) {
            userRepresentation.setFirstName( userVO.getFirstName() );
        }
        if ( userVO.getLastName() != null ) {
            userRepresentation.setLastName( userVO.getLastName() );
        }
        if ( userVO.getEmail() != null ) {
            userRepresentation.setEmail( userVO.getEmail() );
        }
        if ( userVO.getUsername() != null ) {
            userRepresentation.setUsername( userVO.getUsername() );
        }
        if ( userVO.getEnabled() != null ) {
            userRepresentation.setEnabled( userVO.getEnabled() );
        }

        return userRepresentation;
    }

    @Override
    public void userUpdate(AtyUserKeyCloakVO userVO, UserRepresentation userRepresentation) {
        if ( userVO == null ) {
            return;
        }

        if ( userVO.getId() != null ) {
            userRepresentation.setId( userVO.getId() );
        }
        if ( userVO.getFirstName() != null ) {
            userRepresentation.setFirstName( userVO.getFirstName() );
        }
        if ( userVO.getLastName() != null ) {
            userRepresentation.setLastName( userVO.getLastName() );
        }
        if ( userVO.getEmail() != null ) {
            userRepresentation.setEmail( userVO.getEmail() );
        }
        if ( userVO.getUsername() != null ) {
            userRepresentation.setUsername( userVO.getUsername() );
        }
        if ( userVO.getEnabled() != null ) {
            userRepresentation.setEnabled( userVO.getEnabled() );
        }
    }

    @Override
    public void userUpdate(UserRepresentation userVO, UserRepresentation userRepresentation) {
        if ( userVO == null ) {
            return;
        }

        if ( userVO.getSelf() != null ) {
            userRepresentation.setSelf( userVO.getSelf() );
        }
        if ( userVO.getId() != null ) {
            userRepresentation.setId( userVO.getId() );
        }
        if ( userVO.getCreatedTimestamp() != null ) {
            userRepresentation.setCreatedTimestamp( userVO.getCreatedTimestamp() );
        }
        if ( userVO.getFirstName() != null ) {
            userRepresentation.setFirstName( userVO.getFirstName() );
        }
        if ( userVO.getLastName() != null ) {
            userRepresentation.setLastName( userVO.getLastName() );
        }
        if ( userVO.getEmail() != null ) {
            userRepresentation.setEmail( userVO.getEmail() );
        }
        if ( userVO.getUsername() != null ) {
            userRepresentation.setUsername( userVO.getUsername() );
        }
        if ( userVO.isEnabled() != null ) {
            userRepresentation.setEnabled( userVO.isEnabled() );
        }
        if ( userVO.isTotp() != null ) {
            userRepresentation.setTotp( userVO.isTotp() );
        }
        if ( userVO.isEmailVerified() != null ) {
            userRepresentation.setEmailVerified( userVO.isEmailVerified() );
        }
        if ( userRepresentation.getAttributes() != null ) {
            Map<String, List<String>> map = userVO.getAttributes();
            if ( map != null ) {
                userRepresentation.getAttributes().clear();
                userRepresentation.getAttributes().putAll( map );
            }
        }
        else {
            Map<String, List<String>> map = userVO.getAttributes();
            if ( map != null ) {
                userRepresentation.setAttributes( new HashMap<String, List<String>>( map ) );
            }
        }
        if ( userRepresentation.getCredentials() != null ) {
            List<CredentialRepresentation> list = userVO.getCredentials();
            if ( list != null ) {
                userRepresentation.getCredentials().clear();
                userRepresentation.getCredentials().addAll( list );
            }
        }
        else {
            List<CredentialRepresentation> list = userVO.getCredentials();
            if ( list != null ) {
                userRepresentation.setCredentials( new ArrayList<CredentialRepresentation>( list ) );
            }
        }
        if ( userRepresentation.getRequiredActions() != null ) {
            List<String> list1 = userVO.getRequiredActions();
            if ( list1 != null ) {
                userRepresentation.getRequiredActions().clear();
                userRepresentation.getRequiredActions().addAll( list1 );
            }
        }
        else {
            List<String> list1 = userVO.getRequiredActions();
            if ( list1 != null ) {
                userRepresentation.setRequiredActions( new ArrayList<String>( list1 ) );
            }
        }
        if ( userRepresentation.getFederatedIdentities() != null ) {
            List<FederatedIdentityRepresentation> list2 = userVO.getFederatedIdentities();
            if ( list2 != null ) {
                userRepresentation.getFederatedIdentities().clear();
                userRepresentation.getFederatedIdentities().addAll( list2 );
            }
        }
        else {
            List<FederatedIdentityRepresentation> list2 = userVO.getFederatedIdentities();
            if ( list2 != null ) {
                userRepresentation.setFederatedIdentities( new ArrayList<FederatedIdentityRepresentation>( list2 ) );
            }
        }
        if ( userRepresentation.getSocialLinks() != null ) {
            List<SocialLinkRepresentation> list3 = userVO.getSocialLinks();
            if ( list3 != null ) {
                userRepresentation.getSocialLinks().clear();
                userRepresentation.getSocialLinks().addAll( list3 );
            }
        }
        else {
            List<SocialLinkRepresentation> list3 = userVO.getSocialLinks();
            if ( list3 != null ) {
                userRepresentation.setSocialLinks( new ArrayList<SocialLinkRepresentation>( list3 ) );
            }
        }
        if ( userRepresentation.getRealmRoles() != null ) {
            List<String> list4 = userVO.getRealmRoles();
            if ( list4 != null ) {
                userRepresentation.getRealmRoles().clear();
                userRepresentation.getRealmRoles().addAll( list4 );
            }
        }
        else {
            List<String> list4 = userVO.getRealmRoles();
            if ( list4 != null ) {
                userRepresentation.setRealmRoles( new ArrayList<String>( list4 ) );
            }
        }
        if ( userRepresentation.getClientRoles() != null ) {
            Map<String, List<String>> map1 = userVO.getClientRoles();
            if ( map1 != null ) {
                userRepresentation.getClientRoles().clear();
                userRepresentation.getClientRoles().putAll( map1 );
            }
        }
        else {
            Map<String, List<String>> map1 = userVO.getClientRoles();
            if ( map1 != null ) {
                userRepresentation.setClientRoles( new HashMap<String, List<String>>( map1 ) );
            }
        }
        if ( userRepresentation.getClientConsents() != null ) {
            List<UserConsentRepresentation> list5 = userVO.getClientConsents();
            if ( list5 != null ) {
                userRepresentation.getClientConsents().clear();
                userRepresentation.getClientConsents().addAll( list5 );
            }
        }
        else {
            List<UserConsentRepresentation> list5 = userVO.getClientConsents();
            if ( list5 != null ) {
                userRepresentation.setClientConsents( new ArrayList<UserConsentRepresentation>( list5 ) );
            }
        }
        if ( userVO.getNotBefore() != null ) {
            userRepresentation.setNotBefore( userVO.getNotBefore() );
        }
        if ( userVO.getFederationLink() != null ) {
            userRepresentation.setFederationLink( userVO.getFederationLink() );
        }
        if ( userVO.getServiceAccountClientId() != null ) {
            userRepresentation.setServiceAccountClientId( userVO.getServiceAccountClientId() );
        }
        if ( userRepresentation.getGroups() != null ) {
            List<String> list6 = userVO.getGroups();
            if ( list6 != null ) {
                userRepresentation.getGroups().clear();
                userRepresentation.getGroups().addAll( list6 );
            }
        }
        else {
            List<String> list6 = userVO.getGroups();
            if ( list6 != null ) {
                userRepresentation.setGroups( new ArrayList<String>( list6 ) );
            }
        }
        if ( userVO.getOrigin() != null ) {
            userRepresentation.setOrigin( userVO.getOrigin() );
        }
        if ( userRepresentation.getDisableableCredentialTypes() != null ) {
            Set<String> set = userVO.getDisableableCredentialTypes();
            if ( set != null ) {
                userRepresentation.getDisableableCredentialTypes().clear();
                userRepresentation.getDisableableCredentialTypes().addAll( set );
            }
        }
        else {
            Set<String> set = userVO.getDisableableCredentialTypes();
            if ( set != null ) {
                userRepresentation.setDisableableCredentialTypes( new HashSet<String>( set ) );
            }
        }
        if ( userRepresentation.getAccess() != null ) {
            Map<String, Boolean> map2 = userVO.getAccess();
            if ( map2 != null ) {
                userRepresentation.getAccess().clear();
                userRepresentation.getAccess().putAll( map2 );
            }
        }
        else {
            Map<String, Boolean> map2 = userVO.getAccess();
            if ( map2 != null ) {
                userRepresentation.setAccess( new HashMap<String, Boolean>( map2 ) );
            }
        }
        if ( userRepresentation.getApplicationRoles() != null ) {
            userRepresentation.getApplicationRoles().clear();
            Map<String, List<String>> map3 = userVO.getApplicationRoles();
            if ( map3 != null ) {
                userRepresentation.getApplicationRoles().putAll( map3 );
            }
        }
    }

    @Override
    public UserRepresentation userExcelInfo(AtyUserInputExceVO atyUserInputExceVO) {
        if ( atyUserInputExceVO == null ) {
            return null;
        }

        UserRepresentation userRepresentation = new UserRepresentation();

        if ( atyUserInputExceVO.getFirstName() != null ) {
            userRepresentation.setFirstName( atyUserInputExceVO.getFirstName() );
        }
        if ( atyUserInputExceVO.getLastName() != null ) {
            userRepresentation.setLastName( atyUserInputExceVO.getLastName() );
        }
        if ( atyUserInputExceVO.getEmail() != null ) {
            userRepresentation.setEmail( atyUserInputExceVO.getEmail() );
        }
        if ( atyUserInputExceVO.getUsername() != null ) {
            userRepresentation.setUsername( atyUserInputExceVO.getUsername() );
        }
        if ( atyUserInputExceVO.getEnabled() != null ) {
            userRepresentation.setEnabled( atyUserInputExceVO.getEnabled() );
        }

        return userRepresentation;
    }
}
