package com.qk.dm.dataquality.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 调度状态
 *
 * @author shenpengjie
 */
public enum SchedulerStateEnum {

    OFFLINE(0,"OFFLINE", "下线"),
    ONLINE(1,"ONLINE", "上线");

    Integer state;
    String code;
    String name;

    SchedulerStateEnum(Integer state, String code, String name) {
        this.state = state;
        this.code = code;
        this.name = name;
    }

    public static SchedulerStateEnum fromValue(String code) {
        for (SchedulerStateEnum b : SchedulerStateEnum.values()) {
            if (b.code.equals(code)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected id '" + code + "'");
    }

    public static Map<String, String> getAllValue() {
        Map<String, String> val = new HashMap<>();
        for (SchedulerStateEnum enums : SchedulerStateEnum.values()) {
            val.put(enums.code, enums.name);
        }
        return val;
    }

    public Integer getState() {
        return state;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
