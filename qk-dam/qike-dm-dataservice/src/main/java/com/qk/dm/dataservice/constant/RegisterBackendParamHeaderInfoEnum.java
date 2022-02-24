package com.qk.dm.dataservice.constant;

import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 注册API_后端请求参数表头信息
 *
 * @author wjq
 * @date 20220221
 * @since 1.0.0
 */
public enum RegisterBackendParamHeaderInfoEnum {

    /**
     * 入参名称
     */
    PARA_NAME("paraName", "入参名称",true),

    /**
     * 入参位置
     */
    PARA_POSITION("paraPosition", "入参位置",true),

    /**
     * 入参类型
     */
    PARA_TYPE("paraType", "入参类型",true),

    /**
     * 后端参数名称
     */
    BACKEND_PARA_NAME("backendParaName", "后端参数名称",true),

    /**
     * 后端参数位置
     */
    BACKEND_PARA_POSITION("backendParaPosition", "后端参数位置",true),

    /**
     * 描述
     */
    DESCRIPTION("description", "描述",false);

    private String key;
    private String value;
    private boolean required;

    RegisterBackendParamHeaderInfoEnum(String key, String value, boolean required) {
        this.key = key;
        this.value = value;
        this.required = required;
    }

    public static RegisterBackendParamHeaderInfoEnum getVal(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        for (RegisterBackendParamHeaderInfoEnum enums : RegisterBackendParamHeaderInfoEnum.values()) {
            if (value.equals(enums.value)) {
                return enums;
            }
        }
        return null;
    }

    public static LinkedList<Map<String, Object>> getAllValue() {
        LinkedList<Map<String, Object>> valList = new LinkedList<>();

        for (RegisterBackendParamHeaderInfoEnum enums : RegisterBackendParamHeaderInfoEnum.values()) {
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
