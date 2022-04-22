package com.qk.dm.dataingestion.enums;

import java.util.Arrays;

public enum SchedulerCycle {

    minute("minute","分钟"),
    hour("hour","小时"),
    day("day","天"),
    week("week","周");

    String code;
    String name;

    SchedulerCycle(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static SchedulerCycle fromValue(String code) {
        return Arrays.stream(values()).filter(schedulerCycle ->
                schedulerCycle.getCode().equals(code)).findAny().orElse(null);
    }


    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
