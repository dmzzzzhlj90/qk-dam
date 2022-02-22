package com.qk.dm.authority.keycloak;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.authority.vo.UserVO;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shenpj
 * @date 2022/2/22 11:13
 * @since 1.0.0
 */
@Component
public class KeyCloakApi {
    private final RealmResource realmResource;

    public KeyCloakApi(RealmResource realmResource) {
        this.realmResource = realmResource;
    }



    /**
     * 获取用户列表
     *
     * @return
     */
    public List<UserVO> getUserList() {
        UsersResource userResource = realmResource.users();
        List<UserRepresentation> userList = userResource.list();
        List<UserVO> userVOS = new ArrayList<>();
        if(userList != null) {
            for (UserRepresentation user : userList) {
                UserVO userVO = UserVO.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .enabled(user.isEnabled())
                        .createdTimestamp(user.getCreatedTimestamp())
                        .build();
                //属性
                Map<String, List<String>> userAttributesList = user.getAttributes();
                //角色
                List<String> userRealmRoles = user.getRealmRoles();
                //组
                List<String> userGroups = user.getGroups();

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
    public Map<String, Object> getUserAttribute(String userId) {
        Map<String, Object> attributeMap = new HashMap<>();
        UsersResource userResource = realmResource.users();
        List<UserRepresentation> userList = userResource.list();
        for (UserRepresentation user : userList) {
            Map<String, List<String>> userAttributesList = user.getAttributes();
            if (userAttributesList != null && userId != null && userId.equals(user.getId())) {
                for (String key : userAttributesList.keySet()) {
                    Object attribute = null;
                    if (userAttributesList != null && userAttributesList.get(key) != null && userAttributesList.get(key).size() > 0) {
                        attribute = userAttributesList.get(key);
                    }
                    attributeMap.put(user.getId() + "-" + key, attribute);
                }
            }
        }
        return attributeMap;
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
        Response response = realmResource.users().create(user);

        //判断创建用户状态；如果时创建成功
        Response.StatusType createUserStatus = response.getStatusInfo();
        System.out.println(createUserStatus);
        if (!"Created".equals(createUserStatus.toString())) {
            throw new BizException("账号已经存在！");
        }
    }

}
