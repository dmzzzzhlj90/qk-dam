package com.qk.dam.gateway.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author Joe Grandja
 * @since 0.0.1
 */
@RestController
public class MessagesController {
  @GetMapping("/messages")
  public Mono<String[]> getMessages() {
    return Mono.just(new String[] {"Message 1", "Message 2", "Message 3"});
  }

  @GetMapping("/messages/bearer/foo")
  public Mono<Object> foo() {
    return getCurrentUser();
  }
  public Mono<Object> getCurrentUser() {
    return ReactiveSecurityContextHolder.getContext()
            .switchIfEmpty(Mono.error(new IllegalStateException("ReactiveSecurityContext is empty")))
            .map(SecurityContext::getAuthentication)
            .map(Authentication::getPrincipal);
  }
  @GetMapping("/messages/auth/foo")
  public Mono<String> foo(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
    NoOpServerSecurityContextRepository instance = NoOpServerSecurityContextRepository
            .getInstance();
    return Mono.just(principal.getAttribute("sub") + " is the subject");
  }
}
