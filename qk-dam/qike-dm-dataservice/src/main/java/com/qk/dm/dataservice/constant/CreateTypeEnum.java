package com.qk.dm.dataservice.constant;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 新建Api 类型
 *
 * @author wjq
 * @date 20220221
 * @since 1.0.0
 */
public enum CreateTypeEnum {

    /**
     * API类型-新建API_配置方式
     */
    CREATE_API_CONFIG_TYPE("CREATE-API-CONFIG-TYPE","API类型-新建API_配置方式"),

    /**
     * API类型-新建API_取数方式
     */
    CREATE_API_SQL_SCRIPT_TYPE("CREATE-API-SQL-SCRIPT-TYPE","API类型-新建API_取数方式");

    private String code;
    private String value;

    CreateTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static CreateTypeEnum getVal(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        for (CreateTypeEnum enums : CreateTypeEnum.values()) {
            if (value.equals(enums.value)) {
                return enums;
            }
        }
        return null;
    }

    public static Map<String, String> getAllValue() {
        Map<String, String> val = new LinkedHashMap<>();
        for (CreateTypeEnum enums : CreateTypeEnum.values()) {
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
