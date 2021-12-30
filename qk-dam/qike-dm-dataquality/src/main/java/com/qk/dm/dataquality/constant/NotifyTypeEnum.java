package com.qk.dm.dataquality.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 通知类型
 *
 * @author wjq
 * @date 2021/12/03
 * @since 1.0.0
 */
public enum NotifyTypeEnum {

    /**
     * 通知类型 "TRIGGER_ALARM":"触发告警", "RUN_SUCCESS":"运行成功";
     */
    TRIGGER_ALARM("TRIGGER_ALARM", "触发告警"),
    RUN_SUCCESS("RUN_SUCCESS", "运行成功");

    private String code;
    private String name;

    NotifyTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static Map<String, String> getAllValue() {
        Map<String, String> val = new HashMap<>();
        for (NotifyTypeEnum enums : NotifyTypeEnum.values()) {
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
