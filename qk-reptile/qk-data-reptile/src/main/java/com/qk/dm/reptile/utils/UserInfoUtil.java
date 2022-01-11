package com.qk.dm.reptile.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

public class UserInfoUtil {

    private static DefaultOidcUser getUserInfo(){
        return (DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    }
    public static Object getUserName(){

        return getUserInfo().getClaims().get("preferred_username");
    }
}
