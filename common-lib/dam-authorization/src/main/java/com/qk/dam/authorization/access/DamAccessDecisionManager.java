package com.qk.dam.authorization.access;

import com.qk.dam.authorization.access.voter.DamRoleVoter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.core.Authentication;

public class DamAccessDecisionManager extends AbstractAccessDecisionManager {
  private static final List<AccessDecisionVoter<?>> DECISION_VOTERS = new ArrayList<>();

  static {
    DECISION_VOTERS.add(new DamRoleVoter());
  }

  public DamAccessDecisionManager() {
    super(DECISION_VOTERS);
  }

  @Override
  public void decide(
      Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
      throws AccessDeniedException {
    int deny = 0;
    for (AccessDecisionVoter voter : getDecisionVoters()) {
      int result = voter.vote(authentication, object, configAttributes);
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
