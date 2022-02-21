package com.qk.dm.dataservice.constant;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 注册API_后端请求参数表头信息
 *
 * @author wjq
 * @date 20220221
 * @since 1.0.0
 */
public enum RegisterBackendParaHeaderInfoEnum {

    /**
     * 入参名称
     */
    PARA_NAME("paraName", "入参名称"),

    /**
     * 入参位置
     */
    PARA_POSITION("paraPosition", "入参位置"),

    /**
     * 入参类型
     */
    PARA_TYPE("paraType", "入参类型"),

    /**
     * 后端参数名称
     */
    BACKEND_PARA_NAME("backendParaName", "后端参数名称"),

    /**
     * 后端参数位置
     */
    BACKEND_PARA_POSITION("backendParaPosition", "后端参数位置");


    private String code;
    private String value;

    RegisterBackendParaHeaderInfoEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static RegisterBackendParaHeaderInfoEnum getVal(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        for (RegisterBackendParaHeaderInfoEnum enums : RegisterBackendParaHeaderInfoEnum.values()) {
            if (value.equals(enums.value)) {
                return enums;
            }
        }
        return null;
    }

    public static Map<String, String> getAllValue() {
        Map<String, String> val = new LinkedHashMap<>();
        for (RegisterBackendParaHeaderInfoEnum enums : RegisterBackendParaHeaderInfoEnum.values()) {
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
