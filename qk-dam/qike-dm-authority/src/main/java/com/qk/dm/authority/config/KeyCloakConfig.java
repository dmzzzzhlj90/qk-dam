package com.qk.dm.authority.config;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author shenpj
 * @date 2022/2/21 14:13
 * @since 1.0.0
 */
@Configuration
public class KeyCloakConfig {

//    @Value("${keycloak.auth-server-url}")
    private static String url = "http://172.20.0.9:8080/auth/";

//    @Value("${kc.master.realm.user.name}")
    private static String adminUserName = "demouser";

//    @Value("${kc.master.realm.user.password}")
    private static String adminPassword = "123456";

//    @Value("${kc.master.realm.client.id}")
    private static String clientId = "demoClient";

    private static String TARGET_REALM = "demoRealm";

    private static final String MASTER_REALM = "demoRealm";

    @Bean
    public RealmResource realmResource() {
        Keycloak keycloak = Keycloak.getInstance(url, MASTER_REALM, adminUserName, adminPassword, clientId);
        return keycloak.realm(TARGET_REALM);
    }

}
