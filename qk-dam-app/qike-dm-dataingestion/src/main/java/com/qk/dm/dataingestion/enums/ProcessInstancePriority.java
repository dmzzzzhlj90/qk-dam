package com.qk.dm.dataingestion.enums;

import java.util.stream.Stream;

/**
 * 流程实例优先级
 */
public enum ProcessInstancePriority {

    HIGHEST("HIGHEST","最高"),
    HIGH("HIGH","高"),
    MEDIUM("MEDIUM","中等"),
    LOW("LOW","低"),
    LOWEST("LOWEST","最低");

    String code;
    String name;

    public static ProcessInstancePriority getVal(String code) {
        return Stream.of(values()).filter(t -> t.code.equalsIgnoreCase(code)).findAny().orElse(null);
    }

    ProcessInstancePriority(String code, String name) {
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
