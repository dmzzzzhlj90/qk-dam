package com.qk.mvc.validation.config;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration(proxyBeanMethods = false)
public class MyHttpMessageConvertersConfiguration {

    @Bean
    public HttpMessageConverters customConverters() {
        HttpMessageConverter<?> gsonHttpMessageConverter = new GsonHttpMessageConverter();
        HttpMessageConverter<?> jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        return new HttpMessageConverters(gsonHttpMessageConverter, jackson2HttpMessageConverter);
    }

}