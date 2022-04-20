package com.qk.dm.dataservice.constant;

import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * API同步方式
 *
 * @author wjq
 * @date 20220228
 * @since 1.0.0
 */
public enum ApiSyncTypeEnum {

    /**
     * 仅更新数据
     */
    ONLY_UPDATE("ONLY_UPDATE", "仅更新数据"),

    /**
     * 仅添加数据
     */
    ONLY_ADD("ONLY_ADD", "仅添加数据"),

    /**
     * 更新,添加数据
     */
    UPDATE_AND_ADD("UPDATE_AND_ADD", "更新,添加数据"),

    /**
     * 忽略更新,添加数据
     */
    OMIT_UPDATE_ONLY_ADD("OMIT_UPDATE_ONLY_ADD", "忽略更新,添加数据"),

    /**
     * 下线操作
     */
    OFFLINE("OFFLINE", "下线操作");

    private String code;
    private String value;

    ApiSyncTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static ApiSyncTypeEnum getVal(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        for (ApiSyncTypeEnum enums : ApiSyncTypeEnum.values()) {
            if (value.equals(enums.value)) {
                return enums;
            }
        }
        return null;
    }

    public static Map<String, String> getAllValue() {
        Map<String, String> val = new LinkedHashMap<>();
        for (ApiSyncTypeEnum enums : ApiSyncTypeEnum.values()) {
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
