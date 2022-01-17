package com.qk.dm.reptile.rest;

import com.qk.dm.reptile.client.ClientInfo;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository.DEFAULT_SPRING_SECURITY_CONTEXT_ATTR_NAME;

/**
 * OAuth2认证
 * @author wangzp
 * @date 2022/01/17 12:11
 * @since 1.0.0
 */
@RestController
public class OAuth2ClientController {
    final ClientInfo clientInfo;


    public OAuth2ClientController(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }


    @GetMapping("/auth/current/logout")
    public Mono<Void> logout(ServerHttpResponse response) {
        return Mono.fromRunnable(
                () -> {
                    response.setStatusCode(HttpStatus.FOUND);
                    HttpHeaders headers = response.getHeaders();
                    headers.setLocation(URI.create("/auth/current/logout"));
                });
    }
    /**
     * 退出
     * @param session
     * @param response
     * @return
     */
    @GetMapping("/oauth2/auth/logout")
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
                    headers.setLocation(URI.create(clientInfo.getLoginSuccessUrl()));
                });
    }
}
