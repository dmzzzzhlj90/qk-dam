package com.qk.dam.resourceserver.conf;

import com.qk.dam.resourceserver.filter.UserInfoOpaqueTokenIntrospector;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author Joe Grandja
 * @since 0.0.1
 */
@EnableWebSecurity
public class ResourceServerConfig {

	// @formatter:off
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.mvcMatcher("/messages/**")
				.authorizeRequests()
					.mvcMatchers("/messages/**").access("hasAuthority('SCOPE_openid')")
					.and()
			.oauth2ResourceServer()
				.jwt();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER).disable();
		http.csrf().disable();
		return http.build();
	}
	// @formatter:on
	@Bean
	OpaqueTokenIntrospector introspector() {
		return new UserInfoOpaqueTokenIntrospector();
	}
}