package com.qk.dm.dataquality.constant;

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
    /**
     * 提示级别 "HINT":"提示","GENERAL":"一般","SERIOUS":"严重","FATAL":"致命";
     */
    HINT("HINT", "提示"),
    GENERAL("GENERAL", "一般"),
    SERIOUS("SERIOUS", "严重"),
    FATAL("FATAL", "致命");

    private String code;
    private String name;

    NotifyLevelEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static Map<String, String> getAllValue() {
        Map<String, String> val = new HashMap<>();
        for (NotifyLevelEnum enums : NotifyLevelEnum.values()) {
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
