package com.qk.dm.auth.user;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

public class CusUserManager extends JdbcUserDetailsManager {
  public CusUserManager(DataSource dataSource) {
    super(dataSource);
  }

  @Override
  protected void addCustomAuthorities(String username, List<GrantedAuthority> authorities) {
    super.addCustomAuthorities(username, authorities);
  }
}
