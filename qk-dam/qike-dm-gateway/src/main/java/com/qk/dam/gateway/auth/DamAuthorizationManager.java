package com.qk.dam.gateway.auth;

import java.util.Collection;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import reactor.core.publisher.Mono;

/**
 * 数据资产授权验证
 *
 * @param <T> 参数类型
 */
public class DamAuthorizationManager<T extends AuthorizationContext>
    implements ReactiveAuthorizationManager<T> {
  DamAuthorizationManager() {}

  @Override
  public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, T ac) {
    return authentication
        .filter(this::isNotAnonymous)
        .map(this::getAuthorizationDecision)
        .defaultIfEmpty(new AuthorizationDecision(false));
  }

  private AuthorizationDecision getAuthorizationDecision(Authentication authentication) {
    return new AuthorizationDecision(authentication.isAuthenticated());
  }

  private boolean isNotAnonymous(Authentication authentication) {
    Object principal = authentication.getPrincipal();
    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    Object credentials = authentication.getCredentials();
    Object details = authentication.getDetails();
    return true;
  }

  public static <T extends AuthorizationContext> DamAuthorizationManager<T> authenticated() {
    return new DamAuthorizationManager<>();
  }
}
