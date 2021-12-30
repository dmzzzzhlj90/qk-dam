package com.qk.dm.dataquality.constant.ruletemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 模版类型
 *
 * @author shenpengjie
 */
public enum TempTypeEnum {
    // 系统内置
    BUILT_IN_SYSTEM("BUILT_IN_SYSTEM", "系统内置"),
    // 自定义
    CUSTOM("CUSTOM", "自定义");

    String code;
    String name;

    TempTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static TempTypeEnum fromValue(String code) {
        for (TempTypeEnum b : TempTypeEnum.values()) {
            if (b.code.equals(code)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected id '" + code + "'");
    }

    public static Map<String, String> getAllValue() {
        Map<String, String> val = new HashMap<>();
        for (TempTypeEnum enums : TempTypeEnum.values()) {
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
