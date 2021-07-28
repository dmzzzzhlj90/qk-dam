package com.qk.dam.rdbmsl2atlas.pojo;

public class MetadataJobYamlVO {
  private String name;
  private String dataConnectType;
  private MysqlDataConnectYamlVO dataConnect;
  private ServerinfoYamlVO serverinfoYamlVO;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDataConnectType() {
    return dataConnectType;
  }

  public void setDataConnectType(String dataConnectType) {
    this.dataConnectType = dataConnectType;
  }

  public MysqlDataConnectYamlVO getDataConnect() {
    return dataConnect;
  }

  public void setDataConnect(MysqlDataConnectYamlVO dataConnect) {
    this.dataConnect = dataConnect;
  }

  public ServerinfoYamlVO getServerinfoYamlVO() {
    return serverinfoYamlVO;
  }

  public void setServerinfoYamlVO(ServerinfoYamlVO serverinfoYamlVO) {
    this.serverinfoYamlVO = serverinfoYamlVO;
  }

  @Override
  public String toString() {
    return "MetadataJobYamlVO{"
        + "name='"
        + name
        + '\''
        + ", dataConnectType='"
        + dataConnectType
        + '\''
        + ", dataConnect="
        + dataConnect
        + ", serverinfoYamlVO="
        + serverinfoYamlVO
        + '}';
  }
}
