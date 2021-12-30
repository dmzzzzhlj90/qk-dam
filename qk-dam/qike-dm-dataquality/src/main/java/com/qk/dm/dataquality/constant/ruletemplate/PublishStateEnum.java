package com.qk.dm.dataquality.constant.ruletemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 发布状态
 * @author shenpengjie
 */
public enum PublishStateEnum {
    OUTLINE("OUTLINE","草稿"),
    OFFLINE("OFFLINE","下线"),
    RELEASE("RELEASE","发布");

    String code;
    String name;

    PublishStateEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static PublishStateEnum fromValue(String code) {
        for (PublishStateEnum b : PublishStateEnum.values()) {
            if (b.code.equals(code)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected id '" + code + "'");
    }

    public static Map<String, String> getAllValue() {
        Map<String, String> val = new HashMap<>();
        for (PublishStateEnum enums : PublishStateEnum.values()) {
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
