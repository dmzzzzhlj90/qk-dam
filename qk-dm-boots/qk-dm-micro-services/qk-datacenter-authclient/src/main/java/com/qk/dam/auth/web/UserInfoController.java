package com.qk.dam.auth.web;

import com.google.common.collect.Maps;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Map;

/**
 * 用户信息
 *
 * @author Joe Grandja
 * @since 0.0.1
 */
@RestController
public class UserInfoController {
  /**
   * 查询认证范围
   *
   * @return Mono
   */
  @GetMapping("/auth/current/authorities")
  public Collection<? extends GrantedAuthority> getAuthorities() {

    return SecurityContextHolder.getContext()
            .getAuthentication()
            .getAuthorities();
  }

  /**
   * 查询用户信息
   *
   * @return OidcUserInfo
   */
  @GetMapping("/auth/current/principal/userinfo")
  public OidcUserInfo userinfo() {
    DefaultOidcUser principal = (DefaultOidcUser)SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
    return principal.getUserInfo();
  }


  /**
   * 查询access token
   *
   * @param authorizedClient 认证客户端
   * @return String token
   */
  @GetMapping("/auth/current/accessToken")
  @ResponseBody
  public Map<String, Object> accessToken(
      @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
    Map<String, Object> objectHashMap = Maps.newHashMap();
    objectHashMap.put("userinfo", userinfo());
    objectHashMap.put("accessToken", authorizedClient.getAccessToken().getTokenValue());
    return objectHashMap;
  }
}
