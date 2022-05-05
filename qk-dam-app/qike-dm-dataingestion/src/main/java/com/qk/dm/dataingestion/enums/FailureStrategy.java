package com.qk.dm.dataingestion.enums;

import java.util.stream.Stream;

/**
 * 调度定时的失败策略
 */
public enum FailureStrategy {

    CONTINUE("CONTINUE","继续"),
    END("END","结束");


    public static FailureStrategy getVal(String code) {
        return Stream.of(values()).filter(t -> t.code.equalsIgnoreCase(code)).findAny().orElse(null);
    }

    private final String code;
    private final String name;

    FailureStrategy(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
