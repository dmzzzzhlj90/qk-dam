package com.qk.dm.dataquality.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 调度周期
 * @author shenpengjie
 */
public enum SchedulerCycleEnum {
    minute("minute","分钟"),
    hour("hour","小时"),
    day("day","天"),
    week("week","周");

    String code;
    String name;

    SchedulerCycleEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static SchedulerCycleEnum fromValue(String code) {
        for (SchedulerCycleEnum b : SchedulerCycleEnum.values()) {
            if (b.code.equals(code)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected id '" + code + "'");
    }

    public static Map<String, String> getAllValue() {
        Map<String, String> val = new HashMap<>();
        for (SchedulerCycleEnum enums : SchedulerCycleEnum.values()) {
            val.put(enums.code, enums.name);
        }
        return val;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
