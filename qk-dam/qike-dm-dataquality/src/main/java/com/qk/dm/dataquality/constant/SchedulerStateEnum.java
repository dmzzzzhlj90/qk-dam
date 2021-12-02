package com.qk.dm.dataquality.constant;

/**
 * 调度状态
 *
 * @author shenpengjie
 */
public enum SchedulerStateEnum {
    // 下线
    OFFLINE(0, "未启动"),
    // 上线
    ONLINE(1, "调度中");

    Integer code;
    String value;

    SchedulerStateEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public static SchedulerStateEnum fromValue(Integer code) {
        for (SchedulerStateEnum b : SchedulerStateEnum.values()) {
            if (b.code.equals(code)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected id '" + code + "'");
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
