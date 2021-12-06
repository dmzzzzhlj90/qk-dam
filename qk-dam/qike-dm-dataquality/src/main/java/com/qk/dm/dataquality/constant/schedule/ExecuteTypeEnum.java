package com.qk.dm.dataquality.constant.schedule;

import java.util.HashMap;
import java.util.Map;

/**
 * 执行类型
 * @author shenpengjie
 */
public enum ExecuteTypeEnum {
    NONE("NONE","无操作"),
    REPEAT_RUNNING("REPEAT_RUNNING","重跑"),
    RECOVER_SUSPENDED_PROCESS("RECOVER_SUSPENDED_PROCESS","恢复"),
    START_FAILURE_TASK_PROCESS("START_FAILURE_TASK_PROCESS","启动失败任务"),
    STOP("STOP","停止"),
    PAUSE("PAUSE","暂停");

    String code;
    String name;

    ExecuteTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static ExecuteTypeEnum fromValue(String code) {
        for (ExecuteTypeEnum b : ExecuteTypeEnum.values()) {
            if (b.code.equals(code)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected id '" + code + "'");
    }

    public static Map<String, String> getAllValue() {
        Map<String, String> val = new HashMap<>();
        for (ExecuteTypeEnum enums : ExecuteTypeEnum.values()) {
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
