package com.qk.dam.auth.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
public class OAuth2LoginSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests(authorize -> authorize
                    //开放请求
                    .antMatchers("/auth/*")
                    //其他请求需要登陆
                .authenticated()
            )
            .oauth2Login(withDefaults());
        //开启登陆页
        http.formLogin()
                //登陆页面
                .loginPage("/index.html")
                //登陆地址
                .failureUrl("/auth/password/login")
                //登陆后跳转地址
                .defaultSuccessUrl("/")
                .permitAll();
        http.csrf().disable();
    }
}