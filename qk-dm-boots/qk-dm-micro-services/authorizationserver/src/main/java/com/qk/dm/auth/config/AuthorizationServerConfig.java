package com.qk.dm.auth.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.qk.dm.auth.jose.Jwks;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.*;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author Joe Grandja
 * @author Daniel Garnier-Moiroux
 */
@Configuration(proxyBeanMethods = false)
public class AuthorizationServerConfig {
  @Bean
  @Order(Ordered.HIGHEST_PRECEDENCE)
  public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
      throws Exception {
    OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
    return http.formLogin(Customizer.withDefaults()).build();
  }

  /**
   * 注册客户端存储
   *
   * @param jdbcTemplate jdbc
   * @param registeredClients 读取的nacos 配置
   * @return RegisteredClientRepository
   */
  @Bean
  public RegisteredClientRepository registeredClientRepository(
      JdbcTemplate jdbcTemplate, RegisteredClients registeredClients) {
    List<InnerRegisteredClient> clients = registeredClients.getClients();
    List<RegisteredClient> registeredClientList =
        clients.stream()
            .map(
                innerRegisteredClient -> {
                  var builder =
                      RegisteredClient.withId(UUID.randomUUID().toString())
                          .clientId(innerRegisteredClient.getClientId())
                          //
                          // .clientSecret(passwordEncoder.encode(innerRegisteredClient.getClientSecret()))
                          .clientSecret(innerRegisteredClient.getClientSecret())
                          .clientName(innerRegisteredClient.getClientName())
                          .clientSettings(
                              clientSettings -> clientSettings.requireUserConsent(true));

                  innerRegisteredClient
                      .getAuthorizationGrantTypes()
                      .forEach(builder::authorizationGrantType);
                  innerRegisteredClient
                      .getClientAuthenticationMethods()
                      .forEach(builder::clientAuthenticationMethod);
                  innerRegisteredClient.getScopes().forEach(builder::scope);
                  // todo redirectUriTemplate {baseUrl}/login/oauth2/code/{registrationId}
                  innerRegisteredClient.getRedirectUris().forEach(builder::redirectUri);
                  return builder.build();
                })
            .collect(Collectors.toList());
    var jdbcRegisteredClientRepository = new JdbcRegisteredClientRepository(jdbcTemplate);
    for (RegisteredClient registeredClient : registeredClientList) {
      RegisteredClient byId =
          jdbcRegisteredClientRepository.findByClientId(registeredClient.getClientId());
      if (byId == null) {
        jdbcRegisteredClientRepository.save(registeredClient);
      }
    }

    return jdbcRegisteredClientRepository;
  }

  @Bean
  public OAuth2AuthorizationService authorizationService(
      JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
    return new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
  }

  @Bean
  public OAuth2AuthorizationConsentService authorizationConsentService(
      JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
    return new JdbcOAuth2AuthorizationConsentService(jdbcTemplate, registeredClientRepository);
  }

  @Bean
  public JWKSource<SecurityContext> jwkSource() {
    RSAKey rsaKey = Jwks.generateRsa();
    JWKSet jwkSet = new JWKSet(rsaKey);
    return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
  }

  @Bean
  public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
    return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
  }

  @Bean
  public ProviderSettings providerSettings() {
    return new ProviderSettings().issuer("http://auth-server:9901");
  }
}
