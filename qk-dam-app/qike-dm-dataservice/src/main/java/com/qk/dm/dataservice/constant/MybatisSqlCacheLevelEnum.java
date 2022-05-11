package com.qk.dm.dataservice.constant;

import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * MYBATIS SQL 开启缓存等级
 *
 * @author wjq
 * @date 2022/05/10
 * @since 1.0.0
 */
public enum MybatisSqlCacheLevelEnum {

    /**
     * 0: 不开启缓存
     */
    NO(0, "0: 不开启缓存"),

    /**
     * 1: 一级缓存
     */
    LEVEL_FIRST(1, "1: 一级缓存"),

    /**
     * 2: 二级缓存
     */
    LEVEL_SECOND(2, "2: 二级缓存");

    private final Integer code;
    private final String value;

    MybatisSqlCacheLevelEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public static MybatisSqlCacheLevelEnum getVal(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        for (MybatisSqlCacheLevelEnum enums : MybatisSqlCacheLevelEnum.values()) {
            if (value.equals(enums.value)) {
                return enums;
            }
        }
        return null;
    }

    public static Map<Integer, String> getAllValue() {
        Map<Integer, String> val = new LinkedHashMap<>();
        for (MybatisSqlCacheLevelEnum enums : MybatisSqlCacheLevelEnum.values()) {
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
