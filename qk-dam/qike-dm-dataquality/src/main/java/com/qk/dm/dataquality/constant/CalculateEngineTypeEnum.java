package com.qk.dm.dataquality.constant;

import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 计算引擎类型
 *
 * @author wjq
 * @date 2021/11/12
 * @since 1.0.0
 */
public enum CalculateEngineTypeEnum {

    CALCULATE_ENGINE_MYSQL("MYSQL"),
    CALCULATE_ENGINE_HIVE("HIVE"),
    CALCULATE_ENGINE_ORACLE("ORACLE");

    private String type;

    CalculateEngineTypeEnum(String type) {
        this.type = type;
    }

    public static CalculateEngineTypeEnum getVal(String type) {
        if (ObjectUtils.isEmpty(type)) {
            return null;
        }
        for (CalculateEngineTypeEnum enums : CalculateEngineTypeEnum.values()) {
            if (type.equals(enums.type)) {
                return enums;
            }
        }
        return null;
    }

    public static List<String> getAllValue() {
        List<String> valList = new ArrayList<>();
        for (CalculateEngineTypeEnum enums : CalculateEngineTypeEnum.values()) {
            valList.add(enums.type);
        }
        return valList;
    }

    public String getType() {
        return type;
    }

}
