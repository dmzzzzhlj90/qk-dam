package com.qk.dam.authorization;

public enum RestActionType {
  /** 只读 */
  GET("get", "查询"),
  DETAIL("detail", "明细"),
  DELETE("delete", "删除"),
  CREATE("create", "创建"),
  UPDATE("update", "修改"),
  IMPORT("import", "导入"),
  EXPORT("export", "导出"),
  BIND("bind", "绑定"),
  UNBIND("unbind", "解绑"),
  UPLOAD("upload", "上传"),
  DOWNLOAD("download", "下载"),
  WRITE_APPLY("apply", "申请"),
  LIST("list", "查询列表"),
  START("start", "开始"),
  STOP("stop", "停止"),
  ;
  private final String type;
  private final String actionDesc;

  RestActionType(String type, String actionDesc) {
    this.type = type;
    this.actionDesc = actionDesc;
  }

  public String getType() {
    return type;
  }

  public String getActionDesc() {
    return actionDesc;
  }
}
