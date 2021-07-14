package com.qk.dam.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.authentication.JwtIssuerReactiveAuthenticationManagerResolver;
import org.springframework.security.oauth2.server.resource.web.server.ServerBearerTokenAuthenticationConverter;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.*;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;


/**
 * @author daomingzhu
 */
@Slf4j
public class JWTokenFilter extends AuthenticationWebFilter {
    JwtIssuerReactiveAuthenticationManagerResolver authenticationManagerResolver = new JwtIssuerReactiveAuthenticationManagerResolver
            ("http://auth-server:9901");
    private ServerWebExchangeMatcher requiresAuthenticationMatcher = ServerWebExchangeMatchers.anyExchange();
    private ServerAuthenticationConverter authenticationConverter = new ServerBearerTokenAuthenticationConverter();
    private ServerAuthenticationFailureHandler authenticationFailureHandler = new ServerAuthenticationEntryPointFailureHandler(
            new CusBearerTokenServerAuthEntryPoint());

    public JWTokenFilter(ReactiveAuthenticationManager authenticationManager) {
        super(authenticationManager);
    }



    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return requiresAuthenticationMatcher.matches(exchange).filter((matchResult) -> matchResult.isMatch())
                .flatMap((matchResult) -> this.authenticationConverter.convert(exchange))
                .switchIfEmpty(chain.filter(exchange).then(Mono.empty()))
                .flatMap((token) ->
                        authenticate(exchange, chain, token))
                .onErrorResume(AuthenticationException.class, (ex) -> this.authenticationFailureHandler
                        .onAuthenticationFailure(new WebFilterExchange(exchange, chain), ex));
    }
    private Mono<Void> authenticate(ServerWebExchange exchange, WebFilterChain chain, Authentication token) {
        return this.authenticationManagerResolver.resolve(exchange)
                .flatMap((authenticationManager) -> authenticationManager.authenticate(token))
                .switchIfEmpty(Mono.defer(
                        () ->
                                Mono.error(new IllegalStateException("No provider found for " + token.getClass()))))
                .flatMap((authentication) -> onAuthenticationSuccess(authentication,
                        new WebFilterExchange(exchange, chain)))
                .doOnError(AuthenticationException.class,
                        (ex) ->
                                log.info("jwt认证失败: "+ex.getMessage()));
    }
}
