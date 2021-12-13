package com.qk.dam.authorization.access.voter;

import com.qk.dam.authorization.DamAuthzPolicy;
import com.qk.dam.authorization.DamStrategyAuthorizer;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.util.CollectionUtils;

/**
 * 方法访问控制
 *
 * @author daomingzhu
 * @date 20210928
 */
@Slf4j
public class DamAclVoter extends DamVoter implements AccessDecisionVoter<MethodInvocation> {
  @Override
  public boolean supports(ConfigAttribute attribute) {
    return true;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return MethodInvocation.class.isAssignableFrom(clazz);
  }

  @Override
  public int vote(
      Authentication authentication,
      MethodInvocation method,
      Collection<ConfigAttribute> attributes) {
    Collection<String> roleArray = extractAuthorities(authentication);
    if (CollectionUtils.isEmpty(roleArray)) {
      return ACCESS_DENIED;
    }
    List<DamAuthzPolicy.AuthzRole> roles = DamStrategyAuthorizer.getRoles(roleArray);
    List<String> actions =
        roles.stream().flatMap(r -> r.getAction().stream()).distinct().collect(Collectors.toList());
    for (ConfigAttribute attribute : attributes) {
      String targetAction = attribute.getAttribute();
      for (String actionPath : actions) {
        if (antPathMatcher.match(actionPath, targetAction)) {
          return ACCESS_GRANTED;
        }
      }
    }
    return ACCESS_DENIED;
  }
}
