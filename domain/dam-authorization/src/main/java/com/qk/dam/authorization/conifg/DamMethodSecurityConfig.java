package com.qk.dam.authorization.conifg;

import com.qk.dam.authorization.access.DamAclDecisionManager;
import com.qk.dam.authorization.access.DamPostAnnotationSecurityMetadataSource;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.method.DelegatingMethodSecurityMetadataSource;
import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration(proxyBeanMethods = false)
@EnableAspectJAutoProxy
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class DamMethodSecurityConfig extends GlobalMethodSecurityConfiguration {
  @Bean
  public MethodSecurityMetadataSource methodSecurityMetadataSource() {
    List<MethodSecurityMetadataSource> sources = new ArrayList<>();
    sources.add(new DamPostAnnotationSecurityMetadataSource());
    return new DelegatingMethodSecurityMetadataSource(sources);
  }

  @Override
  protected AccessDecisionManager accessDecisionManager() {
    return new DamAclDecisionManager();
  }
}
