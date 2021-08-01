package com.qk.dam.resourceserver.filter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.NimbusOpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Map;

public class UserInfoOpaqueTokenIntrospector implements OpaqueTokenIntrospector {
    private final OpaqueTokenIntrospector delegate =
            new NimbusOpaqueTokenIntrospector("https://auth-server:9901/oauth2/introspect", "client", "secret");
    private final RestTemplate rest =  new RestTemplate();

    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        OAuth2AuthenticatedPrincipal authorized = this.delegate.introspect(token);
        return makeUserInfoRequest(token,authorized);
    }

    private OAuth2AuthenticatedPrincipal makeUserInfoRequest(String token,OAuth2AuthenticatedPrincipal oAuth2AuthenticatedPrincipal) {
        Map<String, Object> result = rest(token).getForObject("", Map.class);
        return new DefaultOAuth2AuthenticatedPrincipal(result.get("sub").toString(),
                oAuth2AuthenticatedPrincipal.getAttributes(),
                (Collection<GrantedAuthority>) oAuth2AuthenticatedPrincipal.getAuthorities());
    }

    RestTemplate rest(String token) {
        RestTemplate rest = new RestTemplate();
        rest.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().setBearerAuth(token);
            return execution.execute(request, body);
        });
        return rest;
    }
}