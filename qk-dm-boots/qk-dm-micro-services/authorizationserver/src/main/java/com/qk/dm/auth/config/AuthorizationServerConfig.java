package com.qk.dm.auth.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.qk.dm.auth.jose.ClientKeyService;
import com.qk.dm.auth.jose.Jwks;
import com.qk.dm.auth.pojo.InnerRegisteredClient;
import com.qk.dm.auth.pojo.RegisteredClients;
import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
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
                          .clientSecret(innerRegisteredClient.getClientSecret())
                          .clientName(innerRegisteredClient.getClientName())
                          .tokenSettings(
                              (tokenSettings ->
                                  tokenSettings
                                      .accessTokenTimeToLive(
                                          Duration.ofMinutes(
                                              innerRegisteredClient.getAccessTokenTimeToLive()))
                                      .refreshTokenTimeToLive(
                                          Duration.ofMinutes(
                                              innerRegisteredClient.getRefreshTokenTimeToLive()))))
                          .clientSettings(
                              clientSettings -> clientSettings.requireUserConsent(false));

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
  public JWKSource<SecurityContext> jwkSource(
      RegisteredClients registeredClients, ClientKeyService clientKeyService) {

    List<JWK> rsaKeyList =
        registeredClients.getClients().stream()
            .map(client -> getRsaKey(clientKeyService, client))
            .collect(Collectors.toList());
    // TODO 多个jwks不支持 需要解决
    JWKSet jwkSet = new JWKSet(rsaKeyList.get(0));

    return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
  }

  /**
   * 获取rsa key
   *
   * @param clientKeyService
   * @param client
   * @return
   */
  private RSAKey getRsaKey(ClientKeyService clientKeyService, InnerRegisteredClient client) {
    InnerRegisteredClient findClient = clientKeyService.innerRegisteredClient(client.getClientId());
    if (findClient != null) {
      String privateEncoded = findClient.getPrivateEncoded();
      String publicEncoded = findClient.getPublicEncoded();

      return new RSAKey.Builder(Jwks.getPublicKey(publicEncoded))
          .privateKey(Jwks.getPrivateKey(privateEncoded))
          .keyID(client.getClientId())
          .build();
    }
    RSAKey rsaKey = Jwks.generateRsa(client.getClientId());
    try {
      String privateEncoded = Jwks.getPrivateEncoded(rsaKey);
      String publicEncoded = Jwks.getPublicEncoded(rsaKey);
      client.setPrivateEncoded(privateEncoded);
      client.setPublicEncoded(publicEncoded);
      clientKeyService.insertClientKey(client);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return rsaKey;
  }

  @Bean
  public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
    return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
  }

  @Bean
  public ProviderSettings providerSettings(RegisteredClients registeredClients) {
    return new ProviderSettings().issuer(registeredClients.getIssuer());
  }
}
