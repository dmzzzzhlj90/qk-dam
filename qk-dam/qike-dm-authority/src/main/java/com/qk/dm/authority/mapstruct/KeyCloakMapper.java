package com.qk.dm.authority.mapstruct;

import com.qk.dm.authority.vo.GroupVO;
import com.qk.dm.authority.vo.RoleVO;
import com.qk.dm.authority.vo.UserInfoVO;
import com.qk.dm.authority.vo.UserVO;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
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
public interface KeyCloakMapper {
    KeyCloakMapper INSTANCE = Mappers.getMapper(KeyCloakMapper.class);

    RoleVO userRole(RoleRepresentation roleRepresentation);

    GroupVO userGroup(GroupRepresentation groupRepresentation);

    List<UserInfoVO> userInfoList(List<UserRepresentation> userRepresentation);

    void userUpdate(UserVO dqcRuleTemplateVo, @MappingTarget UserRepresentation dqcRuleTemplate);

    UserRepresentation userRep(UserVO userVO);

    UserInfoVO userInfo(UserRepresentation userRepresentation);
}
