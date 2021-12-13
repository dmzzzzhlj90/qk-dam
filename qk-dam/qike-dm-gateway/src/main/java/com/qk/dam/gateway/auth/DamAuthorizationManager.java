package com.qk.dam.gateway.auth;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 数据资产授权验证
 *
 * @param <T> 参数类型
 */
public class DamAuthorizationManager<T extends AuthorizationContext>
    implements ReactiveAuthorizationManager<T> {
  private final List<String> authorities;

  DamAuthorizationManager(String... authorities) {
    this.authorities = Arrays.asList(authorities);
  }

  @Override
  public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, T ac) {
    ServerWebExchange exchange = ac.getExchange();
    ServerHttpRequest request = exchange.getRequest();
    return authentication
        .filter((a) -> a.isAuthenticated())
        .flatMapIterable(Authentication::getAuthorities)
        .map(grantedAuthority -> grantedAuthority.getAuthority())
        .any(c -> this.authorities.contains(c))
        .map(AuthorizationDecision::new)
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

  public static <T extends AuthorizationContext> DamAuthorizationManager<T> authenticated(
      String... authorities) {
    return new DamAuthorizationManager<>(authorities);
  }
}
