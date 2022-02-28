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

    @GetMapping("/users")
    public Object getUsers(String realm, String search, Pagination pagination) {
        return keyCloakApi.getUserList(realm,search,pagination);
    }

    @GetMapping("/user")
    public Object getUser(String realm,String userId) {
        return keyCloakApi.userDetail(realm,userId);
    }

    @GetMapping("/groupList")
    public Object groupList(String realm) {
        return keyCloakApi.groupList(realm);
    }

    @GetMapping("/group")
    public Object group(String realm,String groupId) {
        return keyCloakApi.groupDetail(realm,groupId);
    }

    @GetMapping("/clientRoleList")
    public Object clientRoleList(String realm,String id,String search, Pagination pagination) {
        return keyCloakApi.clientRoleList(realm,id,search,pagination);
    }

    @GetMapping("/clientRoleDetail")
    public Object clientRoleDetail(String realm,String id,String roleName) {
        return keyCloakApi.clientRoleDetail(realm,id,roleName);
    }

//    /**
//     * 分页查询用户信息
//     *
//     * @param userParamVO
//     * @return DefaultCommonResult<PageResultVO < UserInfoVO> >
//     */
//    @PostMapping("/query")
//    public DefaultCommonResult<PageResultVO<UserInfoVO>> queryUsers(@RequestBody UserParamVO userParamVO) {
//        return DefaultCommonResult.success(ResultCodeEnum.OK, keyCloakApi.getUserList(userParamVO));
//    }

    /**
     * 新增用户信息
     *
     * @param userVO
     */
    @PostMapping("/add")
    public void addUser(String realm,@RequestBody UserVO userVO) {
        userVO = UserVO.builder()
                .username("zhangsan")
                .enabled(true)
                .firstName("名称")
                .lastName("姓")
                .email("email")
                .password("123456")
                .build();
        keyCloakApi.createUser(realm,userVO);
    }

    @GetMapping("/addUserGroup")
    public void addUserGroup() {
        String id = "48340692-b6de-47d1-aee0-b0abbef54a23";
        keyCloakApi.addUserGroup("demoRealm",id,"c0b49540-47b7-45b0-bb40-0a19c92eee11");
    }

    @GetMapping("/deleteUserGroup")
    public void deleteUserGroup() {
        String id = "48340692-b6de-47d1-aee0-b0abbef54a23";
        keyCloakApi.deleteUserGroup("demoRealm",id,"c0b49540-47b7-45b0-bb40-0a19c92eee11");
    }

    @GetMapping("/addUserClientRole")
    public void addUserClientRole() {
        String clentId = "6fdfce20-1f50-43a2-bbbe-62337e35c3d9";
        String userId = "48340692-b6de-47d1-aee0-b0abbef54a23";
        keyCloakApi.addUserClientRole("demoRealm",clentId,userId,"测试角色3");
    }

    @GetMapping("/deleteUserClientRole")
    public void deleteUserClientRole() {
        String clentId = "6fdfce20-1f50-43a2-bbbe-62337e35c3d9";
        String userId = "48340692-b6de-47d1-aee0-b0abbef54a23";
        keyCloakApi.deleteUserClientRole("demoRealm",clentId,userId,"测试角色3");
    }
}
