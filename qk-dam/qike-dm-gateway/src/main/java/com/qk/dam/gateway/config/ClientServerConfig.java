package com.qk.dam.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebSecurity
public class ClientServerConfig {

  @Bean
  SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

    http.authorizeExchange(
        exchanges ->
            exchanges
                .pathMatchers("/**")
                .hasAuthority("SCOPE_openid")
                .anyExchange()
                .authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt());

    return http.build();
  }
}
