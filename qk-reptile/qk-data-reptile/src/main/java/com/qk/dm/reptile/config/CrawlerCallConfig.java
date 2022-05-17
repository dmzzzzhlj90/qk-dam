package com.qk.dm.reptile.config;

import com.qk.dm.reptile.client.CrawlerCall;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CrawlerCallConfig {
    @Bean
    @ConfigurationProperties(prefix = "crawler-call", ignoreInvalidFields = true)
    public CrawlerCall getCrawlerCall(){
        return new CrawlerCall();
    }
}
