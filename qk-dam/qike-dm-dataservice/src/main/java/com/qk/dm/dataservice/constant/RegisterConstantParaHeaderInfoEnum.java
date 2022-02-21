package com.qk.dm.dataservice.constant;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 注册API_常量参数表头信息
 *
 * @author wjq
 * @date 20220221
 * @since 1.0.0
 */
public enum RegisterConstantParaHeaderInfoEnum {

    /**
     * 常量参数名称
     */
    CONSTANT_PARA_NAME("constantParaName", "常量参数名称"),

    /**
     * 参数位置
     */
    CONSTANT_PARA_POSITION("constantParaPosition", "参数位置"),

    /**
     * 参数类型
     */
    CONSTANT_PARA_TYPE("constantParaType", "参数类型"),

    /**
     * 参数值
     */
    CONSTANT_PARA_VALUE("constantParaValue", "参数值"),

    /**
     * 描述
     */
    DESCRIPTION("description", "描述");


    private String code;
    private String value;

    RegisterConstantParaHeaderInfoEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static RegisterConstantParaHeaderInfoEnum getVal(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        for (RegisterConstantParaHeaderInfoEnum enums : RegisterConstantParaHeaderInfoEnum.values()) {
            if (value.equals(enums.value)) {
                return enums;
            }
        }
        return null;
    }

    public static Map<String, String> getAllValue() {
        Map<String, String> val = new LinkedHashMap<>();
        for (RegisterConstantParaHeaderInfoEnum enums : RegisterConstantParaHeaderInfoEnum.values()) {
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
