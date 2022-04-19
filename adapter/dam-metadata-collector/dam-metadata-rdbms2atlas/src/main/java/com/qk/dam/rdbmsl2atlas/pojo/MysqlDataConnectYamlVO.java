package com.qk.dam.rdbmsl2atlas.pojo;

public class MysqlDataConnectYamlVO {
  private String db;
  private String table;
  private String host;
  private String port;
  private String username;
  private String password;

  @Override
  public String toString() {
    return "MysqlDataConnectConf{"
        + "db='"
        + db
        + '\''
        + ", table='"
        + table
        + '\''
        + ", host='"
        + host
        + '\''
        + ", port='"
        + port
        + '\''
        + ", username='"
        + username
        + '\''
        + ", password='"
        + password
        + '\''
        + '}';
  }

  public String getDb() {
    return db;
  }

  public void setDb(String db) {
    this.db = db;
  }

  public String getTable() {
    return table;
  }

  public void setTable(String table) {
    this.table = table;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public String getPort() {
    return port;
  }

  public void setPort(String port) {
    this.port = port;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
