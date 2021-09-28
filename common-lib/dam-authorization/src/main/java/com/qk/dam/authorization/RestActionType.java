package com.qk.dam.authorization;

public enum RestActionType {
  /** 只读 */
  GET("get", "查询"),
  DETAIL("detail", "明细"),
  WRITE_DELETE("delete", "删除"),
  WRITE_CREATE("create", "创建"),
  WRITE_UPDATE("update", "修改"),
  WRITE_IMPORT("import", "导入"),
  WRITE_EXPORT("export", "导出"),
  WRITE_BIND("bind", "绑定"),
  WRITE_UNBIND("unbind", "解绑"),
  WRITE_UPLOAD("upload", "上传"),
  WRITE_DOWNLOAD("download", "下载"),
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
