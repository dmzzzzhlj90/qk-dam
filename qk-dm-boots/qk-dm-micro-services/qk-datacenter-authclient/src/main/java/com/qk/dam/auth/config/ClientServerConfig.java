//package com.qk.dam.auth.config;
//
//import static org.springframework.security.config.Customizer.withDefaults;
//
//import com.qk.dam.auth.client.ClientInfo;
//import java.net.URISyntaxException;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//import org.springframework.security.web.server.authentication.logout.DelegatingServerLogoutHandler;
//import org.springframework.security.web.server.authentication.logout.HeaderWriterServerLogoutHandler;
//import org.springframework.security.web.server.authentication.logout.SecurityContextServerLogoutHandler;
//import org.springframework.security.web.server.authentication.logout.ServerLogoutHandler;
//import org.springframework.security.web.server.header.ClearSiteDataServerHttpHeadersWriter;
//
//@Configuration
//@EnableWebSecurity
//public class ClientServerConfig extends WebSecurityConfigurerAdapter {
//  final ClientInfo clientInfo;
//
//  public ClientServerConfig(ClientInfo clientInfo) {
//    this.clientInfo = clientInfo;
//  }
//
//  @Bean
//  SecurityWebFilterChain springSecurityFilterChain(
//      ServerHttpSecurity http, ReactiveClientRegistrationRepository clientRegistrationRepository)
//      throws URISyntaxException {
//
//    http.authorizeExchange(
//        exchanges ->
//            exchanges
//                .pathMatchers("/**")
//                .hasAuthority("SCOPE_openid")
//                .anyExchange()
//                .authenticated());
//
//    // oauth 登录 客户端配置
//    http.oauth2Login(withDefaults()).oauth2Client(withDefaults());
//
//    ServerLogoutHandler securityContext = new SecurityContextServerLogoutHandler();
//    ClearSiteDataServerHttpHeadersWriter writer =
//        new ClearSiteDataServerHttpHeadersWriter(
//            ClearSiteDataServerHttpHeadersWriter.Directive.ALL);
//    ServerLogoutHandler clearSiteData = new HeaderWriterServerLogoutHandler(writer);
//    DelegatingServerLogoutHandler logoutHandler =
//        new DelegatingServerLogoutHandler(securityContext, clearSiteData);
//
//    http.logout((o) -> o.logoutUrl("/logout").logoutHandler(logoutHandler));
//    http.csrf().disable();
//    return http.build();
//  }
//}
