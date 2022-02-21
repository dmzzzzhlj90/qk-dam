package com.qk.dm.dataservice.constant;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 新建API_请求参数表头信息
 *
 * @author wjq
 * @date 20220221
 * @since 1.0.0
 */
public enum CreateRequestParaHeaderInfoEnum {

    /**
     * 绑定参数
     */
    PARA_NAME("paraName", "绑定参数"),

    /**
     * 绑定字段
     */
    MAPPING_NAME("mappingName", "绑定字段"),

    /**
     * 操作符
     */
    CONDITION_TYPE("conditionType", "操作符"),

    /**
     * 后端参数
     */
    BACKEND_PARA_NAME("backendParaName", "后端参数"),

    /**
     * 后端参数位置
     */
    BACKEND_PARA_POSITION("backendParaPosition", "后端参数位置");


    private String code;
    private String value;

    CreateRequestParaHeaderInfoEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static CreateRequestParaHeaderInfoEnum getVal(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        for (CreateRequestParaHeaderInfoEnum enums : CreateRequestParaHeaderInfoEnum.values()) {
            if (value.equals(enums.value)) {
                return enums;
            }
        }
        return null;
    }

    public static Map<String, String> getAllValue() {
        Map<String, String> val = new LinkedHashMap<>();
        for (CreateRequestParaHeaderInfoEnum enums : CreateRequestParaHeaderInfoEnum.values()) {
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
