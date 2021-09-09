package com.qk.dam.auth.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

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
    public Mono getAuthorities() {
        return ReactiveSecurityContextHolder.getContext()
                .switchIfEmpty(Mono.error(new IllegalStateException("ReactiveSecurityContext is empty")))
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getAuthorities);
        //                .map(grantedAuthorities ->
        // grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")));
    }

    /**
     * 查询用户信息
     *
     * @return OidcUserInfo
     */
    @GetMapping("/auth/current/principal/userinfo")
    public Mono<OidcUserInfo> userinfo() {
        return ReactiveSecurityContextHolder.getContext()
                .switchIfEmpty(Mono.error(new IllegalStateException("ReactiveSecurityContext is empty")))
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(w -> (DefaultOidcUser) w.getPrincipal())
                .map(DefaultOidcUser::getUserInfo);
    }

    /**
     * 查询ID token
     *
     * @return OidcIdToken
     */
    @GetMapping("/auth/current/principal/idToken")
    public Mono<OidcIdToken> idToken() {
        return ReactiveSecurityContextHolder.getContext()
                .switchIfEmpty(Mono.error(new IllegalStateException("ReactiveSecurityContext is empty")))
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(w -> (DefaultOidcUser) w.getPrincipal())
                .map(DefaultOidcUser::getIdToken);
    }

    /**
     * 查询认证信息
     *
     * @return getAuthentication
     */
    @GetMapping("/auth/current/credentials")
    public Mono credentials() {
        return ReactiveSecurityContextHolder.getContext()
                .switchIfEmpty(Mono.error(new IllegalStateException("ReactiveSecurityContext is empty")))
                .map(SecurityContext::getAuthentication);
    }

    /**
     * 查询access token
     *
     * @param authorizedClient 认证客户端
     * @return String token
     */
    @GetMapping("/auth/current/accessToken")
    @ResponseBody
    public OAuth2AccessToken accessToken(
            @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
        return authorizedClient.getAccessToken();
    }
}
