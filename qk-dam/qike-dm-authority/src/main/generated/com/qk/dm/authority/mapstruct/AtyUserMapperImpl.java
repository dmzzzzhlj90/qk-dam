package com.qk.dm.authority.mapstruct;

import com.qk.dam.authority.common.vo.group.AtyGroupInfoVO;
import com.qk.dam.authority.common.vo.user.AtyUserInfoVO;
import com.qk.dam.authority.common.vo.user.AtyUserKeyCloakVO;
import com.qk.dam.authority.common.vo.user.AtyUserKeyCloakVO.AtyUserKeyCloakVOBuilder;
import com.qk.dm.authority.vo.user.AtyUserCreateVO;
import com.qk.dm.authority.vo.user.AtyUserExcelVO;
import com.qk.dm.authority.vo.user.AtyUserUpdateVO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-14T15:32:35+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class AtyUserMapperImpl implements AtyUserMapper {

    @Override
    public AtyUserKeyCloakVO userInfo(AtyUserCreateVO userVO) {
        if ( userVO == null ) {
            return null;
        }

        AtyUserKeyCloakVOBuilder atyUserKeyCloakVO = AtyUserKeyCloakVO.builder();

        if ( userVO.getUsername() != null ) {
            atyUserKeyCloakVO.username( userVO.getUsername() );
        }
        if ( userVO.getEnabled() != null ) {
            atyUserKeyCloakVO.enabled( userVO.getEnabled() );
        }
        if ( userVO.getFirstName() != null ) {
            atyUserKeyCloakVO.firstName( userVO.getFirstName() );
        }
        if ( userVO.getLastName() != null ) {
            atyUserKeyCloakVO.lastName( userVO.getLastName() );
        }
        if ( userVO.getEmail() != null ) {
            atyUserKeyCloakVO.email( userVO.getEmail() );
        }
        if ( userVO.getPassword() != null ) {
            atyUserKeyCloakVO.password( userVO.getPassword() );
        }

        return atyUserKeyCloakVO.build();
    }

    @Override
    public AtyUserKeyCloakVO userInfo(AtyUserUpdateVO atyUserVO) {
        if ( atyUserVO == null ) {
            return null;
        }

        AtyUserKeyCloakVOBuilder atyUserKeyCloakVO = AtyUserKeyCloakVO.builder();

        if ( atyUserVO.getEnabled() != null ) {
            atyUserKeyCloakVO.enabled( atyUserVO.getEnabled() );
        }
        if ( atyUserVO.getFirstName() != null ) {
            atyUserKeyCloakVO.firstName( atyUserVO.getFirstName() );
        }
        if ( atyUserVO.getLastName() != null ) {
            atyUserKeyCloakVO.lastName( atyUserVO.getLastName() );
        }
        if ( atyUserVO.getEmail() != null ) {
            atyUserKeyCloakVO.email( atyUserVO.getEmail() );
        }

        return atyUserKeyCloakVO.build();
    }

    @Override
    public void userExcel(AtyUserInfoVO atyUserInfoVO, AtyUserExcelVO atyUserExcelVO) {
        if ( atyUserInfoVO == null ) {
            return;
        }

        if ( atyUserInfoVO.getId() != null ) {
            atyUserExcelVO.setId( atyUserInfoVO.getId() );
        }
        if ( atyUserInfoVO.getUsername() != null ) {
            atyUserExcelVO.setUsername( atyUserInfoVO.getUsername() );
        }
        if ( atyUserInfoVO.getEnabled() != null ) {
            atyUserExcelVO.setEnabled( atyUserInfoVO.getEnabled() );
        }
        if ( atyUserInfoVO.getFirstName() != null ) {
            atyUserExcelVO.setFirstName( atyUserInfoVO.getFirstName() );
        }
        if ( atyUserInfoVO.getLastName() != null ) {
            atyUserExcelVO.setLastName( atyUserInfoVO.getLastName() );
        }
        if ( atyUserInfoVO.getEmail() != null ) {
            atyUserExcelVO.setEmail( atyUserInfoVO.getEmail() );
        }
        if ( atyUserInfoVO.getPassword() != null ) {
            atyUserExcelVO.setPassword( atyUserInfoVO.getPassword() );
        }
        if ( atyUserExcelVO.getAttributes() != null ) {
            Map<String, List<String>> map = atyUserInfoVO.getAttributes();
            if ( map != null ) {
                atyUserExcelVO.getAttributes().clear();
                atyUserExcelVO.getAttributes().putAll( map );
            }
        }
        else {
            Map<String, List<String>> map = atyUserInfoVO.getAttributes();
            if ( map != null ) {
                atyUserExcelVO.setAttributes( new HashMap<String, List<String>>( map ) );
            }
        }
        if ( atyUserExcelVO.getGroupList() != null ) {
            List<AtyGroupInfoVO> list = atyUserInfoVO.getGroupList();
            if ( list != null ) {
                atyUserExcelVO.getGroupList().clear();
                atyUserExcelVO.getGroupList().addAll( list );
            }
        }
        else {
            List<AtyGroupInfoVO> list = atyUserInfoVO.getGroupList();
            if ( list != null ) {
                atyUserExcelVO.setGroupList( new ArrayList<AtyGroupInfoVO>( list ) );
            }
        }
    }
}
