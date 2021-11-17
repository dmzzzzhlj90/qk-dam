package com.qk.dm.dataquality.constant;

import org.springframework.util.ObjectUtils;

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

    SCHEDULER_TYPE_SINGLE("SCHEDULER_TYPE_SINGLE", "单次调度"),
    SCHEDULER_TYPE_CYCLE("SCHEDULER_TYPE_CYCLE", "周期调度");

    private String code;
    private String name;

    SchedulerTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static SchedulerTypeEnum getVal(String name) {
        if (ObjectUtils.isEmpty(name)) {
            return null;
        }
        for (SchedulerTypeEnum enums : SchedulerTypeEnum.values()) {
            if (name.equals(enums.name)) {
                return enums;
            }
        }
        return null;
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
