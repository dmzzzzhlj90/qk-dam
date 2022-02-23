package com.qk.dm.authority.keycloak;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.authority.vo.UserInfoVO;
import com.qk.dm.authority.vo.UserVO;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.ws.rs.core.Response;
import java.util.*;

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

        List<UserRepresentation> userList = userResource.list();
        List<UserInfoVO> userVOS = new ArrayList<>();
        if(userList != null) {
            for (UserRepresentation user : userList) {
                UserInfoVO userVO = UserInfoVO.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .enabled(user.isEnabled())
                        .createdTimestamp(user.getCreatedTimestamp())
                        .build();
                //属性
                userVO.setUserAttributesList(user.getAttributes());
                UserResource userInfo = userResource.get(user.getId());

                //组
                List<GroupRepresentation> groups = userInfo.groups();
                if(groups != null){
                    for (GroupRepresentation group : groups) {
                        System.out.println(group.getName());
                    }
                }
                //角色
                RoleMappingResource roles = userInfo.roles();
                MappingsRepresentation mappingsRepresentation = roles.getAll();
                List<RoleRepresentation> realmMappings = mappingsRepresentation.getRealmMappings();
                if(realmMappings != null){
                    for (RoleRepresentation realmMapping : realmMappings) {
                        System.out.println(realmMapping.getName());
                    }
                }
                System.out.println("-----------------------------------");

                userVO.setUserRealmRoles(user.getRealmRoles());
                //组
                userVO.setUserGroups(user.getGroups());
                userVOS.add(userVO);
            }
        }
        return userVOS;
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


        UserRepresentation user = new UserRepresentation();
        // 设置登录账号
        user.setUsername(userVO.getUsername());
        user.setEnabled(userVO.getEnabled());
        user.setFirstName(userVO.getFirstName());
        user.setLastName(userVO.getLastName());
        user.setEmail(userVO.getEmail());
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
     * @param userVO
     */
    public void updateUserInfo(UserVO userVO) {
        UsersResource userResource = keycloak.realm(TARGET_REALM).users();
        UserRepresentation user = new UserRepresentation();
        // 设置登录账号
        user.setUsername(userVO.getUsername());
        user.setEnabled(userVO.getEnabled());
        user.setFirstName(userVO.getFirstName());
        user.setLastName(userVO.getLastName());
        user.setEmail(userVO.getEmail());
        user.setEmailVerified(false);
        userResource.get(userVO.getId()).update(user);
    }

    /**
     * 重置密码
     * @param userVO
     */
    public void resetUserPassword(UserVO userVO){
        UsersResource userResource = keycloak.realm(TARGET_REALM).users();
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(userVO.getPassword());
        // 重置用户密码
        userResource.get(userVO.getId()).resetPassword(passwordCred);
    }

    /**
     * 更改属性
     * @param userVO
     */
    public void resetUserAttributes(UserVO userVO){
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
    public void groups(){
        GroupsResource groups = keycloak.realm(TARGET_REALM).groups();

        List<GroupRepresentation> group = groups.groups();
        for (GroupRepresentation groupRepresentation : group) {
            //分组类
            System.out.println(groupRepresentation.getName());
            //分组下用户
            List<UserRepresentation> members = groups.group(groupRepresentation.getId()).members();
            if(members != null){
                for (UserRepresentation member : members) {
                    System.out.println("member:"+member.getUsername());
                }
            }
            System.out.println(groupRepresentation.getAttributes());
            System.out.println("------------------------------------");
        }
    }

    /**
     * 用户分组
     * @param userId
     */
    public void usetGroup(String userId){
        UsersResource userResource = keycloak.realm(TARGET_REALM).users();
        UserResource userInfo = userResource.get(userId);

        //组
        List<GroupRepresentation> groups = userInfo.groups();
        if(groups != null){
            for (GroupRepresentation group : groups) {
                //分组类
                System.out.println(group.getName());
            }
        }
    }

    /**
     * 用户角色
     * @param userId
     */
    public void usetRole(String userId){
        UsersResource userResource = keycloak.realm(TARGET_REALM).users();
        UserResource userInfo = userResource.get(userId);
        //角色
        RoleMappingResource roles = userInfo.roles();
        MappingsRepresentation mappingsRepresentation = roles.getAll();
        List<RoleRepresentation> realmMappings = mappingsRepresentation.getRealmMappings();
        if(realmMappings != null){
            for (RoleRepresentation realmMapping : realmMappings) {
                //角色类
                System.out.println(realmMapping.getName());
            }
        }
    }
}
