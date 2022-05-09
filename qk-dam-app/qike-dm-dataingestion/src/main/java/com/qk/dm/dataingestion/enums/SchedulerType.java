package com.qk.dm.dataingestion.enums;

/**
 * 调度方式
 */
public enum SchedulerType {
    SINGLE("SCHEDULER_TYPE_SINGLE", "单次调度"),
    CYCLE("SCHEDULER_TYPE_CYCLE", "周期调度");

    private final String code;
    private final String name;

    SchedulerType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
