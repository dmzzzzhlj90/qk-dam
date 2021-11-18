package com.qk.dm.dataquality.constant;

import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 计算引擎类型
 *
 * @author wjq
 * @date 2021/11/12
 * @since 1.0.0
 */
public enum CalculateEngineTypeEnum {

    CALCULATE_ENGINE_MYSQL("CALCULATE_ENGINE_MYSQL", "MYSQL"),
    CALCULATE_ENGINE_HIVE("CALCULATE_ENGINE_HIVE", "HIVE"),
    CALCULATE_ENGINE_ORACLE("CALCULATE_ENGINE_ORACLE", "ORACLE");

    private String code;
    private String type;

    CalculateEngineTypeEnum(String code, String type) {
        this.code = code;
        this.type = type;
    }

    public static CalculateEngineTypeEnum getVal(String code) {
        if (ObjectUtils.isEmpty(code)) {
            return null;
        }
        for (CalculateEngineTypeEnum enums : CalculateEngineTypeEnum.values()) {
            if (code.equals(enums.type)) {
                return enums;
            }
        }
        return null;
    }

    public static Map<String, String> getAllValue() {
        Map<String, String> val = new HashMap<>();
        for (CalculateEngineTypeEnum enums : CalculateEngineTypeEnum.values()) {
            val.put(enums.code, enums.type);
        }
        return val;
    }

    public String getType() {
        return type;
    }

}
