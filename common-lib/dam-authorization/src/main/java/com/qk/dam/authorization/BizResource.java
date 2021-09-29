package com.qk.dam.authorization;

public enum BizResource {
  MTD_ENTITY_TYPE("entityType", "元数据实体类型资源"),
  MTD_BASIC_TYPE("basicType", "元数据实体类型资源"),
  MTD_ENTITY("entity", "元数据实体资源"),
  MTD_ENTITY_DB("entity:db", "元数据实体DB资源"),
  MTD_CRITERIA_ENTITY("criteria", "元数据条件查询实体资源"),
  ;

  private String bizCls;
  private String bizDesc;

  BizResource(String bizCls, String bizDesc) {
    this.bizCls = bizCls;
    this.bizDesc = bizDesc;
  }

  public String getBizCls() {
    return bizCls;
  }

  public String getBizDesc() {
    return bizDesc;
  }
}