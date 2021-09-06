package com.qk.dam.gateway.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authorization.AuthenticatedReactiveAuthorizationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.DelegatingServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.HeaderWriterServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.SecurityContextServerLogoutHandler;
import org.springframework.security.web.server.authentication.logout.ServerLogoutHandler;
import org.springframework.security.web.server.header.ClearSiteDataServerHttpHeadersWriter;

@EnableWebSecurity
public class ClientServerConfig {

  @Bean
  SecurityWebFilterChain springSecurityFilterChain(
      ServerHttpSecurity http, ReactiveClientRegistrationRepository clientRegistrationRepository)
      throws URISyntaxException {

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

    // oauth 登录 客户端配置
    http.oauth2Login(withDefaults()).oauth2Client(withDefaults());

    ServerLogoutHandler securityContext = new SecurityContextServerLogoutHandler();
    ClearSiteDataServerHttpHeadersWriter writer =
        new ClearSiteDataServerHttpHeadersWriter(
            ClearSiteDataServerHttpHeadersWriter.Directive.ALL);
    ServerLogoutHandler clearSiteData = new HeaderWriterServerLogoutHandler(writer);
    DelegatingServerLogoutHandler logoutHandler =
        new DelegatingServerLogoutHandler(securityContext, clearSiteData);
    OidcClientInitiatedServerLogoutSuccessHandler oidcClientInitiatedServerLogoutSuccessHandler =
        new OidcClientInitiatedServerLogoutSuccessHandler(clientRegistrationRepository);
//    oidcClientInitiatedServerLogoutSuccessHandler.setLogoutSuccessUrl(
//        new URI("http://auth-server:9901/logout?r=http://auth-client:8780/access/token"));

    http.logout(
        (o) ->
            o.logoutUrl("/logout")
                .logoutSuccessHandler(oidcClientInitiatedServerLogoutSuccessHandler)
                .logoutHandler(logoutHandler));
    http.csrf().disable();
    return http.build();
  }
}
