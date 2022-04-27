package com.qk.dm.datacollect.dolphin.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * 调度方式
 *
 * @author wjq
 * @date 2021/11/12
 * @since 1.0.0
 */
public enum SchedulerTypeEnum {

    SINGLE("SINGLE", "单次调度"),
    CYCLE("CYCLE", "周期调度");

    private String code;
    private String name;

    SchedulerTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static Map<String, String> getAllValue() {
        Map<String, String> val = new HashMap<>();
        for (SchedulerTypeEnum enums : SchedulerTypeEnum.values()) {
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
