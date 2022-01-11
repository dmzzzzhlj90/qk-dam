package com.qk.dm.reptile.config;

import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.CompositeLogoutHandler;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Order(9)
public class OAuth2ClientSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorize -> authorize.anyRequest().authenticated())
                .oauth2Login(withDefaults())
                .oauth2Client(withDefaults());
        CompositeLogoutHandler compositeLogoutHandler =
                new CompositeLogoutHandler(new CookieClearingLogoutHandler("JSESSIONID"));
        http.logout((o) -> o.logoutUrl("/logout").addLogoutHandler(compositeLogoutHandler));
        http.csrf().disable();
    }
}
