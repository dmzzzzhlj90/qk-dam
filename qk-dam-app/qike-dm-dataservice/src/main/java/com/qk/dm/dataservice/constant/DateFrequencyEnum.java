package com.qk.dm.dataservice.constant;

import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 日期频次
 *
 * @author wjq
 * @date 2022/03/14
 * @since 1.0.0
 */
public enum DateFrequencyEnum {

    /**
     * 日
     */
    DAY("DAY", "今日"),

    /**
     * 周
     */
    WEEK("WEEK", "本周"),

    /**
     * 月
     */
    MONTH("MONTH", "本月"),

    /**
     * 年
     */
    YEAR("YEAR", "今年");

    private String code;
    private String value;

    DateFrequencyEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static DateFrequencyEnum getVal(String code) {
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        for (DateFrequencyEnum enums : DateFrequencyEnum.values()) {
            if (code.equalsIgnoreCase(enums.code)) {
                return enums;
            }
        }
        return null;
    }

    public static Map<String, String> getAllValue() {
        Map<String, String> val = new LinkedHashMap<>();
        for (DateFrequencyEnum enums : DateFrequencyEnum.values()) {
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
