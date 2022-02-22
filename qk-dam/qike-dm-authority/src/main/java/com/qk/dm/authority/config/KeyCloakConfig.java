package com.qk.dm.authority.config;

import org.keycloak.admin.client.Keycloak;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author shenpj
 * @date 2022/2/21 14:13
 * @since 1.0.0
 */
@Configuration
public class KeyCloakConfig {

    @Value("${keycloak.url}")
    private String url;

    @Value("${keycloak.master.realm.username}")
    private String userName;

    @Value("${keycloak.master.realm.password}")
    private String password;

    @Value("${keycloak.master.realm.client_id}")
    private String clientId;

    @Value("${keycloak.master.realm.name}")
    private String masterRealm;

    @Bean
    public Keycloak keycloak() {
        return Keycloak.getInstance(url, masterRealm, userName, password, clientId);
    }

}
