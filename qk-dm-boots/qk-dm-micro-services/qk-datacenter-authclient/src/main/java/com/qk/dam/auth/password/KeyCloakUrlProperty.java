package com.qk.dam.auth.password;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author shenpj
 * @date 2022/4/19 11:52
 * @since 1.0.0
 */
@Component
public class KeyCloakUrlProperty {
    public static String issuer_uri;
    public static String clientId;
    public static String clientSecret;

    @Value("${spring.security.oauth2.client.provider.qkdatacenter.issuer-uri}")
    public void setIssuer_uri(String issuer_uri) {
        this.issuer_uri = issuer_uri;
    }

    @Value("${spring.security.oauth2.client.registration.qkdatacenter.client-id}")
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Value("${spring.security.oauth2.client.registration.qkdatacenter.client-secret}")
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}
