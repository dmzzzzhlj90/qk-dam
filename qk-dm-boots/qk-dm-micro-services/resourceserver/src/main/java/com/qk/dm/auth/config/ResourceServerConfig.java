package com.qk.dm.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class ResourceServerConfig {

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.mvcMatcher("/messages/**")
        .authorizeRequests()
        .mvcMatchers("/messages/**")
        .access("hasAuthority('SCOPE_openid')")
        .and()
        .oauth2ResourceServer(
            oauth2 ->
                oauth2.opaqueToken(
                    opaqueToken ->
                        opaqueToken
                            .introspectionUri("https://auth-server:9901/introspect")
                            .introspectionClientCredentials("qk-dam-client", "qk-dam-client-zzr")))
        .oauth2ResourceServer()
        .jwt();
    return http.build();
  }
}
