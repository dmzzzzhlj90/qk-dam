package com.qk.dm.auth.config;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 默认的安全配置
 *
 * @author Joe Grandja
 */
@EnableWebSecurity
public class DefaultSecurityConfig {
  @Bean
  SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/web/**")
        .permitAll() // 允许/web路径下的url，这里可以自己根据需求定制
        .anyRequest()
        .authenticated() // 操作必须是已登录状态
        .and()
        .formLogin()
        .loginPage("/authserver/login") // 跳转自己定制的登录界面
        .permitAll() // 自定义登录页面权限放开
        .and()
        .csrf()
        .disable() // 跨站请求伪造防护功能，这个先不用管，直接禁用了
        .httpBasic();
    //    http.headers().frameOptions().disable().xssProtection().disable();
    //    http.csrf().disable();
    //    http.httpBasic().disable();
    //    http.formLogin().disable();
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  UserDetailsManager users(DataSource dataSource) {
    var jdbcAuthentication = new JdbcUserDetailsManager(dataSource);
    var passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    UserDetails admin =
        User.builder()
            .username("admin")
            .password(passwordEncoder.encode("zhudao123"))
            .roles("USER", "ADMIN")
            .build();
    if (!jdbcAuthentication.userExists(admin.getUsername())) {
      jdbcAuthentication.createUser(admin);
    }

    return jdbcAuthentication;
  }
}
