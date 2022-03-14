package com.qk.dam.authority.common.mapstruct;

import com.qk.dam.authority.common.vo.clientrole.AtyClientRoleInfoVO;
import com.qk.dam.authority.common.vo.clientrole.AtyClientRoleInfoVO.AtyClientRoleInfoVOBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.keycloak.representations.idm.RoleRepresentation;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-14T16:02:55+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class AtyRoleMapperImpl implements AtyRoleMapper {

    @Override
    public AtyClientRoleInfoVO userRole(RoleRepresentation roleRepresentation) {
        if ( roleRepresentation == null ) {
            return null;
        }

        AtyClientRoleInfoVOBuilder atyClientRoleInfoVO = AtyClientRoleInfoVO.builder();

        if ( roleRepresentation.getId() != null ) {
            atyClientRoleInfoVO.id( roleRepresentation.getId() );
        }
        if ( roleRepresentation.getName() != null ) {
            atyClientRoleInfoVO.name( roleRepresentation.getName() );
        }
        if ( roleRepresentation.getDescription() != null ) {
            atyClientRoleInfoVO.description( roleRepresentation.getDescription() );
        }
        Map<String, List<String>> map = roleRepresentation.getAttributes();
        if ( map != null ) {
            atyClientRoleInfoVO.attributes( new HashMap<String, List<String>>( map ) );
        }

        return atyClientRoleInfoVO.build();
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
