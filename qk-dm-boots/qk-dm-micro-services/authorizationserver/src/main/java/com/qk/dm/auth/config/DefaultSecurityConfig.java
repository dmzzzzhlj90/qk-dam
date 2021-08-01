package com.qk.dm.auth.config;

import static org.springframework.security.config.Customizer.withDefaults;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

/**
 * 默认的安全配置
 *
 * @author Joe Grandja
 */
@EnableWebSecurity
public class DefaultSecurityConfig {
  @Bean
  SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
        .formLogin(withDefaults());

    http.logout().logoutSuccessHandler(new LogoutSuccessHandler(){

      @Override
      public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String r = request.getParameter("r");
        if (r!=null){
          response.sendRedirect(r);
        }

        response.sendRedirect("/");
        return;
      }
    });
    http.headers().frameOptions().disable().xssProtection().disable();
    http.csrf().disable();
    http.httpBasic().disable();
    //        http.formLogin().disable();
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
