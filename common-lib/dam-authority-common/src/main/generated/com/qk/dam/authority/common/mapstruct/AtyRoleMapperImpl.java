package com.qk.dam.authority.common.mapstruct;

import com.qk.dam.authority.common.vo.clientrole.AtyClientRoleInfoVO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.keycloak.representations.idm.RoleRepresentation;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-14T15:31:12+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class AtyRoleMapperImpl implements AtyRoleMapper {

    @Override
    public AtyClientRoleInfoVO userRole(RoleRepresentation roleRepresentation) {
        if ( roleRepresentation == null ) {
            return null;
        }

        AtyClientRoleInfoVO atyClientRoleInfoVO = new AtyClientRoleInfoVO();

        if ( roleRepresentation.getId() != null ) {
            atyClientRoleInfoVO.setId( roleRepresentation.getId() );
        }
        if ( roleRepresentation.getName() != null ) {
            atyClientRoleInfoVO.setName( roleRepresentation.getName() );
        }
        if ( roleRepresentation.getDescription() != null ) {
            atyClientRoleInfoVO.setDescription( roleRepresentation.getDescription() );
        }
        Map<String, List<String>> map = roleRepresentation.getAttributes();
        if ( map != null ) {
            atyClientRoleInfoVO.setAttributes( new HashMap<String, List<String>>( map ) );
        }

        return atyClientRoleInfoVO;
    }

    @Override
    public List<AtyClientRoleInfoVO> userRole(List<RoleRepresentation> roleRepresentation) {
        if ( roleRepresentation == null ) {
            return null;
        }

        List<AtyClientRoleInfoVO> list = new ArrayList<AtyClientRoleInfoVO>( roleRepresentation.size() );
        for ( RoleRepresentation roleRepresentation1 : roleRepresentation ) {
            list.add( userRole( roleRepresentation1 ) );
        }

        return list;
    }
}
