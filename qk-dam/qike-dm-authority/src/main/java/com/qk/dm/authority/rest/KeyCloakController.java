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

    @GetMapping("/users")
    public Object getUsers(){
        return keyCloakApi.getUserList();
    }

    @GetMapping("/resetUserAttributes")
    public void resetUserAttributes(){
        UserVO build = UserVO.builder().id("fabb1284-7a5b-43d4-bb8d-86ca8720d41c").build();
        keyCloakApi.resetUserAttributes(build);
    }

    @GetMapping("/group")
    public void group(){
        keyCloakApi.groupList();
    }
}
