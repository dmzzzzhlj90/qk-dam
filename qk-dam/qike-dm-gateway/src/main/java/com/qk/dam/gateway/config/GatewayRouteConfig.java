package com.qk.dam.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRouteConfig {
  @Bean
  public RouteLocator routes(RouteLocatorBuilder builder) {
    return builder
        .routes()
        .route("dm-datastandards-route", r -> r.path("/dsd/**").uri("lb://dm-datastandards"))
        .route("dm-metadata-route", r -> r.path("/mtd/**").uri("lb://dm-metadata"))
        .build();
  }
}
