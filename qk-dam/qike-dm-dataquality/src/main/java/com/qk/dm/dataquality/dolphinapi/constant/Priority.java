package com.qk.dm.dataquality.dolphinapi.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum Priority {
    /**
     * 0 highest priority
     * 1 higher priority
     * 2 medium priority
     * 3 lower priority
     * 4 lowest priority
     */
    HIGHEST(0, "HIGHEST"),
    HIGH(1, "HIGH"),
    MEDIUM(2, "MEDIUM"),
    LOW(3, "LOW"),
    LOWEST(4, "LOWEST");

    Priority(int code, String descp){
        this.code = code;
        this.descp = descp;
    }

    @EnumValue
    private final int code;
    private final String descp;

    public int getCode() {
        return code;
    }

    public String getDescp() {
        return descp;
    }
}
