package com.qk.dm.dataservice.constant;

import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 新建API_配置方式_参数设置
 *
 * @author wjq
 * @date 20220223
 * @since 1.0.0
 */
public enum CreateConfigParamHeaderEnum {

    /**
     * 字段名称
     */
    COLUMN_NAME("columnName", "字段名称"),

    /**
     * 字段类型
     */
    COLUMN_TYPE("columnType", "字段类型"),

    /**
     * 字段描述
     */
    CUSTOM_DESCRIPTION("customDescription", "字段描述"),

    /**
     * 请求参数
     */
    REQUEST_PARAM("requestParam", "请求参数"),

    /**
     * 返回参数
     */
    RESPONSE_PARAM("responseParam", "返回参数"),

    /**
     * 排序参数
     */
    SORT_PARAM("sortParam", "排序参数");

    private String key;
    private String value;

    CreateConfigParamHeaderEnum(String code, String value) {
        this.key = code;
        this.value = value;
    }

    public static CreateConfigParamHeaderEnum getVal(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        for (CreateConfigParamHeaderEnum enums : CreateConfigParamHeaderEnum.values()) {
            if (value.equals(enums.value)) {
                return enums;
            }
        }
        return null;
    }

    public static LinkedList<Map<String, Object>> getAllValue() {
        LinkedList<Map<String, Object>> valList = new LinkedList<>();

        for (CreateConfigParamHeaderEnum enums : CreateConfigParamHeaderEnum.values()) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put(DasConstant.PARAM_KEY, enums.key);
            map.put(DasConstant.PARAM_VALUE, enums.value);
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
}
