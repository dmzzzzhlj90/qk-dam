package com.qk.dam.authorization.access;

import com.qk.dam.authorization.access.voter.DamAclVoter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;

public class DamAclDecisionManager extends AbstractAccessDecisionManager {
  private static final List<AccessDecisionVoter<?>> DECISION_VOTERS = new ArrayList<>();

  static {
    DECISION_VOTERS.add(new DamAclVoter());
  }

  public DamAclDecisionManager() {
    super(DECISION_VOTERS);
  }

  @Override
  public void decide(
      Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
      throws AccessDeniedException, InsufficientAuthenticationException {
    int deny = 0;
    for (AccessDecisionVoter accessDecisionVoter : getDecisionVoters()) {
      int result = accessDecisionVoter.vote(authentication, object, configAttributes);
      switch (result) {
        case AccessDecisionVoter.ACCESS_GRANTED:
          return;
        case AccessDecisionVoter.ACCESS_DENIED:
          deny++;
          break;
        default:
          break;
      }
    }
    if (deny > 0) {
      throw new AccessDeniedException("请求被拒绝");
    }
  }
}
