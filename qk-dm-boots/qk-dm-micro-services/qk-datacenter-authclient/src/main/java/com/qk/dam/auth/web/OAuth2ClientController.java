package com.qk.dam.auth.web;


import com.qk.dam.auth.client.ClientInfo;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.WebSession;

import java.net.URI;

import static org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository.DEFAULT_SPRING_SECURITY_CONTEXT_ATTR_NAME;

@RestController
@RefreshScope
public class OAuth2ClientController {
    final ClientInfo clientInfo;

    final RestTemplate restTemplate = new RestTemplate();;

    public OAuth2ClientController(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

    @GetMapping("/oauth2/auth/{registerId}")
    public void registerId(
            @PathVariable String registerId,
            ServerHttpResponse response,
            @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
        response.setStatusCode(HttpStatus.FOUND);
        HttpHeaders headers = response.getHeaders();
        headers.setLocation(URI.create(clientInfo.getFrontend()));
    }

    @GetMapping("/oauth2/auth/logout")
    public void logout(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FOUND);
        HttpHeaders headers = response.getHeaders();
        headers.setLocation(URI.create("/auth/current/logout"));
    }

    @GetMapping(path = "/auth/current/logout")
    public void logout(
            WebSession session,
            ServerHttpResponse response,
            @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
        SecurityContext securityContext =
                (SecurityContext) session.getAttributes().get(DEFAULT_SPRING_SECURITY_CONTEXT_ATTR_NAME);
        OidcUser principal = (DefaultOidcUser) securityContext.getAuthentication().getPrincipal();
        OidcIdToken idToken = principal.getIdToken();

        String clientName = authorizedClient.getClientRegistration().getClientName();
        Object forObject = restTemplate.getForObject(clientName + "/protocol/openid-connect/logout?id_token_hint=" + idToken.getTokenValue(), Object.class);


        session.getAttributes().remove(DEFAULT_SPRING_SECURITY_CONTEXT_ATTR_NAME);
        response.setStatusCode(HttpStatus.FOUND);
        HttpHeaders headers = response.getHeaders();
        headers.setLocation(URI.create(clientInfo.getLoginSuccessUrl()));
    }
}
