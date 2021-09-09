package com.qk.dam.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication
public class DataCenterAuthClient {
    public static void main(String[] args) {
        SpringApplication.run(DataCenterAuthClient.class, args);
    }
}
