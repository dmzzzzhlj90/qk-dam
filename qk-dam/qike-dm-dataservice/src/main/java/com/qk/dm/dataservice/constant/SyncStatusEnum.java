package com.qk.dm.dataservice.constant;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 数据服务同步状态
 *
 * @author wjq
 * @date 20210907
 * @since 1.0.0
 */
public enum SyncStatusEnum {

    /**
     * 未上传
     */
    NO_UPLOAD("0", "未上传"),

    /**
     * 上传失败
     */
    FAIL_UPLOAD("1", "上传失败"),

    /**
     * 已上传
     */
    SUCCESS_UPLOAD("2", "已上传");

    private String code;
    private String name;

    SyncStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static SyncStatusEnum getVal(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        for (SyncStatusEnum enums : SyncStatusEnum.values()) {
            if (name.equals(enums.name)) {
                return enums;
            }
        }
        return null;
    }

    public static Map<String, String> getAllValue() {
        Map<String, String> val = new LinkedHashMap<>();
        for (SyncStatusEnum enums : SyncStatusEnum.values()) {
            val.put(enums.code, enums.name);
        }
        return val;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
