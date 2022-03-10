//package com.qk.dm.authority.rest;
//
//import com.qk.dam.commons.enums.ResultCodeEnum;
//import com.qk.dam.commons.http.result.DefaultCommonResult;
//import com.qk.dam.jpa.pojo.PageResultVO;
//import com.qk.dam.jpa.pojo.Pagination;
//import com.qk.dm.authority.keycloak.KeyCloakApi;
//import com.qk.dm.authority.vo.user.AtyUserInfoVO;
//import com.qk.dm.authority.vo.user.AtyUserKeyCloakVO;
//import com.qk.dm.authority.vo.user.AtyUserParamVO;
//import org.springframework.web.bind.annotation.*;
//
///**
// * 权限管理用户
// *
// * @author shenpj
// * @date 2022/2/22 14:49
// * @since 1.0.0
// */
//@RestController
//@RequestMapping("/keycloak")
//public class KeyCloakController {
//
//    private final KeyCloakApi keyCloakApi;
//
//    public KeyCloakController(KeyCloakApi keyCloakApi) {
//        this.keyCloakApi = keyCloakApi;
//    }
//
//    @GetMapping("/realmList")
//    public Object realmList() {
//        return keyCloakApi.realmList();
//    }
//
//    @GetMapping("/clientList")
//    public Object clientList(String realm,String client_clientId) {
//        return keyCloakApi.clientListByRealm(realm,client_clientId);
//    }
//
//    @PostMapping("/user")
//    public void addUser(String realm, AtyUserKeyCloakVO userVO) {
//        userVO = AtyUserKeyCloakVO.builder().username("zhangsan1").enabled(true).firstName("名称").lastName("姓").email("email").password("123456").build();
//        keyCloakApi.createUser(realm, userVO);
//    }
//
//    @PutMapping("/user")
//    public void updateUser(String realm, AtyUserKeyCloakVO userVO) {
//        userVO = AtyUserKeyCloakVO.builder().id(userVO.getId()).enabled(true).firstName("名称22").lastName("姓").email("286777111@qq.com").build();
//        keyCloakApi.updateUser(realm,userVO);
//    }
//
//    @DeleteMapping("/user")
//    public void deleteUser(String realm,String userId) {
//        keyCloakApi.deleteUser(realm,userId);
//    }
//
//    @PutMapping("/password")
//    public void deleteUser(String realm,String userId,String password) {
//        keyCloakApi.resetUserPassword(realm,userId,password);
//    }
//
//    @GetMapping("/user")
//    public Object getUser(String realm,String userId) {
//        return keyCloakApi.userDetail(realm,userId);
//    }
//
//    @GetMapping("/users")
//    public Object getUsers(String realm, String search, Pagination pagination) {
//        return keyCloakApi.getUserList(realm,search,pagination);
//    }
//
//    @PostMapping("/group")
//    public void addgroup(String realm,String groupName) {
//        keyCloakApi.addGroup(realm,groupName);
//    }
//
//    @PutMapping("/group")
//    public void updategroup(String realm,String groupId,String groupName) {
//        keyCloakApi.updateGroup(realm,groupId,groupName);
//    }
//
//    @DeleteMapping("/group")
//    public void deletegroup(String realm,String groupId) {
//        keyCloakApi.deleteGroup(realm,groupId);
//    }
//
//    @GetMapping("/group")
//    public Object group(String realm,String groupId) {
//        return keyCloakApi.groupDetail(realm,groupId);
//    }
//
//    @GetMapping("/groupList")
//    public Object groupList(String realm, String search, Pagination pagination) {
//        return keyCloakApi.groupList(realm,search,pagination);
//    }
//
//    @GetMapping("/userGroup")
//    public Object userGroup(String realm, String userId) {
//        return keyCloakApi.userGroup(realm,userId);
//    }
//
//    @PostMapping("/userGroup")
//    public void addUserGroup(String realm,String userId,String groupId) {
//        keyCloakApi.addUserGroup(realm,userId,groupId);
//    }
//
//    @DeleteMapping("/userGroup")
//    public void deleteUserGroup(String realm,String userId,String groupId) {
//        keyCloakApi.deleteUserGroup(realm,userId,groupId);
//    }
//
//    @PostMapping("/clientRole")
//    public void addclientRole(String realm,String client_id,String roleName,String description) {
//        keyCloakApi.addClientRole(realm,client_id,roleName,description);
//    }
//
//    @PutMapping("/clientRole")
//    public void updateClientRole(String realm,String client_id,String roleName,String description) {
//        keyCloakApi.updateClientRole(realm,client_id,roleName,description);
//    }
//
//    @DeleteMapping("/clientRole")
//    public void deleteClientRole(String realm,String client_id,String roleName) {
//        keyCloakApi.deleteClientRole(realm,client_id,roleName);
//    }
//
//    @GetMapping("/clientRole")
//    public Object clientRoleDetail(String realm,String client_id,String roleName) {
//        return keyCloakApi.clientRoleDetail(realm,client_id,roleName);
//    }
//
//    @GetMapping("/clientRoleList")
//    public Object clientRoleList(String realm,String client_id,String search, Pagination pagination) {
//        return keyCloakApi.clientRoleList(realm,client_id,search,pagination);
//    }
//
//    @GetMapping("/userClientRole")
//    public Object userClientRole(String realm, String userId,String client_clientId) {
//        return keyCloakApi.userClientRole(realm,userId,client_clientId);
//    }
//
//    @PostMapping("/userClientRole")
//    public void addUserClientRole(String realm,String client_id,String userId,String roleName) {
//        keyCloakApi.addUserClientRole(realm,client_id,userId,roleName);
//    }
//
//    @DeleteMapping("/userClientRole")
//    public void deleteUserClientRole(String realm,String client_id,String userId,String roleName) {
//        keyCloakApi.deleteUserClientRole(realm,client_id,userId,roleName);
//    }
//
//    /**
//     * 分页查询用户信息
//     *
//     * @param atyUserParamVO
//     * @return DefaultCommonResult<PageResultVO < UserInfoVO> >
//     */
//    @PostMapping("/query")
//    public DefaultCommonResult<PageResultVO<AtyUserInfoVO>> queryUsers(@RequestBody AtyUserParamVO atyUserParamVO) {
//        return DefaultCommonResult.success(ResultCodeEnum.OK, keyCloakApi.getUserList(atyUserParamVO.getRealm(), atyUserParamVO.getSearch(), atyUserParamVO.getPagination()));
//    }
//
//}
