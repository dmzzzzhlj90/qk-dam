package com.qk.dm.dataservice.constant;

import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 时间单位
 *
 * @author wjq
 * @date 2022/03/17
 * @since 1.0.0
 */
public enum TimeUnitTypeEnum {

    /**
     * 秒
     */
    MS("ms", "秒"),

    /**
     * 分钟
     */
    MIN("min", "分钟"),

    /**
     * 小时
     */
    H("h", "小时"),

    /**
     * 天
     */
    D("d", "天");

    private String code;
    private String value;

    TimeUnitTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static TimeUnitTypeEnum getVal(String code) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        for (TimeUnitTypeEnum enums : TimeUnitTypeEnum.values()) {
            if (code.equals(enums.code)) {
                return enums;
            }
        }
        return null;
    }

    public static Map<String, String> getAllValue() {
        Map<String, String> val = new LinkedHashMap<>();
        for (TimeUnitTypeEnum enums : TimeUnitTypeEnum.values()) {
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
