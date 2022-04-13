package com.qk.dm.dataservice.constant;

import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 数据库数据类型 database type of data
 *
 * @author wjq
 * @date 2022/04/13
 * @since 1.0.0
 */
public enum DataBaseDataTypeEnum {

    /**
     * date
     */
    DATE("date", "%Y-%m-%d"),
    /**

     * datetime
     */
    DATETIME("datetime", "%Y-%m-%d %T");

    private String code;
    private String value;

    DataBaseDataTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static DataBaseDataTypeEnum getVal(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        for (DataBaseDataTypeEnum enums : DataBaseDataTypeEnum.values()) {
            if (value.equals(enums.value)) {
                return enums;
            }
        }
        return null;
    }

    public static Map<String, String> getAllValue() {
        Map<String, String> val = new LinkedHashMap<>();
        for (DataBaseDataTypeEnum enums : DataBaseDataTypeEnum.values()) {
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
