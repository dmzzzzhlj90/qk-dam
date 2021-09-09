package com.qk.dm.auth.pojo;

import java.util.List;

/** @author daomingzhu */
public class RegisteredClients {
  private String issuer;
  private List<InnerRegisteredClient> clients;

  public List<InnerRegisteredClient> getClients() {
    return clients;
  }

  public void setClients(List<InnerRegisteredClient> clients) {
    this.clients = clients;
  }

  public String getIssuer() {
    return issuer;
  }

  public void setIssuer(String issuer) {
    this.issuer = issuer;
  }
}
