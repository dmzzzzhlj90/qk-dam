package com.qk.dam.authorization.access.voter;

import com.nimbusds.jose.shaded.json.JSONArray;
import com.nimbusds.jose.shaded.json.JSONObject;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ObjectUtils;

public abstract class DamVoter {
  /** 资源权限 */
  private static final String RESOURCE_ACCESS = "resource_access";
  /** 资源客户端ID */
  private static final String RESOURCE_CLIENT_ID = "qk-dam";
  /** 资源角色KEY */
  private static final String ROLES = "roles";

  protected static final AntPathMatcher antPathMatcher;

  static {
    antPathMatcher = new AntPathMatcher(":");
    antPathMatcher.setCachePatterns(false);
    antPathMatcher.setCaseSensitive(true);
    antPathMatcher.setTrimTokens(true);
  }

  Collection<String> extractAuthorities(Authentication authentication) {
    if (!ObjectUtils.isEmpty(authentication.getCredentials())){
      Jwt credentials = (Jwt) authentication.getCredentials();
      Map<String, Object> claims = credentials.getClaims();
      JSONObject resourceAccess = (JSONObject) claims.get(RESOURCE_ACCESS);
      JSONObject dam = (JSONObject) resourceAccess.get(RESOURCE_CLIENT_ID);
      JSONArray roles = (JSONArray) dam.get(ROLES);
      return roles.stream().map(String::valueOf).collect(Collectors.toList());
    }
    return Collections.emptyList();
  }
}
