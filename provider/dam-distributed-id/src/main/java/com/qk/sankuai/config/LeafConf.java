package com.qk.sankuai.config;

public class LeafConf {
  private boolean leafSegmentEnable;
  private boolean leafSnowflakeEnable;
  private int leafSnowflakePort;
  private String leafSnowflakeZkAddress;

  public boolean isLeafSegmentEnable() {
    return leafSegmentEnable;
  }

  public void setLeafSegmentEnable(boolean leafSegmentEnable) {
    this.leafSegmentEnable = leafSegmentEnable;
  }

  public boolean isLeafSnowflakeEnable() {
    return leafSnowflakeEnable;
  }

  public void setLeafSnowflakeEnable(boolean leafSnowflakeEnable) {
    this.leafSnowflakeEnable = leafSnowflakeEnable;
  }

  public int getLeafSnowflakePort() {
    return leafSnowflakePort;
  }

  public void setLeafSnowflakePort(int leafSnowflakePort) {
    this.leafSnowflakePort = leafSnowflakePort;
  }

  public String getLeafSnowflakeZkAddress() {
    return leafSnowflakeZkAddress;
  }

  public void setLeafSnowflakeZkAddress(String leafSnowflakeZkAddress) {
    this.leafSnowflakeZkAddress = leafSnowflakeZkAddress;
  }

  @Override
  public String toString() {
    return "LeafConf{"
        + "leafSegmentEnable="
        + leafSegmentEnable
        + ", leafSnowflakeEnable="
        + leafSnowflakeEnable
        + ", leafSnowflakePort="
        + leafSnowflakePort
        + ", leafSnowflakeZkAddress='"
        + leafSnowflakeZkAddress
        + '\''
        + '}';
  }
}
