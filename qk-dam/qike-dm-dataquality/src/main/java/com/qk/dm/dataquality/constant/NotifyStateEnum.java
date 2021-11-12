package com.qk.dm.dataquality.constant;

import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 通知状态
 *
 * @author wjq
 * @date 2021/11/12
 * @since 1.0.0
 */
public enum NotifyStateEnum {

    NOTIFY_STATE_CLOSE(0, "关"),
    NOTIFY_STATE_OPEN(1, "开");

    private Integer code;
    private String name;

    NotifyStateEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static NotifyStateEnum getVal(String name) {
        if (ObjectUtils.isEmpty(name)) {
            return null;
        }
        for (NotifyStateEnum enums : NotifyStateEnum.values()) {
            if (name.equals(enums.name)) {
                return enums;
            }
        }
        return null;
    }

    public static Map<Integer, String> getAllValue() {
        Map<Integer, String> val = new HashMap<>();
        for (NotifyStateEnum enums : NotifyStateEnum.values()) {
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
