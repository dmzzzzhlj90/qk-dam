package com.qk.dam.authorization.conifg;

import com.qk.dam.authorization.access.DamAclDecisionManager;
import com.qk.dam.authorization.access.DamPostAnnotationSecurityMetadataSource;
import com.qk.dam.authorization.access.voter.DamAclVoter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Role;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.annotation.SecuredAnnotationSecurityMetadataSource;
import org.springframework.security.access.expression.method.ExpressionBasedPreInvocationAdvice;
import org.springframework.security.access.method.DelegatingMethodSecurityMetadataSource;
import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.security.access.prepost.PreInvocationAuthorizationAdviceVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.core.GrantedAuthorityDefaults;

import java.util.ArrayList;
import java.util.List;

@Configuration(proxyBeanMethods = false)
@EnableAspectJAutoProxy
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled=true)
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
