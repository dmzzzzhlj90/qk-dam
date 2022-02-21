package com.qk.dm.dataservice.constant;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 新建API_排序表头信息
 *
 * @author wjq
 * @date 20220221
 * @since 1.0.0
 */
public enum CreateOrderParaHeaderInfoEnum {

    /**
     * 序号
     */
    ORDER_NUM("orderNum", "序号"),

    /**
     * 字段名称
     */
    COLUMN_NAME("columnName", "字段名称"),

    /**
     * 是否可选
     */
    PARA_NAME("paraName", "参数名称"),

    /**
     * 排序方式
     */
    OPTIONAL("optional", "是否可选"),

    /**
     * 序号
     */
    ORDER_TYPE("orderType", "排序方式"),

    /**
     * 描述
     */
    DESCRIPTION("description", "描述");


    private String code;
    private String value;

    CreateOrderParaHeaderInfoEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static CreateOrderParaHeaderInfoEnum getVal(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        for (CreateOrderParaHeaderInfoEnum enums : CreateOrderParaHeaderInfoEnum.values()) {
            if (value.equals(enums.value)) {
                return enums;
            }
        }
        return null;
    }

    public static Map<String, String> getAllValue() {
        Map<String, String> val = new LinkedHashMap<>();
        for (CreateOrderParaHeaderInfoEnum enums : CreateOrderParaHeaderInfoEnum.values()) {
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
