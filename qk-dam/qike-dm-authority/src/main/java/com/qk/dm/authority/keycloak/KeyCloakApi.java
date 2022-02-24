package com.qk.dm.authority.keycloak;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
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
     * 获取所有域
     *
     * @return
     */
    public List<RealmVO> realmList() {
        RealmsResource realms = keycloak.realms();
        List<RealmRepresentation> list = realms.findAll();
        return KeyCloakMapper.INSTANCE.userRealm(list);
    }

    /**
     * 获取域下的所有客户端
     *
     * @return
     */
    public List<ClientVO> clientList() {
        ClientsResource clients = keycloak.realm(TARGET_REALM).clients();
        //todo 可分页及搜索
        List<ClientRepresentation> list = clients.findAll();
        return KeyCloakMapper.INSTANCE.userClient(list);
    }


    /**
     * 获取用户列表
     *
     * @return
     */
    public List<UserInfoVO> getUserList() {
        UsersResource userResource = keycloak.realm(TARGET_REALM).users();
        List<UserRepresentation> userList = userResource.search("user", 0, 10);
        return userList.stream().map(KeyCloakMapper.INSTANCE::userInfo).collect(Collectors.toList());
    }

    public PageResultVO<UserInfoVO> getUserList(UserParamVO userParamVO) {
        UsersResource userResource = keycloak.realm(TARGET_REALM).users();
        Integer count = keycloak.realm(TARGET_REALM).users().count();
        List<UserRepresentation> userList = userResource.search(userParamVO.getSearch(),
                Util.dealPage(userParamVO), userParamVO.getPagination().getSize());
        List<UserInfoVO> collect = userList.stream().map(user -> {
            UserInfoVO userInfoVO = KeyCloakMapper.INSTANCE.userInfo(user);
            userInfoVO.setClientRoleList(userClientRole(user.getId()));
            //组
            userInfoVO.setGroupList(userGroup(user.getId()));
            return userInfoVO;
        }).collect(Collectors.toList());
        return new PageResultVO<>(
                (long) count,
                userParamVO.getPagination().getPage(),
                userParamVO.getPagination().getSize(),
                collect);
    }


    /**
     * 用户详情
     *
     * @param userId
     * @return
     */
    public UserInfoVO userDetail(String userId) {
        UsersResource userResource = keycloak.realm(TARGET_REALM).users();
        UserResource resource = userResource.get(userId);
        UserRepresentation user = resource.toRepresentation();
        UserInfoVO userInfoVO = KeyCloakMapper.INSTANCE.userInfo(user);
        //角色
        userInfoVO.setClientRoleList(getUserClientRole(resource));
        //组
        userInfoVO.setGroupList(getGroupList(resource));
        return userInfoVO;
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
        KeyCloakMapper.INSTANCE.userUpdate(userVO, userRepresentation);
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
        //todo 分页
        List<GroupRepresentation> groups = groupsResource.groups();
        return KeyCloakMapper.INSTANCE.userGroup(groups);
    }

    /**
     * 分组详情
     *
     * @param groupId
     * @return
     */
    public GroupVO groupDetail(String groupId) {
        GroupsResource groupsResource = keycloak.realm(TARGET_REALM).groups();
        GroupResource groupResource = groupsResource.group(groupId);
        GroupVO groupVO = KeyCloakMapper.INSTANCE.userGroup(groupResource.toRepresentation());
        List<UserRepresentation> members = groupResource.members();
        List<UserInfoVO> userInfos = KeyCloakMapper.INSTANCE.userInfo(members);
        groupVO.setMembers(userInfos);
        return groupVO;
    }

    /**
     * 用户分组
     *
     * @param userId
     */
    public List<GroupVO> userGroup(String userId) {
        UsersResource userResource = keycloak.realm(TARGET_REALM).users();
        UserResource resource = userResource.get(userId);
        return getGroupList(resource);
    }

    private List<GroupVO> getGroupList(UserResource resource) {
        return resource.groups().stream().map(KeyCloakMapper.INSTANCE::userGroup).collect(Collectors.toList());
    }

    /**
     * 域所有角色
     *
     * @return
     */
    public List<RoleVO> realmRoleList() {
        RolesResource roles = keycloak.realm(TARGET_REALM).roles();
        //todo 分页
        List<RoleRepresentation> representationList = roles.list("", 0, 10);
        return representationList.stream().map(representation -> {
            RoleVO roleVO = KeyCloakMapper.INSTANCE.userRole(representation);
            RoleResource roleResource = roles.get(representation.getName());
            Set<UserRepresentation> roleUserMembers = roleResource.getRoleUserMembers();
            List<UserInfoVO> userInfoVOS = KeyCloakMapper.INSTANCE.userInfo(new ArrayList<>(roleUserMembers));
            roleVO.setMembers(userInfoVOS);
            return roleVO;
        }).collect(Collectors.toList());
    }

    /**
     * 客户端所有角色
     *
     * @return
     */
    public List<RoleVO> clientRoleList() {
        String id = "6fdfce20-1f50-43a2-bbbe-62337e35c3d9";
        ClientsResource clients = keycloak.realm(TARGET_REALM).clients();
        ClientResource clientResource = clients.get(id);
        RolesResource roles = clientResource.roles();
        //todo 分页
        List<RoleRepresentation> list = roles.list();
        return KeyCloakMapper.INSTANCE.userRole(list);
    }

    /**
     * 客户端角色详情
     *
     * @param roleName
     * @return
     */
    public RoleVO clientRoleDetail(String roleName) {
        String id = "6fdfce20-1f50-43a2-bbbe-62337e35c3d9";
        ClientsResource clients = keycloak.realm(TARGET_REALM).clients();
        ClientResource clientResource = clients.get(id);
        RolesResource roles = clientResource.roles();
        RoleResource roleResource = roles.get(roleName);
        RoleVO roleVO = KeyCloakMapper.INSTANCE.userRole(roleResource.toRepresentation());
        Set<UserRepresentation> roleUserMembers = roleResource.getRoleUserMembers();
        List<UserInfoVO> userInfoVOS = KeyCloakMapper.INSTANCE.userInfo(new ArrayList<>(roleUserMembers));
        roleVO.setMembers(userInfoVOS);
        return roleVO;
    }

    /**
     * 用户客户端角色
     *
     * @param userId
     */
    public List<RoleVO> userClientRole(String userId) {
        UsersResource userResource = keycloak.realm(TARGET_REALM).users();
        UserResource resource = userResource.get(userId);
        return getUserClientRole(resource);
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
