package com.qk.dam.gateway.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author Joe Grandja
 * @since 0.0.1
 */
@RestController
public class UserInfoController {
  @GetMapping("/current/user")
  public Mono<Object> getCurrentUser() {
    return ReactiveSecurityContextHolder.getContext()
        .switchIfEmpty(Mono.error(new IllegalStateException("ReactiveSecurityContext is empty")))
        .map(SecurityContext::getAuthentication)
        .map(Authentication::getPrincipal);
  }
}
