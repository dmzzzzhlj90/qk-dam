package com.qk.dm.dataquality.constant;

import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 规则类型
 *
 * @author wjq
 * @date 2021/11/12
 * @since 1.0.0
 */
public enum RuleTypeEnum {

    RULE_TYPE_FIELD("RULE_TYPE_FIELD", "字段级别规则"),
    RULE_TYPE_TABLE("RULE_TYPE_TABLE", "表级别规则"),
    RULE_TYPE_DB("RULE_TYPE_DB", "库级别规则");

    private String code;
    private String name;

    RuleTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static RuleTypeEnum getVal(String code) {
        if (ObjectUtils.isEmpty(code)) {
            return null;
        }
        for (RuleTypeEnum enums : RuleTypeEnum.values()) {
            if (code.equals(enums.code)) {
                return enums;
            }
        }
        return null;
    }

    public static Map<String, String> getAllValue() {
        Map<String, String> val = new HashMap<>();
        for (RuleTypeEnum enums : RuleTypeEnum.values()) {
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
