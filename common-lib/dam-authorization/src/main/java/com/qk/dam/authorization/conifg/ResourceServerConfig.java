package com.qk.dam.authorization.conifg;

import com.qk.dam.authorization.DamAuthzPolicy;
import com.qk.dam.authorization.DamStrategyAuthorizer;
import com.qk.dam.authorization.access.DamAccessDecisionManager;
import java.util.List;
import java.util.Objects;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.util.ObjectUtils;

@EnableWebSecurity
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    List<DamAuthzPolicy.AuthzRole> roles = DamStrategyAuthorizer.getRoles();
    http.authorizeRequests(
            authorize -> {
              Objects.requireNonNull(roles)
                  .forEach(
                      authzRole -> {
                        // 角色匹配路径权限
                        String roleName = authzRole.getRoleName();
                        List<String> mvcMatchers = authzRole.getMvcMatchers();
                        if (!ObjectUtils.isEmpty(mvcMatchers)) {
                          mvcMatchers.forEach(
                              mvcMatch -> authorize.mvcMatchers(mvcMatch).access(roleName));
                        } else {
                          // 默认角色可以访问所有
                          //                            authorize
                          //                                    .mvcMatchers("/**")
                          //                                    .access(roleName);
                        }
                      });
              authorize
                  .accessDecisionManager(new DamAccessDecisionManager())
                  .anyRequest()
                  .authenticated();
            })
        .oauth2ResourceServer()
        .jwt();
  }
}
