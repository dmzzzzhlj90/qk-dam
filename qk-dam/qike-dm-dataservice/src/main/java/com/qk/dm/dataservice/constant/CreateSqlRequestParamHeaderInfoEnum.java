package com.qk.dm.dataservice.constant;

import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 新建API_SQL脚本方式_请求参数表头信息
 *
 * @author wjq
 * @date 20220221
 * @since 1.0.0
 */
public enum CreateSqlRequestParamHeaderInfoEnum {

    /**
     * 变量
     */
    VARIABLE("variable", "变量",true),

    /**
     * 请求参数
     */
    PARA_NAME("paraName", "请求参数",true),

    /**
     * 参数类型
     */
    PARA_TYPE("paraType", "参数类型",true),

    /**
     * 是否必填
     */
    NECESSARY("necessary", "是否必填",true),

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

    CreateSqlRequestParamHeaderInfoEnum(String key, String value, boolean required) {
        this.key = key;
        this.value = value;
        this.required = required;
    }

    public static CreateSqlRequestParamHeaderInfoEnum getVal(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        for (CreateSqlRequestParamHeaderInfoEnum enums : CreateSqlRequestParamHeaderInfoEnum.values()) {
            if (value.equals(enums.value)) {
                return enums;
            }
        }
        return null;
    }

    public static LinkedList<Map<String, Object>> getAllValue() {
        LinkedList<Map<String, Object>> valList = new LinkedList<>();

        for (CreateSqlRequestParamHeaderInfoEnum enums : CreateSqlRequestParamHeaderInfoEnum.values()) {
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
