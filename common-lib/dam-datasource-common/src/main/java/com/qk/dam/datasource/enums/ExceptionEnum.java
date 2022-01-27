package com.qk.dam.datasource.enums;

import java.util.HashMap;
import java.util.Map;

public enum ExceptionEnum {
  /*捕获异常信息*/
  //MYSQL
  MYSQL_COMMUNICATIONSEXCEPTION("mysql","CommunicationsException","连接超时,请检测网络和配置信息"),
  MYSQL_SQLEXCEPTION("mysql","SQLException","用户名或密码错误"),
  //HIVE
  HIVE_SQLEXCEPTION("hive","SQLException","连接超时,请检测网络和配置信息");

  private String linkType;
  private String exceptionName;
  private String viue;

  public String getLinkType() {
    return linkType;
  }

  public void setLinkType(String linkType) {
    this.linkType = linkType;
  }

  public String getExceptionName() {
    return exceptionName;
  }

  public void setExceptionName(String exceptionName) {
    this.exceptionName = exceptionName;
  }

  public String getViue() {
    return viue;
  }

  public void setViue(String viue) {
    this.viue = viue;
  }

  ExceptionEnum(String linkType, String exceptionName, String viue) {
    this.linkType = linkType;
    this.exceptionName = exceptionName;
    this.viue = viue;
  }

  ExceptionEnum() {
  }

  public static String fromValue(String linkType,String exceptionName ) {
    for (ExceptionEnum b : ExceptionEnum.values()) {
      if (b.linkType.equals(linkType) && b.exceptionName.equals(exceptionName)) {
        return b.viue;
      }
    }
    return "请检查网络和连接信息后重新测试";
  }

  public static Map<String, String> getAllValue() {
    Map<String, String> val = new HashMap<>();
    for (ExceptionEnum enums : ExceptionEnum.values()) {
      val.put(enums.exceptionName, enums.viue);
    }
    return val;
  }
}
