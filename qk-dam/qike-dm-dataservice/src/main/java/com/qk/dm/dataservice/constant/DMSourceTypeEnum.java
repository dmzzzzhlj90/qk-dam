package com.qk.dm.dataservice.constant;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * DM 取数方式类型
 *
 * @author wjq
 * @date 20220221
 * @since 1.0.0
 */
public enum DMSourceTypeEnum {

    /**
     * 配置方式
     */
    CREATE_API_CONFIG_TYPE("CREATE-API-CONFIG-TYPE", "配置方式"),

    /**
     * SQL脚本方式
     */
    CREATE_API_SQL_SCRIPT_TYPE("CREATE-API-SQL-SCRIPT-TYPE", "SQL脚本方式");

    private String code;
    private String value;

    DMSourceTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static DMSourceTypeEnum getVal(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        for (DMSourceTypeEnum enums : DMSourceTypeEnum.values()) {
            if (value.equals(enums.value)) {
                return enums;
            }
        }
        return null;
    }

    public static Map<String, String> getAllValue() {
        Map<String, String> val = new HashMap<>();
        for (DMSourceTypeEnum enums : DMSourceTypeEnum.values()) {
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
