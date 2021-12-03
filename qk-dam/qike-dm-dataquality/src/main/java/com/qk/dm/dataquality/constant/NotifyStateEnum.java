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

    /**
     * 通知状态 "CLOSE":"关","OPEN":"开";
     */
    CLOSE("CLOSE", "关"),
    OPEN("OPEN", "开");

    private String code;
    private String name;

    NotifyStateEnum(String code, String name) {
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

    public static Map<String, String> getAllValue() {
        Map<String, String> val = new HashMap<>();
        for (NotifyStateEnum enums : NotifyStateEnum.values()) {
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
