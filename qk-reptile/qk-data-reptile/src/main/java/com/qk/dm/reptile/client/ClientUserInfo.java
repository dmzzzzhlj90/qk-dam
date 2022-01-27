package com.qk.dm.reptile.client;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import java.util.Objects;

public class ClientUserInfo {
    
    private static DefaultOidcUser getUserInfo(){
        return (DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    }
    public static String getUserName(){
        return Objects.requireNonNullElse(getUserInfo().getClaims().get("preferred_username"),"").toString();
    }

}
