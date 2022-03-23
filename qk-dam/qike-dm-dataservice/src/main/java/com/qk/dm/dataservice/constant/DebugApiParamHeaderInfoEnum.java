package com.qk.dm.dataservice.constant;

import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * API调试__请求参数表头信息
 *
 * @author wjq
 * @date 2022/03/04
 * @since 1.0.0
 */
public enum DebugApiParamHeaderInfoEnum {

    /**
     * 参数名称
     */
    PARA_NAME("paraName", "参数名称"),

    /**
     * 参数类型
     */
    PARA_TYPE("paraType", "参数类型"),

    /**
     * 是否必填
     */
    NECESSARY("necessary", "是否必填"),

    /**
     * 值
     */
    VALUE("value", "值");

    private String key;
    private String value;

    DebugApiParamHeaderInfoEnum(String code, String value) {
        this.key = code;
        this.value = value;
    }

    public static DebugApiParamHeaderInfoEnum getVal(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        for (DebugApiParamHeaderInfoEnum enums : DebugApiParamHeaderInfoEnum.values()) {
            if (value.equals(enums.value)) {
                return enums;
            }
        }
        return null;
    }

    public static LinkedList<Map<String, Object>> getAllValue() {
        LinkedList<Map<String, Object>> valList = new LinkedList<>();

        for (DebugApiParamHeaderInfoEnum enums : DebugApiParamHeaderInfoEnum.values()) {
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
