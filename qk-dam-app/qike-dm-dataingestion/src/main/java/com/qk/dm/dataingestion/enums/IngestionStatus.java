package com.qk.dm.dataingestion.enums;

/**
 * 数据引入数据状态
 */
public enum IngestionStatus {

    INIT(0, "初始状态"),
    RUNNING(1, "运行中"),
    RUN_SUCCEED(2, "成功"),
    RUN_FAIL(3, "失败");

    Integer code;
    String value;

    IngestionStatus(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
