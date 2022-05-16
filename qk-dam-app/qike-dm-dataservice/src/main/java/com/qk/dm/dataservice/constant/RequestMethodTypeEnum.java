package com.qk.dm.dataservice.constant;

import org.apache.commons.compress.utils.Lists;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 请求方法
 *
 * @author wjq
 * @date 2022/05/14
 * @since 1.0.0
 */
public enum RequestMethodTypeEnum {

    /**
     * GET
     */
    GET("GET"),

    /**
     * POST
     */
    POST("POST"),

    /**
     * PUT
     */
    PUT("PUT"),

    /**
     * DELETE
     */
    DELETE("DELETE");

    private String value;

    RequestMethodTypeEnum(String value) {
        this.value = value;
    }

    public static RequestMethodTypeEnum getVal(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        for (RequestMethodTypeEnum enums : RequestMethodTypeEnum.values()) {
            if (value.equals(enums.value)) {
                return enums;
            }
        }
        return null;
    }

    public static List<String> getAllValue() {
        List<String> val = Lists.newArrayList();
        for (RequestMethodTypeEnum enums : RequestMethodTypeEnum.values()) {
            val.add(enums.value);
        }
        return val;
    }

    public String getValue() {
        return value;
    }
}
