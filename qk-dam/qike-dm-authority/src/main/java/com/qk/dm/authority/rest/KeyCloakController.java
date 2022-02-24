package com.qk.dm.authority.rest;

import com.qk.dm.authority.keycloak.KeyCloakApi;
import com.qk.dm.authority.vo.UserVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
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
    public Object realmList(){
        return keyCloakApi.realmList();
    }

    @GetMapping("/clientList")
    public Object clientList(){
        return keyCloakApi.clientList();
    }

    @GetMapping("/users")
    public Object getUsers(){
        return keyCloakApi.getUserList();
    }

    @GetMapping("/user")
    public Object getUser(String userId){
        return keyCloakApi.userDetail(userId);
    }

    @GetMapping("/groupList")
    public Object groupList(){
        return keyCloakApi.groupList();
    }

    @GetMapping("/group")
    public Object group(String groupId){
        return keyCloakApi.groupDetail(groupId);
    }

    @GetMapping("/clientRoleList")
    public Object clientRoleList(){
        return keyCloakApi.clientRoleList();
    }

    @GetMapping("/clientRoleDetail")
    public Object clientRoleDetail(String roleName){
        return keyCloakApi.clientRoleDetail(roleName);
    }
}
