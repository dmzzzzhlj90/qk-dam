package com.qk.dm.reptile.client;

import com.nimbusds.jose.shaded.json.JSONArray;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.qk.dm.reptile.constant.RptRolesConstant;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import java.util.Objects;

public class ClientUserInfo {
    
    private static DefaultOidcUser getUserInfo(){
        return (DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    }
    public static String getUserName(){
        return Objects.requireNonNullElse(getUserInfo().getClaims().get("preferred_username"),"").toString();
    }

    /**
     * 菜单权限
     * @param jwtDecoder
     * @param authorizedClient
     * @return
     */
    public static Boolean menuPermissions(JwtDecoder jwtDecoder,OAuth2AuthorizedClient authorizedClient){
        JSONArray roles = judgeRoles(jwtDecoder,authorizedClient);
        return roles.contains(RptRolesConstant.DAM_ADMIN);
    }

    /**
     * 按钮权限
     * @param jwtDecoder
     * @param authorizedClient
     * @return
     */
    public static Boolean buttonPermissions(JwtDecoder jwtDecoder,OAuth2AuthorizedClient authorizedClient) {
        JSONArray roles = judgeRoles(jwtDecoder,authorizedClient);
        return roles.contains(RptRolesConstant.DAM_ADMIN)||roles.contains(RptRolesConstant.GROUP_ADMIN);
    }

    private static JSONArray judgeRoles(JwtDecoder jwtDecoder,OAuth2AuthorizedClient authorizedClient){
        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
        JSONObject resource_access = (JSONObject) jwtDecoder.decode(accessToken.getTokenValue()).getClaims().get("resource_access");
        JSONObject reptile = (JSONObject)resource_access.get(RptRolesConstant.REPTILE);
        if(Objects.isNull(reptile)||Objects.isNull(reptile.get(RptRolesConstant.ROLES))){
            return new JSONArray();
        }
        return (JSONArray)reptile.get(RptRolesConstant.ROLES);
    }

}
