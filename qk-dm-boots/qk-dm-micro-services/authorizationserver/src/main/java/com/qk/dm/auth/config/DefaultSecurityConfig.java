package com.qk.dm.auth.config;

import com.qk.dm.auth.user.CusUserManager;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    http.formLogin(
        login -> {
          try {
            login
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .authorizeRequests()
                .antMatchers("/login.html", "/login")
                .permitAll()
                .anyRequest()
                .authenticated();
          } catch (Exception e) {
            e.printStackTrace();
          }
        });

    http.logout()
        .logoutSuccessHandler(
            (request, response, authentication) -> {
              String r = request.getParameter("r");
              if (r != null) {
                response.sendRedirect(r);
              }
              response.sendRedirect("/");
            });

    http.headers().frameOptions().disable().xssProtection().disable();
    http.csrf().disable();
    http.httpBasic();
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  UserDetailsManager users(DataSource dataSource) {
    var jdbcAuthentication = new CusUserManager(dataSource);
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
