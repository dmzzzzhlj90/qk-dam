package com.qk.dam.authorization;

public enum BizResource {
  MTD_ENTITY_TYPE("entityType", "元数据实体类型资源"),
  MTD_BASIC_TYPE("basicType", "元数据实体类型资源"),
  MTD_ENTITY("entity", "元数据实体资源"),
  MTD_CLASS("class", "元数据实分类资源"),
  MTD_CLASS_BIND("class:bind", "元数据实分类资源"),
  MTD_LABELS("labels", "元数据实分类资源"),
  MTD_LABELS_BIND("labels:bind", "元数据实分类资源"),
  MTD_AUDIT("audit", "元数据操作资源"),
  MTD_ENTITY_DB("entity:db", "元数据实体DB资源"),
  MTD_CRITERIA_ENTITY("criteria", "元数据条件查询实体资源"),
  DSD_BASIC_INFO("basicInfo", "数据标准标准基础信息"),
  DSD_BASIC_INFO_CODE("basicInfo:code", "标准信息引用码表"),
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
