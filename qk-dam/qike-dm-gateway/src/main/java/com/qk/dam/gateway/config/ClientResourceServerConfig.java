package com.qk.dam.gateway.config;

import static org.springframework.security.config.Customizer.withDefaults;

import com.qk.dam.gateway.filter.CusBearerTokenAuthenticationEntryPoint;
import com.qk.dam.gateway.filter.JWTokenFilter;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authorization.AuthenticatedReactiveAuthorizationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtReactiveAuthenticationManager;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@EnableWebSecurity
public class ClientResourceServerConfig {

  @Bean
  SecurityWebFilterChain springSecurityFilterChain(
      ServerHttpSecurity http, ReactiveJwtDecoder jwtDecoder) {
    // jwt 认证管理器个性化配置
    var jwtReactiveAuthenticationManager = new JwtReactiveAuthenticationManager(jwtDecoder);
    http.addFilterBefore(
        new JWTokenFilter(jwtReactiveAuthenticationManager),
        SecurityWebFiltersOrder.AUTHENTICATION);
    http.authorizeExchange(
        exchanges ->
            exchanges
                .pathMatchers("/messages/**")
                .hasAuthority("SCOPE_openid")
                .anyExchange()
                .access(AuthenticatedReactiveAuthorizationManager.authenticated()));

    // 资源服务配置
    http.oauth2ResourceServer(
        oauth2 ->
            oauth2
                .authenticationEntryPoint(new CusBearerTokenAuthenticationEntryPoint())
                //                        .accessDeniedHandler((exchange, denied) -> {
                //                            //TODO 动态权限拒绝访问策略
                //                            return exchange.getResponse().setComplete();
                //                        })
                .jwt(withDefaults())
        //                        .jwt(jwt ->
        // jwt.jwtAuthenticationConverter(grantedAuthoritiesExtractor()))
        );

    // oauth 登录 客户端配置
    http.oauth2Login(withDefaults()).oauth2Client(withDefaults());

    return http.build();
  }

  Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor() {
    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new GrantedAuthoritiesExtractor());
    return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
  }

  static class GrantedAuthoritiesExtractor implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
      Collection<?> authorities =
          (Collection<?>) jwt.getClaims().getOrDefault("mycustomclaim", Collections.emptyList());

      return authorities.stream()
          .map(Object::toString)
          .map(SimpleGrantedAuthority::new)
          .collect(Collectors.toList());
    }
  }
}
