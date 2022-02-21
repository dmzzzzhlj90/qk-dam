package com.qk.dm.dataservice.constant;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 新建API_排序方式
 *
 * @author wjq
 * @date 20220221
 * @since 1.0.0
 */
public enum CreateParasSortStyleEnum {

    /**
     * 升序
     */
    ASC("REGISTER-API", "升序"),

    /**
     * 新建API
     */
    DESC("DESC", "降序"),

    /**
     * 自定义
     */
    CUSTOM("CUSTOM", "自定义");

    private String code;
    private String value;

    CreateParasSortStyleEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static CreateParasSortStyleEnum getVal(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        for (CreateParasSortStyleEnum enums : CreateParasSortStyleEnum.values()) {
            if (value.equals(enums.value)) {
                return enums;
            }
        }
        return null;
    }

    public static Map<String, String> getAllValue() {
        Map<String, String> val = new HashMap<>();
        for (CreateParasSortStyleEnum enums : CreateParasSortStyleEnum.values()) {
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
