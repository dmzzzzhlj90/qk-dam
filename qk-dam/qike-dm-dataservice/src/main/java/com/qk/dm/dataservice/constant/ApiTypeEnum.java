package com.qk.dm.dataservice.constant;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * API 类型
 *
 * @author wjq
 * @date 20220221
 * @since 1.0.0
 */
public enum ApiTypeEnum {

    /**
     * 注册API
     */
    REGISTER_API("REGISTER-API", "注册API"),

    /**
     * 新建API
     */
    CREATE_API("CREATE-API", "新建API");

    private String code;
    private String value;

    ApiTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static ApiTypeEnum getVal(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        for (ApiTypeEnum enums : ApiTypeEnum.values()) {
            if (value.equals(enums.value)) {
                return enums;
            }
        }
        return null;
    }

    public static Map<String, String> getAllValue() {
        Map<String, String> val = new LinkedHashMap<>();
        for (ApiTypeEnum enums : ApiTypeEnum.values()) {
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
