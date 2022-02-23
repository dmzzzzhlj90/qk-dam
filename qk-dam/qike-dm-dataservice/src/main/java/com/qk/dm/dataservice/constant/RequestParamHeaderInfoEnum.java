package com.qk.dm.dataservice.constant;

import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * API基础信息_入参定义表头信息
 *
 * @author wjq
 * @date 20220221
 * @since 1.0.0
 */
public enum RequestParamHeaderInfoEnum {

    /**
     * http请求参数
     */
    PARA_NAME("paraName", "http请求参数",true),

    /**
     * 入参位置
     */
    PARA_POSITION("paraPosition", "入参位置",true),

    /**
     * 参数类型
     */
    PARA_TYPE("paraType", "参数类型",true),

    /**
     * 是否必填
     */
    NECESSARY("necessary", "是否必填",true),

    /**
     * 允许空值
     */
    SUPPORT_NULL("supportNull", "允许空值",true),

    /**
     * 默认值
     */
    DEFAULT_VALUE("defaultValue", "默认值",false),

    /**
     * 示例值
     */
    EXAMPLE_VALUE("exampleValue", "示例值",false),

    /**
     * 描述
     */
    DESCRIPTION("description", "描述",false);


    private String key;
    private String value;
    private boolean required;


    RequestParamHeaderInfoEnum(String key, String value, boolean required) {
        this.key = key;
        this.value = value;
        this.required = required;
    }

    public static RequestParamHeaderInfoEnum getVal(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        for (RequestParamHeaderInfoEnum enums : RequestParamHeaderInfoEnum.values()) {
            if (value.equals(enums.value)) {
                return enums;
            }
        }
        return null;
    }

    public static LinkedList<Map<String, Object>> getAllValue() {
        LinkedList<Map<String, Object>> valList = new LinkedList<>();

        for (RequestParamHeaderInfoEnum enums : RequestParamHeaderInfoEnum.values()) {
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

    public boolean getRequired() {
        return required;
    }
}
