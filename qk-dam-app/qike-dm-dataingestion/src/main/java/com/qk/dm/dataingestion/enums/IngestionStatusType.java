package com.qk.dm.dataingestion.enums;

import java.util.stream.Stream;

/**
 * 数据引入，调度状态映射
 */
public enum IngestionStatusType {
    SUBMITTED_SUCCESS("SUBMITTED_SUCCESS", "提交成功", IngestionStatus.RUNNING),
    RUNNING_EXECUTION("RUNNING_EXECUTION", "正在运行", IngestionStatus.RUNNING),
    READY_PAUSE("READY_PAUSE", "准备暂停", IngestionStatus.RUNNING),
    PAUSE("PAUSE", "暂停", IngestionStatus.RUNNING),
    READY_STOP("READY_STOP", "准备停止", IngestionStatus.RUNNING),
    FAILURE("FAILURE", "失败", IngestionStatus.RUN_FAIL),
    SUCCESS("SUCCESS", "成功", IngestionStatus.RUN_SUCCEED),
    NEED_FAULT_TOLERANCE("NEED_FAULT_TOLERANCE", "需要容错", IngestionStatus.RUNNING),
    WAITTING_THREAD("WAITTING_THREAD", "等待线程", IngestionStatus.RUNNING),
    WAITTING_DEPEND("WAITTING_DEPEND", "等待依赖完成", IngestionStatus.RUNNING),
    WARN("WARN","告警", IngestionStatus.RUN_SUCCEED);

    String code;
    String value;
    IngestionStatus ingestionStatus;

    IngestionStatusType(String code, String value, IngestionStatus ingestionStatus) {
        this.code = code;
        this.value = value;
        this.ingestionStatus = ingestionStatus;
    }

    public static IngestionStatusType getVal(String code) {
        return Stream.of(values()).filter(t -> t.code.equalsIgnoreCase(code)).findAny().orElse(null);
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public IngestionStatus getIngestionStatus() {
        return ingestionStatus;
    }
}
