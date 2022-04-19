package com.qk.mvc.validation.config;

import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class MvcConfiguration {

  //    @Bean
  //    public WebMvcConfigurer corsConfigurer() {
  //        return new WebMvcConfigurer() {
  //            @Override
  //            public void configurePathMatch(PathMatchConfigurer configurer) {
  //                configurer
  //                        .addPathPrefix("/api",(cls) -> true);
  //            }
  //            @Override
  //            public void addCorsMappings(CorsRegistry registry) {
  //                registry.addMapping("/api/**");
  //            }
  //
  //        };
  //    }

}
