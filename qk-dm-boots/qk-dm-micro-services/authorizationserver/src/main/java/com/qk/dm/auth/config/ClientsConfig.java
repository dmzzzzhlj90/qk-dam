package com.qk.dm.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientsConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.register", ignoreInvalidFields = true)
    public RegisteredClients registeredClients() {
        return new RegisteredClients();
    }
}
