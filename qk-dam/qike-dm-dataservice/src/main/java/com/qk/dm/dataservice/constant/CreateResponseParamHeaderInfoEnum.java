package com.qk.dm.dataservice.constant;

import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 新建API_响应参数表头信息
 *
 * @author wjq
 * @date 20220221
 * @since 1.0.0
 */
public enum CreateResponseParamHeaderInfoEnum {

    /**
     * 参数名称
     */
    PARA_NAME("paraName", "参数名称",true),

    /**
     * 绑定字段
     */
    MAPPING_NAME("mappingName", "绑定字段",true),

    /**
     * 参数类型
     */
    PARA_TYPE("paraType", "参数类型",true),

    /**
     * 示例值
     */
    EXAMPLE_VALUE("exampleValue", "示例值",false),

    /**
     * 默认值
     */
    DEFAULT_VALUE("defaultValue", "默认值",false),

    /**
     * 描述
     */
    DESCRIPTION("description", "描述",false);


    private String key;
    private String value;
    private boolean required;

    CreateResponseParamHeaderInfoEnum(String key, String value, boolean required) {
        this.key = key;
        this.value = value;
        this.required = required;
    }

    public static CreateResponseParamHeaderInfoEnum getVal(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        for (CreateResponseParamHeaderInfoEnum enums : CreateResponseParamHeaderInfoEnum.values()) {
            if (value.equals(enums.value)) {
                return enums;
            }
        }
        return null;
    }

    public static LinkedList<Map<String, Object>> getAllValue() {
        LinkedList<Map<String, Object>> valList = new LinkedList<>();

        for (CreateResponseParamHeaderInfoEnum enums : CreateResponseParamHeaderInfoEnum.values()) {
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
