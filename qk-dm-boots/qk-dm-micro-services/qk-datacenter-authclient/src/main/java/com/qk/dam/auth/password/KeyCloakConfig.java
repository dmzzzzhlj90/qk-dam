package com.qk.dam.auth.password;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author shenpj
 * @date 2022/2/21 14:13
 * @since 1.0.0
 */
@Configuration
//@ConfigurationProperties(prefix = "spring.security.oauth2.client", ignoreInvalidFields = true)
public class KeyCloakConfig {

    @Value("${spring.security.oauth2.client.provider.qkdatacenter.issuer-uri}")
    private String issuer_uri;

    @Value("${spring.security.oauth2.client.registration.qkdatacenter.client-id}")
    private String clientId;

    private Keycloak keycloak(String username, String password) {
        String url = issuer_uri.substring(0, issuer_uri.lastIndexOf("/"));
        return KeycloakBuilder.builder()
                .serverUrl(url.substring(0, url.lastIndexOf("/")) + "/")
                .realm(issuer_uri.substring(issuer_uri.lastIndexOf("/") + 1))
                .clientId(clientId)
                .username(username)
                .password(password)
                .resteasyClient(
                        new ResteasyClientBuilder()
                                .connectionPoolSize(10).build()
                ).build();
    }

    public String getToken(String userName, String password) {
        return keycloak(userName, password)
                .tokenManager()
                .getAccessToken()
                .getToken();
    }

    public TokenInfoVO getTokenInfo(String userName, String password) {
//        try {
        return TokenInfoVO
                .builder()
                .access_token(
                        getToken(userName, password)
                )
                .build();
//        } catch (NotAuthorizedException n) {
//            throw new BizException("账户或密码错误");
//        } catch (Exception e) {
//            throw new BizException("内部错误，请联系管理员");
//        }
    }
}
