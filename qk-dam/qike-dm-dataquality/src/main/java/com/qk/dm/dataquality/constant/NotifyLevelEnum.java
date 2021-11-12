package com.qk.dm.dataquality.constant;

import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 提示级别
 *
 * @author wjq
 * @date 2021/11/12
 * @since 1.0.0
 */
public enum NotifyLevelEnum {

    NOTIFY_LEVEL_HINT(0, "提示"),
    NOTIFY_LEVEL_GENERAL(1, "一般"),
    NOTIFY_LEVEL_SERIOUS(2, "严重"),
    NOTIFY_LEVEL_FATAL(3, "致命");

    private Integer code;
    private String name;

    NotifyLevelEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static NotifyLevelEnum getVal(String name) {
        if (ObjectUtils.isEmpty(name)) {
            return null;
        }
        for (NotifyLevelEnum enums : NotifyLevelEnum.values()) {
            if (name.equals(enums.name)) {
                return enums;
            }
        }
        return null;
    }

    public static Map<Integer, String> getAllValue() {
        Map<Integer, String> val = new HashMap<>();
        for (NotifyLevelEnum enums : NotifyLevelEnum.values()) {
            val.put(enums.code, enums.name);
        }
        return val;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
