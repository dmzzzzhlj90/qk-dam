package com.qk.dam.authority.common.mapstruct;

import com.qk.dam.authority.common.vo.group.AtyGroupInfoVO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.keycloak.representations.idm.GroupRepresentation;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-14T15:31:12+0800",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class AtyGroupMapperImpl implements AtyGroupMapper {

    @Override
    public AtyGroupInfoVO userGroup(GroupRepresentation groupRepresentation) {
        if ( groupRepresentation == null ) {
            return null;
        }

        AtyGroupInfoVO atyGroupInfoVO = new AtyGroupInfoVO();

        if ( groupRepresentation.getId() != null ) {
            atyGroupInfoVO.setId( groupRepresentation.getId() );
        }
        if ( groupRepresentation.getName() != null ) {
            atyGroupInfoVO.setName( groupRepresentation.getName() );
        }
        if ( groupRepresentation.getPath() != null ) {
            atyGroupInfoVO.setPath( groupRepresentation.getPath() );
        }
        Map<String, List<String>> map = groupRepresentation.getAttributes();
        if ( map != null ) {
            atyGroupInfoVO.setAttributes( new HashMap<String, List<String>>( map ) );
        }

        return atyGroupInfoVO;
    }

    @Override
    public List<AtyGroupInfoVO> userGroup(List<GroupRepresentation> groupRepresentation) {
        if ( groupRepresentation == null ) {
            return null;
        }

        List<AtyGroupInfoVO> list = new ArrayList<AtyGroupInfoVO>( groupRepresentation.size() );
        for ( GroupRepresentation groupRepresentation1 : groupRepresentation ) {
            list.add( userGroup( groupRepresentation1 ) );
        }

        return list;
    }
}
