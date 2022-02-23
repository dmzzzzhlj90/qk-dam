package com.qk.dm.authority.keycloak;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.authority.mapstruct.KeyCloakMapper;
import com.qk.dm.authority.vo.*;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.ws.rs.core.Response;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author shenpj
 * @date 2022/2/22 11:13
 * @since 1.0.0
 */
@Component
public class KeyCloakApi {
    private static String TARGET_REALM = "demoRealm";

    @Autowired
    private Keycloak keycloak;


    private static Keycloak getKeycloak() {
        Keycloak keycloak = Keycloak.getInstance("http://172.20.0.9:8080/auth/", "master", "admin", "zhudao123", "admin-cli");
        System.out.println(keycloak.tokenManager().getAccessTokenString());
        return keycloak;
    }

    /**
     * 获取用户列表
     *
     * @return
     */
    public List<UserInfoVO> getUserList() {
        UsersResource userResource = keycloak.realm(TARGET_REALM).users();
        List<UserRepresentation> userList = userResource.search("user", 0, 10);
        return userList.stream().map(user -> {
            UserInfoVO userInfoVO = KeyCloakMapper.INSTANCE.userInfo(user);
            userInfoVO.setRoleList(userRole(user.getId()));
            //组
            userInfoVO.setGroupList(userGroup(user.getId()));
            return userInfoVO;
        }).collect(Collectors.toList());
    }

    /**
     * 创建用户
     */
    public void createUser(UserVO userVO) {
        userVO = UserVO.builder()
                .username("zhangsan")
                .enabled(true)
                .firstName("名称")
                .lastName("姓")
                .email("email")
                .password("123456")
                .build();
        UserRepresentation user = KeyCloakMapper.INSTANCE.userRep(userVO);
        user.setEmailVerified(false);
        // 设置密码
        List<CredentialRepresentation> credentials = new ArrayList<>();
        CredentialRepresentation cr = new CredentialRepresentation();
        cr.setType(CredentialRepresentation.PASSWORD);
        cr.setValue(userVO.getPassword());
        //临时密码，如果启用，用户需在下次登陆时更换密码
        cr.setTemporary(false);
        credentials.add(cr);
        user.setCredentials(credentials);
        //创建
        Response response = keycloak.realm(TARGET_REALM).users().create(user);
        //判断创建用户状态；如果时创建成功
        Response.StatusType createUserStatus = response.getStatusInfo();
        System.out.println(createUserStatus);
        if (!"Created".equals(createUserStatus.toString())) {
            throw new BizException("账号已经存在！");
        }
    }

    /**
     * 修改用户信息
     *
     * @param userVO
     */
    public void updateUserInfo(UserVO userVO) {
        UsersResource userResource = keycloak.realm(TARGET_REALM).users();
        UserResource user = userResource.get(userVO.getId());
        UserRepresentation userRepresentation = user.toRepresentation();
        KeyCloakMapper.INSTANCE.userUpdate(userVO,userRepresentation);
        user.update(userRepresentation);
    }

    /**
     * 重置密码
     *
     * @param userVO
     */
    public void resetUserPassword(UserVO userVO) {
        UsersResource userResource = keycloak.realm(TARGET_REALM).users();
        UserResource user = userResource.get(userVO.getId());
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(userVO.getPassword());
        user.resetPassword(passwordCred);
    }

    /**
     * 更改属性
     *
     * @param userVO
     */
    public void resetUserAttributes(UserVO userVO) {
        UsersResource userResource = keycloak.realm(TARGET_REALM).users();
        UserResource user = userResource.get(userVO.getId());
        UserRepresentation userRepresentation = user.toRepresentation();
        //属性
        Map<String, List<String>> attributeMap = userRepresentation.getAttributes();
        attributeMap.put("test", Arrays.asList("123"));
        userRepresentation.setAttributes(attributeMap);
        user.update(userRepresentation);
    }

    /**
     * 查询所有分组
     */
    public List<GroupVO> groupList() {
        GroupsResource groupsResource = keycloak.realm(TARGET_REALM).groups();
        List<GroupRepresentation> groups = groupsResource.groups();
        return groups.stream().map(group -> {
            GroupVO groupVO = KeyCloakMapper.INSTANCE.userGroup(group);
            List<UserRepresentation> members =groupsResource.group(group.getId()).members();
            List<UserInfoVO> userInfos = KeyCloakMapper.INSTANCE.userInfoList(members);
            groupVO.setMembers(userInfos);
            return groupVO;
        }).collect(Collectors.toList());
    }

    /**
     * 用户分组
     *
     * @param userId
     */
    public List<GroupVO> userGroup(String userId) {
        UsersResource userResource = keycloak.realm(TARGET_REALM).users();
        List<GroupRepresentation> groups = userResource.get(userId).groups();
        return groups.stream().map(KeyCloakMapper.INSTANCE::userGroup).collect(Collectors.toList());
    }

    /**
     * 所有角色
     *
     * @return
     */
    public List<RoleVO> roleList() {
        RolesResource roles = keycloak.realm(TARGET_REALM).roles();
        List<RoleRepresentation> representationList = roles.list("", 0, 10);
        return representationList.stream().map(representation -> {
            RoleVO roleVO = KeyCloakMapper.INSTANCE.userRole(representation);
            RoleResource roleResource = roles.get(representation.getName());
            Set<UserRepresentation> roleUserMembers = roleResource.getRoleUserMembers();
            List<UserInfoVO> userInfoVOS = KeyCloakMapper.INSTANCE.userInfoList(new ArrayList<>(roleUserMembers));
            roleVO.setMembers(userInfoVOS);
            return roleVO;
        }).collect(Collectors.toList());
    }

    /**
     * 用户角色
     *
     * @param userId
     */
    public List<RoleVO> userRole(String userId) {
        UsersResource userResource = keycloak.realm(TARGET_REALM).users();
        RoleMappingResource roles = userResource.get(userId).roles();
        MappingsRepresentation mappingsRepresentation = roles.getAll();
        List<RoleRepresentation> realmMappings = mappingsRepresentation.getRealmMappings();
        return realmMappings.stream().map(KeyCloakMapper.INSTANCE::userRole).collect(Collectors.toList());
    }

    /**
     * 获取某用户的自定义属性值
     *
     * @param userId
     * @return
     */
    public Map<String, List<String>> getUserAttribute(String userId) {
        UsersResource userResource = keycloak.realm(TARGET_REALM).users();
        UserRepresentation user = userResource.get(userId).toRepresentation();
        Map<String, List<String>> userAttributes = user.getAttributes();
        return userAttributes;
    }
}
