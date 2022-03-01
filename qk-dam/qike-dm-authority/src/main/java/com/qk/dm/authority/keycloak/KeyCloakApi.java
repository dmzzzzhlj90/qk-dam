package com.qk.dm.authority.keycloak;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dam.jpa.pojo.Pagination;
import com.qk.dm.authority.mapstruct.KeyCloakMapper;
import com.qk.dm.authority.util.Util;
import com.qk.dm.authority.vo.*;
import com.qk.dm.authority.vo.params.UserParamVO;
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
    private static String id = "6fdfce20-1f50-43a2-bbbe-62337e35c3d9";
    private static String client_id = "demoClient";

    @Autowired
    private Keycloak keycloak;


    private static Keycloak getKeycloak() {
        Keycloak keycloak = Keycloak.getInstance("http://172.20.0.9:8080/auth/", "master", "admin", "zhudao123", "admin-cli");
        System.out.println(keycloak.tokenManager().getAccessTokenString());
        return keycloak;
    }

    public static void main(String[] args) {
        Keycloak keycloak = getKeycloak();
        RealmsResource realms = keycloak.realms();
        List<RealmRepresentation> all = realms.findAll();
        List<RealmVO> realmVOS = KeyCloakMapper.INSTANCE.userRealm(all);
        System.out.println(realmVOS);

    }

    /**
     * 所有域
     *
     * @return
     */
    public List<RealmVO> realmList() {
        RealmsResource realms = keycloak.realms();
        List<RealmRepresentation> list = realms.findAll();
        return KeyCloakMapper.INSTANCE.userRealm(list);
    }

    /**
     * 域下客户端列表
     *
     * @param realm
     * @param clientId
     * @return
     */
    public List<ClientVO> clientListByRealm(String realm, String clientId) {
        ClientsResource clients = keycloak.realm(realm).clients();
        List<ClientRepresentation> list = clients.findAll(clientId, true, true, null, null);
        return KeyCloakMapper.INSTANCE.userClient(list);
    }


    /**
     * 创建用户
     *
     * @param realm
     * @param userVO
     */
    public void createUser(String realm, UserVO userVO) {
        //todo 用户名电子邮箱不能重复
        UserRepresentation user = KeyCloakMapper.INSTANCE.userInfo(userVO);
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
        //创建用户
        Response response = keycloak.realm(realm).users().create(user);
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
     * @param realm
     * @param userVO
     */
    public void updateUser(String realm, UserVO userVO) {
        UsersResource userResource = keycloak.realm(realm).users();
        UserResource user = userResource.get(userVO.getId());
        UserRepresentation userRepresentation = user.toRepresentation();
        KeyCloakMapper.INSTANCE.userUpdate(userVO, userRepresentation);
        user.update(userRepresentation);
    }

    /**
     * 删除用户
     *
     * @param realm
     * @param userId
     */
    public void deleteUser(String realm, String userId) {
        keycloak.realm(realm).users().delete(userId);
    }


    /**
     * 重置密码
     *
     * @param realm
     * @param userId
     * @param password
     */
    public void resetUserPassword(String realm, String userId, String password) {
        UsersResource userResource = keycloak.realm(realm).users();
        UserResource user = userResource.get(userId);
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(password);
        user.resetPassword(passwordCred);
    }

    /**
     * 更改属性
     *
     * @param realm
     * @param id
     */
    public void resetUserAttributes(String realm, String id, Map<String, List<String>> map) {
        UsersResource userResource = keycloak.realm(realm).users();
        UserResource user = userResource.get(id);
        UserRepresentation userRepresentation = user.toRepresentation();
        //属性
        Map<String, List<String>> attribute = userRepresentation.getAttributes();
        Map<String, List<String>> attributeMap = new HashMap<>();
        if (attribute != null) {
            attributeMap.putAll(attribute);
        }
        attributeMap.putAll(map);
//        attributeMap.put("test", Arrays.asList("123"));
        userRepresentation.setAttributes(attributeMap);
        user.update(userRepresentation);
    }

    /**
     * 用户列表
     *
     * @param realm
     * @param search
     * @param pagination
     * @return
     */
    public PageResultVO<UserInfoVO> getUserList(String realm, String search, Pagination pagination) {
        UsersResource userResource = keycloak.realm(realm).users();
        List<UserRepresentation> userList = userResource.search(
                search,
                (pagination.getPage() - 1) * pagination.getSize(),
                pagination.getSize());
        return new PageResultVO<>(
                (long) userResource.count(search),
                pagination.getPage(),
                pagination.getSize(),
                KeyCloakMapper.INSTANCE.userInfo(userList));
    }

    /**
     * 用户详情
     *
     * @param realm
     * @param userId
     * @return
     */
    public UserInfoVO userDetail(String realm, String userId) {
        UserResource resource = keycloak.realm(realm).users().get(userId);
        UserRepresentation user = resource.toRepresentation();
        UserInfoVO userInfoVO = KeyCloakMapper.INSTANCE.userInfo(user);
        //角色
        userInfoVO.setClientRoleList(getUserClientRole(resource));
        //组
        userInfoVO.setGroupList(getGroupList(resource));
        return userInfoVO;
    }


    /**********************************分组开始************************************/

    /**
     * 创建分组
     *
     * @param realm
     * @param groupName
     */
    public void addGroup(String realm, String groupName) {
        GroupsResource groupsResource = keycloak.realm(realm).groups();
        GroupRepresentation groupRepresentation = new GroupRepresentation();
        groupRepresentation.setName(groupName);
        groupsResource.add(groupRepresentation);
    }

    /**
     * 修改分组
     *
     * @param realm
     * @param groupId
     * @param groupName
     */
    public void updateGroup(String realm, String groupId, String groupName) {
        GroupResource group = keycloak.realm(realm).groups().group(groupId);
        GroupRepresentation groupRepresentation = group.toRepresentation();
        groupRepresentation.setName(groupName);
        group.update(groupRepresentation);
    }

    /**
     * 删除分组
     *
     * @param realm
     * @param groupId
     */
    public void deleteGroup(String realm, String groupId) {
        keycloak.realm(realm).groups().group(groupId).remove();
    }

    /**
     * 分组详情
     *
     * @param groupId
     * @return
     */
    public GroupVO groupDetail(String realm, String groupId) {
        GroupResource groupResource = keycloak.realm(realm).groups().group(groupId);
        GroupVO groupVO = KeyCloakMapper.INSTANCE.userGroup(groupResource.toRepresentation());
        List<UserRepresentation> members = groupResource.members();
        List<UserInfoVO> userInfos = KeyCloakMapper.INSTANCE.userInfo(members);
        groupVO.setMembers(userInfos);
        return groupVO;
    }

    /**
     * 查询所有分组
     */
    public PageResultVO<GroupVO> groupList(String realm, String search, Pagination pagination) {
        GroupsResource groupsResource = keycloak.realm(realm).groups();
        List<GroupRepresentation> groups = groupsResource.groups(
                search,
                (pagination.getPage() - 1) * pagination.getSize(),
                pagination.getSize());
        return new PageResultVO<>(
                groupsResource.groups(search, null, null).size(),
                pagination.getPage(),
                pagination.getSize(),
                KeyCloakMapper.INSTANCE.userGroup(groups));
    }


    /**
     * 用户分组列表
     *
     * @param userId
     */
    public List<GroupVO> userGroup(String realm, String userId) {
        return getGroupList(keycloak.realm(realm).users().get(userId));
    }

    private List<GroupVO> getGroupList(UserResource resource) {
        return resource.groups().stream().map(KeyCloakMapper.INSTANCE::userGroup).collect(Collectors.toList());
    }


    /**
     * 用户添加分组
     */
    public void addUserGroup(String realm, String userId, String groupId) {
        UserResource resource = keycloak.realm(realm).users().get(userId);
        resource.joinGroup(groupId);
    }

    /**
     * 用户离开分组
     */
    public void deleteUserGroup(String realm, String userId, String groupId) {
        UserResource resource = keycloak.realm(realm).users().get(userId);
        resource.leaveGroup(groupId);
    }

    /**********************************分组结束************************************/


    /**********************************角色开始************************************/

    /**
     * 新增客户端角色
     *
     * @param realm
     * @param clentId
     * @param roleName
     * @param description
     */
    public void addClientRole(String realm, String clentId, String roleName, String description) {
        RolesResource roles = keycloak.realm(realm).clients().get(clentId).roles();
        RoleRepresentation roleRepresentation = new RoleRepresentation();
        roleRepresentation.setName(roleName);
        roleRepresentation.setDescription(description);
        roles.create(roleRepresentation);
    }

    /**
     * 修改客户端角色
     *
     * @param realm
     * @param clentId
     * @param roleName
     * @param description
     */
    public void updateClientRole(String realm, String clentId, String roleName, String description) {
        RoleResource roleResource = keycloak.realm(realm).clients().get(clentId).roles().get(roleName);
        RoleRepresentation roleRepresentation = roleResource.toRepresentation();
        roleRepresentation.setDescription(description);
        roleResource.update(roleRepresentation);
    }

    /**
     * 删除客户端角色
     *
     * @param realm
     * @param clentId
     * @param roleName
     */
    public void deleteClientRole(String realm, String clentId, String roleName) {
        keycloak.realm(realm).clients().get(clentId).roles().get(roleName).remove();
    }

    /**
     * 客户端角色详情
     *
     * @param roleName
     * @return
     */
    public RoleVO clientRoleDetail(String realm, String clentId, String roleName) {
        RoleResource roleResource = keycloak.realm(realm).clients().get(clentId).roles().get(roleName);
        RoleVO roleVO = KeyCloakMapper.INSTANCE.userRole(roleResource.toRepresentation());
        Set<UserRepresentation> roleUserMembers = roleResource.getRoleUserMembers();
        List<UserInfoVO> userInfoVOS = KeyCloakMapper.INSTANCE.userInfo(new ArrayList<>(roleUserMembers));
        roleVO.setMembers(userInfoVOS);
        return roleVO;
    }

    /**
     * 客户端所有角色
     *
     * @return
     */
    public PageResultVO<RoleVO> clientRoleList(String realm, String clentId, String search, Pagination pagination) {
        RolesResource roles = keycloak.realm(realm).clients().get(clentId).roles();
        //todo 分页
        List<RoleRepresentation> list = roles.list(
                search,
                (pagination.getPage() - 1) * pagination.getSize(),
                pagination.getSize());
        return new PageResultVO<>(
                roles.list(search, null, null).size(),
                pagination.getPage(),
                pagination.getSize(),
                KeyCloakMapper.INSTANCE.userRole(list));
    }


    /**
     * 用户客户端角色
     *
     * @param userId
     */
    public List<RoleVO> userClientRole(String realm, String userId) {
        return getUserClientRole(keycloak.realm(realm).users().get(userId));
    }

    private List<RoleVO> getUserClientRole(UserResource resource) {
        RoleMappingResource roles = resource.roles();
        MappingsRepresentation mappingsRepresentation = roles.getAll();
        List<RoleVO> roleVOS = null;
        Map<String, ClientMappingsRepresentation> clientMappings = mappingsRepresentation.getClientMappings();
        if (clientMappings != null && clientMappings.get(client_id) != null) {
            ClientMappingsRepresentation clientMappingsRepresentation = clientMappings.get(client_id);
            List<RoleRepresentation> mappings = clientMappingsRepresentation.getMappings();
            roleVOS = KeyCloakMapper.INSTANCE.userRole(mappings);
        }
        return roleVOS;
    }

    /**
     * 用户添加客户端角色
     *
     * @param realm
     * @param clentId
     * @param userId
     * @param name
     */
    public void addUserClientRole(String realm, String clentId, String userId, String name) {
        RealmResource realmResource = keycloak.realm(realm);
        RoleRepresentation clientRoleRep = realmResource.clients().get(clentId).roles().get(name).toRepresentation();
        realmResource.users().get(userId).roles().clientLevel(clentId).add(Arrays.asList(clientRoleRep));
    }

    /**
     * 用户删除客户端角色
     *
     * @param realm
     * @param clentId
     * @param userId
     * @param name
     */
    public void deleteUserClientRole(String realm, String clentId, String userId, String name) {
        RealmResource realmResource = keycloak.realm(realm);
        RoleRepresentation clientRoleRep = realmResource.clients().get(clentId).roles().get(name).toRepresentation();
        realmResource.users().get(userId).roles().clientLevel(clentId).remove(Arrays.asList(clientRoleRep));
    }


    public PageResultVO<UserInfoVO> getUserList(UserParamVO userParamVO) {
        UsersResource userResource = keycloak.realm(TARGET_REALM).users();
        Integer count = keycloak.realm(TARGET_REALM).users().count();
        List<UserRepresentation> userList = userResource.search(userParamVO.getSearch(),
                Util.dealPage(userParamVO), userParamVO.getPagination().getSize());
        List<UserInfoVO> collect = userList.stream().map(user -> {
            UserInfoVO userInfoVO = KeyCloakMapper.INSTANCE.userInfo(user);
            userInfoVO.setClientRoleList(userClientRole(TARGET_REALM,user.getId()));
            //组
            userInfoVO.setGroupList(userGroup(TARGET_REALM,user.getId()));
            return userInfoVO;
        }).collect(Collectors.toList());
        return new PageResultVO<>(
                (long) count,
                userParamVO.getPagination().getPage(),
                userParamVO.getPagination().getSize(),
                collect);
    }
}
