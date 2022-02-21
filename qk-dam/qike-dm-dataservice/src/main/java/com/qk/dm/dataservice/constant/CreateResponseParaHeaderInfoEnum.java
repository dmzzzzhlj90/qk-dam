package com.qk.dm.dataservice.constant;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 新建API_响应参数表头信息
 *
 * @author wjq
 * @date 20220221
 * @since 1.0.0
 */
public enum CreateResponseParaHeaderInfoEnum {

    /**
     * 参数名称
     */
    PARA_NAME("paraName", "参数名称"),

    /**
     * 绑定字段
     */
    MAPPING_NAME("mappingName", "绑定字段"),

    /**
     * 参数类型
     */
    PARA_TYPE("paraType", "参数类型"),

    /**
     * 示例值
     */
    EXAMPLE_VALUE("exampleValue", "示例值"),

    /**
     * 默认值
     */
    DEFAULT_VALUE("defaultValue", "默认值"),

    /**
     * 描述
     */
    DESCRIPTION("description", "描述");


    private String code;
    private String value;

    CreateResponseParaHeaderInfoEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static CreateResponseParaHeaderInfoEnum getVal(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        for (CreateResponseParaHeaderInfoEnum enums : CreateResponseParaHeaderInfoEnum.values()) {
            if (value.equals(enums.value)) {
                return enums;
            }
        }
        return null;
    }

    public static Map<String, String> getAllValue() {
        Map<String, String> val = new LinkedHashMap<>();
        for (CreateResponseParaHeaderInfoEnum enums : CreateResponseParaHeaderInfoEnum.values()) {
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
