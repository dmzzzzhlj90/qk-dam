package com.qk.dm.dataservice.constant;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * API基础信息_入参定义表头信息
 *
 * @author wjq
 * @date 20220221
 * @since 1.0.0
 */
public enum RequestParasHeaderInfoEnum {

    /**
     * http请求参数
     */
    PARA_NAME("paraName", "http请求参数"),

    /**
     * 入参位置
     */
    PARA_POSITION("paraPosition", "入参位置"),

    /**
     * 参数类型
     */
    PARA_TYPE("paraType", "参数类型"),

    /**
     * 是否必填
     */
    NECESSARY("necessary", "是否必填"),

    /**
     * 允许空值
     */
    SUPPORT_NULL("supportNull", "允许空值"),

    /**
     * 默认值
     */
    DEFAULT_VALUE("defaultValue", "默认值"),

    /**
     * 示例值
     */
    EXAMPLE_VALUE("exampleValue", "示例值"),

    /**
     * 描述
     */
    DESCRIPTION("description", "描述");


    private String code;
    private String value;

    RequestParasHeaderInfoEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static RequestParasHeaderInfoEnum getVal(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        for (RequestParasHeaderInfoEnum enums : RequestParasHeaderInfoEnum.values()) {
            if (value.equals(enums.value)) {
                return enums;
            }
        }
        return null;
    }

    public static Map<String, String> getAllValue() {
        Map<String, String> val = new LinkedHashMap<>();
        for (RequestParasHeaderInfoEnum enums : RequestParasHeaderInfoEnum.values()) {
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
