package com.qk.dm.dataquality.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 执行SQL扫描方式
 *
 * @author wjq
 * @date 2021/12/04
 * @since 1.0.0
 */
public enum ScanTypeEnum {

    /**
     * 扫描范围 "FULL_TABLE":"全表", "CONDITION":"条件";
     */
    FULL_TABLE("FULL_TABLE", "全表"),
    CONDITION("CONDITION", "条件");

    private String code;
    private String name;

    ScanTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static Map<String, String> getAllValue() {
        Map<String, String> val = new HashMap<>();
        for (ScanTypeEnum enums : ScanTypeEnum.values()) {
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
