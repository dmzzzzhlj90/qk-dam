package com.qk.dam.authorization.access.voter;

import java.util.Collection;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.util.CollectionUtils;

/**
 * 角色访问控制
 *
 * @author daomingzhu
 * @date 20210928
 */
public class DamRoleVoter extends DamVoter implements AccessDecisionVoter<Object> {
  private String rolePrefix = "DAM_";

  public String getRolePrefix() {
    return this.rolePrefix;
  }

  public void setRolePrefix(String rolePrefix) {
    this.rolePrefix = rolePrefix;
  }

  @Override
  public boolean supports(ConfigAttribute attribute) {
    return attribute != null && attribute.toString() != null;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return true;
  }

  @Override
  public int vote(
      Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
    if (authentication == null) {
      return ACCESS_DENIED;
    }
    int result = ACCESS_ABSTAIN;
    Collection<String> authorities = extractAuthorities(authentication);
    if (CollectionUtils.isEmpty(authorities)){
      return ACCESS_DENIED;
    }
    for (ConfigAttribute attribute : attributes) {
      if (this.supports(attribute)) {
        result = ACCESS_DENIED;
        // Attempt to find a matching granted authority
        for (String authority : authorities) {
          if (authority.startsWith(rolePrefix) && attribute.toString().equals(authority)) {
            return ACCESS_GRANTED;
          }
        }
      }
    }
    return result;
  }
}
