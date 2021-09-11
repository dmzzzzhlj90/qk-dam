package com.qk.dam.auth.web;

import static org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository.DEFAULT_SPRING_SECURITY_CONTEXT_ATTR_NAME;

import com.qk.dam.auth.client.ClientInfo;
import java.net.URI;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

@RestController
@RefreshScope
public class OAuth2ClientController {
  final ClientInfo clientInfo;

  public OAuth2ClientController(ClientInfo clientInfo) {
    this.clientInfo = clientInfo;
  }

  @GetMapping("/oauth2/auth/{registerId}")
  public Mono<Void> registerId(
      @PathVariable String registerId,
      ServerHttpResponse response,
      @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
    return Mono.fromRunnable(
        () -> {
          response.setStatusCode(HttpStatus.FOUND);
          HttpHeaders headers = response.getHeaders();
          headers.setLocation(URI.create(clientInfo.getFrontend()));
        });
  }

  @GetMapping("/oauth2/auth/logout")
  public Mono<Void> logout(ServerHttpResponse response) {
    return Mono.fromRunnable(
        () -> {
          response.setStatusCode(HttpStatus.FOUND);
          HttpHeaders headers = response.getHeaders();
          headers.setLocation(URI.create("/auth/current/logout"));
        });
  }

  @GetMapping(path = "/auth/current/logout")
  public Mono<Void> logout(
      WebSession session,
      ServerHttpResponse response,
      @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
    SecurityContext securityContext =
        (SecurityContext) session.getAttributes().get(DEFAULT_SPRING_SECURITY_CONTEXT_ATTR_NAME);
    OidcUser principal = (DefaultOidcUser) securityContext.getAuthentication().getPrincipal();
    OidcIdToken idToken = principal.getIdToken();

    String clientName = authorizedClient.getClientRegistration().getClientName();

    WebClient webClient = WebClient.create(clientName + "/protocol/openid-connect/logout");
    Object block =
        webClient
            .get()
            .uri(
                uriBuilder ->
                    uriBuilder.queryParam("id_token_hint", idToken.getTokenValue()).build())
            .retrieve()
            .bodyToMono(Object.class)
            .block();

    session.getAttributes().remove(DEFAULT_SPRING_SECURITY_CONTEXT_ATTR_NAME);
    return Mono.fromRunnable(
        () -> {
          response.setStatusCode(HttpStatus.FOUND);
          HttpHeaders headers = response.getHeaders();
          headers.setLocation(URI.create(clientInfo.getFrontend()));
        });
  }
}
