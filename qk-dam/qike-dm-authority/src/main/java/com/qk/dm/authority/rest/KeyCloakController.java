package com.qk.dm.authority.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
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
@RequestMapping("/user")
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
    public Object clientList() {
        return keyCloakApi.clientList();
    }

    @GetMapping("/users")
    public Object getUsers() {
        return keyCloakApi.getUserList();
    }

    @GetMapping("/user")
    public Object getUser(String userId) {
        return keyCloakApi.userDetail(userId);
    }

    @GetMapping("/groupList")
    public Object groupList() {
        return keyCloakApi.groupList();
    }

    @GetMapping("/group")
    public Object group(String groupId) {
        return keyCloakApi.groupDetail(groupId);
    }

    @GetMapping("/clientRoleList")
    public Object clientRoleList() {
        return keyCloakApi.clientRoleList();
    }

    @GetMapping("/clientRoleDetail")
    public Object clientRoleDetail(String roleName) {
        return keyCloakApi.clientRoleDetail(roleName);
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

    /**
     * 新增用户信息
     *
     * @param userVO
     */
    @PostMapping("/add")
    public void addUser(@RequestBody UserVO userVO) {
        keyCloakApi.createUser(userVO);
    }
}
