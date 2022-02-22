package com.qk.dm.dataservice.constant;

import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.LinkedList;
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
    CONSTANT_PARA_NAME("constantParaName", "常量参数名称", true),

    /**
     * 参数位置
     */
    CONSTANT_PARA_POSITION("constantParaPosition", "参数位置", true),

    /**
     * 参数类型
     */
    CONSTANT_PARA_TYPE("constantParaType", "参数类型", true),

    /**
     * 参数值
     */
    CONSTANT_PARA_VALUE("constantParaValue", "参数值", true),

    /**
     * 描述
     */
    DESCRIPTION("description", "描述", false),
    ;


    private String key;
    private String value;
    private boolean required;

    RegisterConstantParaHeaderInfoEnum(String key, String value, boolean required) {
        this.key = key;
        this.value = value;
        this.required = required;
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

    public static LinkedList<Map<String, Object>> getAllValue() {
        LinkedList<Map<String, Object>> valList = new LinkedList<>();

        for (RegisterConstantParaHeaderInfoEnum enums : RegisterConstantParaHeaderInfoEnum.values()) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put(DasConstant.PARAM_KEY, enums.key);
            map.put(DasConstant.PARAM_VALUE, enums.value);
            map.put(DasConstant.PARAM_REQUIRED, enums.required);
            valList.add(map);
        }
        return valList;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public boolean isRequired() {
        return required;
    }
}
