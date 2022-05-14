package com.qk.dm.dataservice.constant;

import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 结果集数据格式类型
 *
 * @author wjq
 * @date 2022/05/14
 * @since 1.0.0
 */
public enum ResultDataTypeEnum {

    /**
     * 0: 单条
     */
    RESULT_DATA_TYPE_MAP(0, "单条"),

    /**
     * 1: 列表
     */
    RESULT_DATA_TYPE_LIST(1, "列表");

    private final Integer code;
    private final String value;

    ResultDataTypeEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public static ResultDataTypeEnum getVal(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        for (ResultDataTypeEnum enums : ResultDataTypeEnum.values()) {
            if (value.equals(enums.value)) {
                return enums;
            }
        }
        return null;
    }

    public static Map<Integer, String> getAllValue() {
        Map<Integer, String> val = new LinkedHashMap<>();
        for (ResultDataTypeEnum enums : ResultDataTypeEnum.values()) {
            val.put(enums.code, enums.value);
        }
        return val;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
