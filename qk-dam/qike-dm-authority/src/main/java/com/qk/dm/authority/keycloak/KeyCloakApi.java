package com.qk.dm.authority.keycloak;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dam.jpa.pojo.Pagination;
import com.qk.dm.authority.mapstruct.AtyGroupMapper;
import com.qk.dm.authority.mapstruct.AtyRoleMapper;
import com.qk.dm.authority.mapstruct.AtyUserMapper;
import com.qk.dm.authority.mapstruct.KeyCloakMapper;
import com.qk.dm.authority.vo.*;
import com.qk.dm.authority.vo.clientrole.AtyClientRoleInfoVO;
import com.qk.dm.authority.vo.group.AtyGroupInfoVO;
import com.qk.dm.authority.vo.user.AtyUserInfoVO;
import com.qk.dm.authority.vo.user.AtyUserInputExceVO;
import com.qk.dm.authority.vo.user.AtyUserKeyCloakVO;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.*;
import org.springframework.stereotype.Service;


import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author shenpj
 * @date 2022/2/22 11:13
 * @since 1.0.0
 */
@Service
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
    public List<RealmVO> realmList() {
        return KeyCloakMapper.INSTANCE.userRealm(keycloak.realms().findAll());
    }

    /**
     * 域下客户端列表
     *
     * @param realm
     * @param client_clientId
     * @return
     */
    public List<ClientVO> clientListByRealm(String realm, String client_clientId) {
        List<ClientRepresentation> list = keycloak.realm(realm).clients()
                .findAll(client_clientId, true, true, null, null);
        return KeyCloakMapper.INSTANCE.userClient(list);
    }


    /**
     * 创建用户
     *
     * @param realm
     * @param userVO
     */
    public void createUser(String realm, AtyUserKeyCloakVO userVO) {
        //todo 用户名电子邮箱不能重复
        UserRepresentation user = AtyUserMapper.INSTANCE.userInfo(userVO);
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
        Response.StatusType createUserStatus = response.getStatusInfo();
        if (!"Created".equals(createUserStatus.toString())) {
            throw new BizException("用户名或邮箱已经存在！");
        }
    }

    /**
     * 修改用户信息
     *
     * @param realm
     * @param userVO
     */
    public void updateUser(String realm, AtyUserKeyCloakVO userVO) {
        UsersResource userResource = keycloak.realm(realm).users();
        UserResource user = userResource.get(userVO.getId());
        UserRepresentation userRepresentation = user.toRepresentation();
        AtyUserMapper.INSTANCE.userUpdate(userVO, userRepresentation);
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
    public AtyUserInfoVO userDetail(String realm, String userId) {
        UserResource resource = keycloak.realm(realm).users().get(userId);
        UserRepresentation user = resource.toRepresentation();
        AtyUserInfoVO atyUserInfoVO = AtyUserMapper.INSTANCE.userInfo(user);
        //角色 todo 在这里用户不传客户端id，用户角色单独查询
//        userInfoVO.setClientRoleList(getUserClientRole(resource,client_clientId));
        //组
        atyUserInfoVO.setGroupList(getGroupList(resource));
        return atyUserInfoVO;
    }

    /**
     * 用户列表
     *
     * @param realm
     * @param search
     * @param pagination
     * @return
     */
    public PageResultVO<AtyUserInfoVO> getUserList(String realm, String search, Pagination pagination) {
        UsersResource userResource = keycloak.realm(realm).users();
        List<UserRepresentation> userList = userResource.search(
                search,
                (pagination.getPage() - 1) * pagination.getSize(),
                pagination.getSize());
        return new PageResultVO<>(
                (long) userResource.count(search),
                pagination.getPage(),
                pagination.getSize(),
                AtyUserMapper.INSTANCE.userInfo(userList));
    }

    /**
     * 用户列表-不分页
     *
     * @param realm
     * @param search
     * @return
     */
    public List<AtyUserInfoVO> getUserList(String realm, String search) {
        return AtyUserMapper.INSTANCE.userInfo(keycloak.realm(realm).users().search(search));
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
    public AtyGroupInfoVO groupDetail(String realm, String groupId) {
        GroupResource groupResource = keycloak.realm(realm).groups().group(groupId);
        AtyGroupInfoVO atyGroupInfoVO = AtyGroupMapper.INSTANCE.userGroup(groupResource.toRepresentation());
        List<UserRepresentation> members = groupResource.members();
        List<AtyUserInfoVO> userInfos = AtyUserMapper.INSTANCE.userInfo(members);
        atyGroupInfoVO.setMembers(userInfos);
        return atyGroupInfoVO;
    }

    /**
     * 查询所有分组
     */
    public PageResultVO<AtyGroupInfoVO> groupList(String realm, String search, Pagination pagination) {
        GroupsResource groupsResource = keycloak.realm(realm).groups();
        List<GroupRepresentation> groups = groupsResource.groups(
                search,
                (pagination.getPage() - 1) * pagination.getSize(),
                pagination.getSize());
        return new PageResultVO<>(
                groupsResource.groups(search, null, null).size(),
                pagination.getPage(),
                pagination.getSize(),
                AtyGroupMapper.INSTANCE.userGroup(groups));
    }

    /**
     * 查询所有分组
     */
    public List<AtyGroupInfoVO> groupList(String realm, String search) {
        return AtyGroupMapper.INSTANCE.userGroup(keycloak.realm(realm).groups().groups(search, null, null));
    }


    /**
     * 用户分组列表
     *
     * @param userId
     */
    public List<AtyGroupInfoVO> userGroup(String realm, String userId) {
        return getGroupList(keycloak.realm(realm).users().get(userId));
    }

    private List<AtyGroupInfoVO> getGroupList(UserResource resource) {
        return resource.groups().stream().map(AtyGroupMapper.INSTANCE::userGroup).collect(Collectors.toList());
    }


    /**
     * 用户添加分组
     */
    public void addUserGroup(String realm, String userId, String groupId) {
        keycloak.realm(realm).users().get(userId).joinGroup(groupId);
    }

    /**
     * 用户离开分组
     */
    public void deleteUserGroup(String realm, String userId, String groupId) {
        keycloak.realm(realm).users().get(userId).leaveGroup(groupId);
    }

    /**********************************分组结束************************************/


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
        roles.create(roleRepresentation);
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
    public AtyClientRoleInfoVO clientRoleDetail(String realm, String client_id, String roleName) {
        RoleResource roleResource = keycloak.realm(realm).clients().get(client_id).roles().get(roleName);
        AtyClientRoleInfoVO atyClientRoleInfoVO = AtyRoleMapper.INSTANCE.userRole(roleResource.toRepresentation());
        Set<UserRepresentation> roleUserMembers = roleResource.getRoleUserMembers();
        List<AtyUserInfoVO> atyUserInfoVOS = AtyUserMapper.INSTANCE.userInfo(new ArrayList<>(roleUserMembers));
        atyClientRoleInfoVO.setMembers(atyUserInfoVOS);
        return atyClientRoleInfoVO;
    }

    /**
     * 客户端所有角色
     *
     * @return
     */
    public PageResultVO<AtyClientRoleInfoVO> clientRoleList(String realm, String client_id, String search, Pagination pagination) {
        RolesResource roles = keycloak.realm(realm).clients().get(client_id).roles();
        //todo 分页
        List<RoleRepresentation> list = roles.list(
                search,
                (pagination.getPage() - 1) * pagination.getSize(),
                pagination.getSize());
        return new PageResultVO<>(
                roles.list(search, false).size(),
                pagination.getPage(),
                pagination.getSize(),
                AtyRoleMapper.INSTANCE.userRole(list));
    }

    /**
     * 客户端所有角色
     *
     * @return
     */
    public List<AtyClientRoleInfoVO> clientRoleList(String realm, String client_id, String search) {
        RolesResource roles = keycloak.realm(realm).clients().get(client_id).roles();
        return AtyRoleMapper.INSTANCE.userRole(roles.list(search, false));
    }

    /**
     * 用户客户端角色
     *
     * @param userId
     */
    public List<AtyClientRoleInfoVO> userClientRole(String realm, String userId, String client_clientId) {
        return getUserClientRole(keycloak.realm(realm).users().get(userId), client_clientId);
    }

    private List<AtyClientRoleInfoVO> getUserClientRole(UserResource resource, String client_clientId) {
        List<AtyClientRoleInfoVO> atyClientRoleInfoVOS = null;
        Map<String, ClientMappingsRepresentation> clientMappings = resource.roles().getAll().getClientMappings();
        if (clientMappings != null && client_clientId != null && clientMappings.get(client_clientId) != null) {
            ClientMappingsRepresentation clientMappingsRepresentation = clientMappings.get(client_clientId);
            List<RoleRepresentation> mappings = clientMappingsRepresentation.getMappings();
            atyClientRoleInfoVOS = AtyRoleMapper.INSTANCE.userRole(mappings);
        }
        return atyClientRoleInfoVOS;
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

    /**
     * 导入添加用户信息
     * @param userlist
     * @param relame
     */
    public void saveAllUsers(List<AtyUserInputExceVO> userlist, String relame) {
        userlist.forEach(atyUserInputExceVO -> {
            //todo 用户名电子邮箱不能重复
            UserRepresentation user = AtyUserMapper.INSTANCE.userExcelInfo(atyUserInputExceVO);
            user.setEmailVerified(false);
            // 设置密码
            List<CredentialRepresentation> credentials = new ArrayList<>();
            CredentialRepresentation cr = new CredentialRepresentation();
            cr.setType(CredentialRepresentation.PASSWORD);
            cr.setValue(atyUserInputExceVO.getPassword());
            //临时密码，如果启用，用户需在下次登陆时更换密码
            cr.setTemporary(false);
            credentials.add(cr);
            user.setCredentials(credentials);
            //创建用户
            Response response = keycloak.realm(relame).users().create(user);
            Response.StatusType createUserStatus = response.getStatusInfo();
            if (!"Created".equals(createUserStatus.toString())) {
                throw new BizException("用户名或邮箱已经存在！");
            }
        });
    }
}
