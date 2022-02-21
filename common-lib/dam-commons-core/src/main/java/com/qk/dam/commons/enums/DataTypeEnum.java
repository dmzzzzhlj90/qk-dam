package com.qk.dam.commons.enums;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据类型
 *
 * @author wjq
 * @date 20220221
 * @since 1.0.0
 */
public enum DataTypeEnum {

    /**
     * 字符类型(STRING)
     */
    STRING("STRING", "字符类型(STRING)"),

    /**
     * 双精度(DOUBLE)
     */
    DOUBLE("STRING", "双精度(DOUBLE)"),

    /**
     * 长整型(BIGINT)
     */
    BIGINT("STRING", "长整型(BIGINT)"),

    /**
     * 布尔类型(BOOLEAN)
     */
    BOOLEAN("STRING", "布尔类型(BOOLEAN)"),

    /**
     * 高精度(DECIMAL)
     */
    DECIMAL("STRING", "高精度(DECIMAL)"),

    /**
     * 日期类型(DATE)
     */
    DATE("STRING", "日期类型(DATE)"),

    /**
     * 时间戳类型(TIMESTAMP)
     */
    TIMESTAMP("STRING", "时间戳类型(TIMESTAMP)"),

    /**
     * 其他数据类型(OTHER_TYPE)
     */
    OTHER_TYPE("STRING", "其他数据类型(OTHER_TYPE)");

    private String code;
    private String value;

    DataTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static DataTypeEnum getVal(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        for (DataTypeEnum enums : DataTypeEnum.values()) {
            if (value.equals(enums.value)) {
                return enums;
            }
        }
        return null;
    }

    public static Map<String, String> getAllValue() {
        Map<String, String> val = new HashMap<>();
        for (DataTypeEnum enums : DataTypeEnum.values()) {
            val.put(enums.code, enums.value);
        }
        return val;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
