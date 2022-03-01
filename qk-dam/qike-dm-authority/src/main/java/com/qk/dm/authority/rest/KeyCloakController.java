package com.qk.dm.authority.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dam.jpa.pojo.Pagination;
import com.qk.dm.authority.keycloak.KeyCloakApi;
import com.qk.dm.authority.vo.UserInfoVO;
import com.qk.dm.authority.vo.UserVO;
import com.qk.dm.authority.vo.params.UserParamVO;
import org.springframework.web.bind.annotation.*;

/**
 * 权限管理用户
 *
 * @author shenpj
 * @date 2022/2/22 14:49
 * @since 1.0.0
 */
@RestController
@RequestMapping("/keycloak")
public class KeyCloakController {

    private final KeyCloakApi keyCloakApi;

    public KeyCloakController(KeyCloakApi keyCloakApi) {
        this.keyCloakApi = keyCloakApi;
    }

    @GetMapping("/realmList")
    public Object realmList() {
        return keyCloakApi.realmList();
    }

    @GetMapping("/clientList")
    public Object clientList(String realm,String clientId) {
        return keyCloakApi.clientListByRealm(realm,clientId);
    }

    @PostMapping("/user")
    public void addUser(String realm,UserVO userVO) {
        userVO = UserVO.builder().username("zhangsan").enabled(true).firstName("名称").lastName("姓").email("email").password("123456").build();
        keyCloakApi.createUser(realm,userVO);
    }

    @PutMapping("/user")
    public void updateUser(String realm,UserVO userVO) {
        userVO = UserVO.builder().id(userVO.getId()).enabled(true).firstName("名称22").lastName("姓").email("286777112@qq.com").build();
        keyCloakApi.updateUser(realm,userVO);
    }

    @DeleteMapping("/user")
    public void deleteUser(String realm,String userId) {
        keyCloakApi.deleteUser(realm,userId);
    }

    @PutMapping("/password")
    public void deleteUser(String realm,String userId,String password) {
        keyCloakApi.resetUserPassword(realm,userId,password);
    }

    @GetMapping("/user")
    public Object getUser(String realm,String userId) {
        return keyCloakApi.userDetail(realm,userId);
    }

    @GetMapping("/users")
    public Object getUsers(String realm, String search, Pagination pagination) {
        return keyCloakApi.getUserList(realm,search,pagination);
    }

    @PostMapping("/group")
    public void addgroup(String realm,String groupName) {
        keyCloakApi.addGroup(realm,groupName);
    }

    @PutMapping("/group")
    public void updategroup(String realm,String groupId,String groupName) {
        keyCloakApi.updateGroup(realm,groupId,groupName);
    }

    @DeleteMapping("/group")
    public void deletegroup(String realm,String groupId) {
        keyCloakApi.deleteGroup(realm,groupId);
    }

    @GetMapping("/group")
    public Object group(String realm,String groupId) {
        return keyCloakApi.groupDetail(realm,groupId);
    }

    @GetMapping("/groupList")
    public Object groupList(String realm, String search, Pagination pagination) {
        return keyCloakApi.groupList(realm,search,pagination);
    }

    @GetMapping("/userGroup")
    public Object userGroup(String realm, String userId) {
        return keyCloakApi.userGroup(realm,userId);
    }

    @PostMapping("/userGroup")
    public void addUserGroup(String realm,String userId,String groupId) {
        keyCloakApi.addUserGroup(realm,userId,groupId);
    }

    @DeleteMapping("/userGroup")
    public void deleteUserGroup(String realm,String userId,String groupId) {
        keyCloakApi.deleteUserGroup(realm,userId,groupId);
    }

    @PostMapping("/clientRole")
    public void addclientRole(String realm,String clentId,String roleName,String description) {
        keyCloakApi.addClientRole(realm,clentId,roleName,description);
    }

    @PutMapping("/clientRole")
    public void updateClientRole(String realm,String clentId,String roleName,String description) {
        keyCloakApi.updateClientRole(realm,clentId,roleName,description);
    }

    @DeleteMapping("/clientRole")
    public void deleteClientRole(String realm,String clentId,String roleName) {
        keyCloakApi.deleteClientRole(realm,clentId,roleName);
    }

    @GetMapping("/clientRole")
    public Object clientRoleDetail(String realm,String clentId,String roleName) {
        return keyCloakApi.clientRoleDetail(realm,clentId,roleName);
    }

    @GetMapping("/clientRoleList")
    public Object clientRoleList(String realm,String clentId,String search, Pagination pagination) {
        return keyCloakApi.clientRoleList(realm,clentId,search,pagination);
    }

    @GetMapping("/userClientRole")
    public Object userClientRole(String realm, String userId) {
        return keyCloakApi.userClientRole(realm,userId);
    }

    @PostMapping("/userClientRole")
    public void addUserClientRole(String realm,String clentId,String userId,String roleName) {
        keyCloakApi.addUserClientRole(realm,clentId,userId,roleName);
    }

    @DeleteMapping("/userClientRole")
    public void deleteUserClientRole(String realm,String clentId,String userId,String roleName) {
        keyCloakApi.deleteUserClientRole(realm,clentId,userId,roleName);
    }

    /**
     * 分页查询用户信息
     *
     * @param userParamVO
     * @return DefaultCommonResult<PageResultVO < UserInfoVO> >
     */
    @PostMapping("/query")
    public DefaultCommonResult<PageResultVO<UserInfoVO>> queryUsers(@RequestBody UserParamVO userParamVO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, keyCloakApi.getUserList(userParamVO));
    }

}
