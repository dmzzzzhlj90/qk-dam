package com.qk.dam.gateway.config;

import com.qk.dam.gateway.auth.DamAuthorizationManager;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authorization.AuthorizationContext;

@EnableWebSecurity
public class ResourceServerConfig {

  @Bean
  SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    http.authorizeExchange(
        exchanges ->
            exchanges
                .pathMatchers("/**")
                .access(DamAuthorizationManager.authenticated("SCOPE_openid"))
                .anyExchange()
                .authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt());

    return http.build();
  }
}
