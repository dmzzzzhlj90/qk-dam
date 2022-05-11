package com.qk.dm.dataservice.constant;

import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * MYBATIS SQL 是否开启分页
 *
 * @author wjq
 * @date 2022/05/10
 * @since 1.0.0
 */
public enum MybatisSqlPageFlagEnum {

    /**
     * 开启分页: true
     */
    TRUE(true, "开启分页: true"),

    /**
     * 开启分页: false
     */
    FALSE(false, "开启分页: false");

    private final Boolean code;
    private final String value;

    MybatisSqlPageFlagEnum(Boolean code, String value) {
        this.code = code;
        this.value = value;
    }

    public static MybatisSqlPageFlagEnum getVal(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        for (MybatisSqlPageFlagEnum enums : MybatisSqlPageFlagEnum.values()) {
            if (value.equals(enums.value)) {
                return enums;
            }
        }
        return null;
    }

    public static Map<Boolean, String> getAllValue() {
        Map<Boolean, String> val = new LinkedHashMap<>();
        for (MybatisSqlPageFlagEnum enums : MybatisSqlPageFlagEnum.values()) {
            val.put(enums.code, enums.value);
        }
        return val;
    }

    public Boolean getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
