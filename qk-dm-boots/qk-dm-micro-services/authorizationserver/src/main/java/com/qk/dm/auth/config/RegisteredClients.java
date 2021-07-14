package com.qk.dm.auth.config;

import java.util.List;

/** @author daomingzhu */
public class RegisteredClients {
  private List<InnerRegisteredClient> clients;

  public List<InnerRegisteredClient> getClients() {
    return clients;
  }

  public void setClients(List<InnerRegisteredClient> clients) {
    this.clients = clients;
  }
}
