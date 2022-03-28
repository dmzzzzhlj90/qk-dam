package com.qk.dam.authority.common.keycloak;

import com.qk.dam.authority.common.util.Pager;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.Pagination;
import org.apache.commons.collections4.CollectionUtils;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.*;
import org.springframework.stereotype.Component;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;
import java.util.*;

/**
 * @author shenpj
 * @date 2022/2/22 11:13
 * @since 1.0.0
 */
@Component
public class KeyCloakApi {


    private final Keycloak keycloak;

    public KeyCloakApi(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    /**
     * 所有域
     *
     * @return
     */
    public List<RealmRepresentation> getRealmRepresentationList() {
        return keycloak.realms().findAll();
    }

    /**
     * 域下客户端列表
     *
     * @param realm
     * @param client_clientId
     * @return
     */
    public List<ClientRepresentation> getClientRepresentationList(String realm, String client_clientId) {
        return keycloak.realm(realm).clients()
                .findAll(client_clientId, true, true, null, null);
    }


    /**
     * 创建用户
     *
     * @param realm
     * @param password
     * @param user
     */
    public void createUser(String realm, String password, UserRepresentation user) {
        user.setEmailVerified(false);
        // 设置密码
        if (password != null) {
            List<CredentialRepresentation> credentials = new ArrayList<>();
            CredentialRepresentation cr = new CredentialRepresentation();
            cr.setType(CredentialRepresentation.PASSWORD);
            cr.setValue(password);
            //临时密码，如果启用，用户需在下次登陆时更换密码
            cr.setTemporary(false);
            credentials.add(cr);
            user.setCredentials(credentials);
        }
        //创建用户
        Response response = keycloak.realm(realm).users().create(user);
        Response.StatusType createUserStatus = response.getStatusInfo();
        if (!"Created".equals(createUserStatus.toString())) {
            throw new BizException("用户名或邮箱已经存在！");
        }
    }

    /**
     * 修改用户信息
     *
     * @param realm
     * @param userId
     * @param email
     * @param firstName
     * @param lastName
     * @param enabled
     */
    public void updateUser(String realm, String userId, String email, String firstName, String lastName, Boolean enabled) {
        UsersResource userResource = keycloak.realm(realm).users();
        UserResource user = userResource.get(userId);
        UserRepresentation userRepresentation = user.toRepresentation();
        userRepresentation.setEmail(email != null ? email : userRepresentation.getEmail());
        userRepresentation.setFirstName(firstName != null ? firstName : userRepresentation.getFirstName());
        userRepresentation.setLastName(lastName != null ? lastName : userRepresentation.getLastName());
        userRepresentation.setEnabled(enabled);
        try {
            user.update(userRepresentation);
        } catch (ClientErrorException e) {
            throw new BizException("邮箱已经存在！");
        }
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
     * 用户详情
     *
     * @param realm
     * @param userId
     * @return
     */
    public UserRepresentation userDetail(String realm, String userId) {
        return keycloak.realm(realm).users().get(userId).toRepresentation();
    }

    /**
     * 用户列表
     *
     * @param realm
     * @param search
     * @param pagination
     * @return
     */
    public List<UserRepresentation> getUserList(String realm, String search, Pagination pagination) {
        List<UserRepresentation> userList = keycloak.realm(realm).users().search(search);
        return dealUser(userList,pagination);
    }

    /**
     * 将用户按照时间排序，然后分页
     * @param userList
     * @param pagination
     * @return
     */
    private List<UserRepresentation> dealUser(List<UserRepresentation> userList, Pagination pagination) {
        //根据时间排序
        if (CollectionUtils.isNotEmpty(userList)){
            userList.sort((x,y)->Long.compare(y.getCreatedTimestamp(),x.getCreatedTimestamp()));
            //做分页
            Pager<UserRepresentation> userRepresentationPager = Pager.create(userList, pagination.getSize());
            return userRepresentationPager.getPagedList(pagination.getPage());
        }
        return new ArrayList<>();
    }

    /**
     * 用户数汇总
     *
     * @param realm
     * @param search
     * @return
     */
    public Integer getUserCount(String realm, String search) {
        return keycloak.realm(realm).users().count(search);
    }

    /**
     * 用户列表-不分页
     *
     * @param realm
     * @param search
     * @return
     */
    public List<UserRepresentation> getUserList(String realm, String search) {
        return keycloak.realm(realm).users().search(search);
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


            Response response = groupsResource.add(groupRepresentation);
            Response.StatusType createUserStatus = response.getStatusInfo();
            if (!"Created".equals(createUserStatus.toString())) {
                throw new BizException("当前新增分组名称为"+groupName+"的数据已经存在,请确认后重新添加");
            }
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
    public GroupRepresentation groupDetail(String realm, String groupId) {
        return keycloak.realm(realm).groups().group(groupId).toRepresentation();
    }

    /**
     * 查询所有分组
     *
     * @param realm
     * @param search
     * @param pagination
     * @return
     */
    public List<GroupRepresentation> groupList(String realm, String search, Pagination pagination) {
        return keycloak.realm(realm).groups().groups(search, (pagination.getPage() - 1) * pagination.getSize(), pagination.getSize());
    }

    /**
     * 查询所有分组
     *
     * @param realm
     * @param search
     * @return
     */
    public List<GroupRepresentation> groupList(String realm, String search) {
        return keycloak.realm(realm).groups().groups(search, null, null);
    }

    /**
     * 分组用户列表
     *
     * @param realm
     * @param groupId
     * @param pagination
     * @return
     */
    public List<UserRepresentation> groupUsers(String realm, String groupId, Pagination pagination) {
        return keycloak.realm(realm).groups().group(groupId).members((pagination.getPage() - 1) * pagination.getSize(), pagination.getSize());
    }

    /**
     * 分组用户列表
     *
     * @param realm
     * @param groupId
     * @return
     */
    public List<UserRepresentation> groupUsers(String realm, String groupId) {
        return keycloak.realm(realm).groups().group(groupId).members();
    }

    /**
     * 用户分组列表
     *
     * @param realm
     * @param userId
     * @param pagination
     * @return
     */
    public List<GroupRepresentation> userGroup(String realm, String userId, Pagination pagination) {
        return keycloak.realm(realm).users().get(userId).groups((pagination.getPage() - 1) * pagination.getSize(), pagination.getSize()
        );
    }

    /**
     * 用户分组列表
     *
     * @param realm
     * @param userId
     * @return
     */
    public List<GroupRepresentation> userGroup(String realm, String userId) {
        return keycloak.realm(realm).users().get(userId).groups();
    }

    /**
     * 用户添加分组
     *
     * @param realm
     * @param userId
     * @param groupId
     */
    public void addUserGroup(String realm, String userId, String groupId) {
        keycloak.realm(realm).users().get(userId).joinGroup(groupId);
    }

    /**
     * 用户离开分组
     *
     * @param realm
     * @param userId
     * @param groupId
     */
    public void deleteUserGroup(String realm, String userId, String groupId) {
        keycloak.realm(realm).users().get(userId).leaveGroup(groupId);
    }

    /**********************************角色开始************************************/

    /**
     * 新增客户端角色
     *
     * @param realm
     * @param client_id
     * @param roleName
     * @param description
     */
    public void addClientRole(String realm, String client_id, String roleName, String description) {
        RolesResource roles = keycloak.realm(realm).clients().get(client_id).roles();
        RoleRepresentation roleRepresentation = new RoleRepresentation();
        roleRepresentation.setName(roleName);
        roleRepresentation.setDescription(description);
        try {
            roles.create(roleRepresentation);
        } catch (Exception e) {
                throw new BizException("当前新增角色名称为"+roleName+"的数据已经存在,请确认后重新添加");
        }
    }

    /**
     * 修改客户端角色
     *
     * @param realm
     * @param client_id
     * @param roleName
     * @param description
     */
    public void updateClientRole(String realm, String client_id, String roleName, String description) {
        RoleResource roleResource = keycloak.realm(realm).clients().get(client_id).roles().get(roleName);
        RoleRepresentation roleRepresentation = roleResource.toRepresentation();
        roleRepresentation.setDescription(description);
        roleResource.update(roleRepresentation);
    }

    /**
     * 删除客户端角色
     *
     * @param realm
     * @param client_id
     * @param roleName
     */
    public void deleteClientRole(String realm, String client_id, String roleName) {
        keycloak.realm(realm).clients().get(client_id).roles().get(roleName).remove();
    }

    /**
     * 客户端角色详情
     *
     * @param roleName
     * @return
     */
    public RoleRepresentation clientRoleDetail(String realm, String client_id, String roleName) {
        return keycloak.realm(realm).clients().get(client_id).roles().get(roleName).toRepresentation();
    }


    /**
     * 客户端所有角色
     *
     * @param realm
     * @param client_id
     * @param search
     * @param pagination
     * @return
     */
    public List<RoleRepresentation> clientRoleList(String realm, String client_id, String search, Pagination pagination) {
        return keycloak.realm(realm).clients().get(client_id).roles().list(search, (pagination.getPage() - 1) * pagination.getSize(), pagination.getSize());
    }

    /**
     * 客户端所有角色
     *
     * @param realm
     * @param client_id
     * @param search
     * @return
     */
    public List<RoleRepresentation> clientRoleList(String realm, String client_id, String search) {
        return keycloak.realm(realm).clients().get(client_id).roles().list(search, false);
    }

    /**
     * 客户端角色下所有用户
     *
     * @param realm
     * @param client_id
     * @param roleName
     * @return
     */
    public Set<UserRepresentation> clientRoleUsers(String realm, String client_id, String roleName, Pagination pagination) {
        return keycloak.realm(realm).clients().get(client_id).roles().get(roleName).getRoleUserMembers((pagination.getPage() - 1) * pagination.getSize(), pagination.getSize());
    }

    /**
     * 客户端角色下所有用户
     *
     * @param realm
     * @param client_id
     * @param roleName
     * @return
     */
    public Set<UserRepresentation> clientRoleUsers(String realm, String client_id, String roleName) {
        return keycloak.realm(realm).clients().get(client_id).roles().get(roleName).getRoleUserMembers();
    }

    /**
     * 用户客户端角色
     * @param realm
     * @param userId
     * @param client_clientId
     * @return
     */
    public List<RoleRepresentation> userClientRole(String realm, String userId, String client_clientId) {
        Map<String, ClientMappingsRepresentation> clientRoleMap = keycloak.realm(realm).users().get(userId).roles().getAll().getClientMappings();
        List<RoleRepresentation> mappings = null;
        if (clientRoleMap != null && client_clientId != null && clientRoleMap.containsKey(client_clientId)) {
            mappings = clientRoleMap.get(client_clientId).getMappings();
        }
        return mappings;
    }

    /**
     * 用户添加客户端角色
     *
     * @param realm
     * @param client_id
     * @param userId
     * @param roleName
     */
    public void addUserClientRole(String realm, String client_id, String userId, String roleName) {
        RealmResource realmResource = keycloak.realm(realm);
        RoleRepresentation clientRoleRep = realmResource.clients().get(client_id).roles().get(roleName).toRepresentation();
        realmResource.users().get(userId).roles().clientLevel(client_id).add(Arrays.asList(clientRoleRep));
    }

    /**
     * 用户删除客户端角色
     *
     * @param realm
     * @param client_id
     * @param userId
     * @param roleName
     */
    public void deleteUserClientRole(String realm, String client_id, String userId, String roleName) {
        RealmResource realmResource = keycloak.realm(realm);
        RoleRepresentation clientRoleRep = realmResource.clients().get(client_id).roles().get(roleName).toRepresentation();
        realmResource.users().get(userId).roles().clientLevel(client_id).remove(Arrays.asList(clientRoleRep));
    }
}
