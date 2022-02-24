package com.qk.dm.dataservice.constant;

import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 新建API_排序表头信息
 *
 * @author wjq
 * @date 20220221
 * @since 1.0.0
 */
public enum CreateOrderParamHeaderInfoEnum {

    /**
     * 序号
     */
    ORDER_NUM("orderNum", "序号",true),

    /**
     * 参数名称
     */
    PARA_NAME("paraName", "参数名称",true),

    /**
     * 字段名称
     */
    COLUMN_NAME("columnName", "字段名称",true),

    /**
     * 是否可选
     */
    OPTIONAL("optional", "是否可选",true),

    /**
     * 排序方式
     */
    ORDER_TYPE("orderType", "排序方式",true),

    /**
     * 描述
     */
    DESCRIPTION("description", "描述",false);


    private String key;
    private String value;
    private boolean required;

    CreateOrderParamHeaderInfoEnum(String key, String value, boolean required) {
        this.key = key;
        this.value = value;
        this.required = required;
    }

    public static CreateOrderParamHeaderInfoEnum getVal(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        for (CreateOrderParamHeaderInfoEnum enums : CreateOrderParamHeaderInfoEnum.values()) {
            if (value.equals(enums.value)) {
                return enums;
            }
        }
        return null;
    }

    public static LinkedList<Map<String, Object>> getAllValue() {
        LinkedList<Map<String, Object>> valList = new LinkedList<>();

        for (CreateOrderParamHeaderInfoEnum enums : CreateOrderParamHeaderInfoEnum.values()) {
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
