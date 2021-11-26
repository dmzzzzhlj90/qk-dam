package com.qk.dm.dataquality.dolphinapi.constant;

/**
 * resource type
 *
 * @author wjq
 * @date 2021/11/24
 * @since 1.0.0
 */
public enum ResourceType {
    /**
     * 0 file, 1 udf
     */
    FILE(0, "file"),
    UDF(1, "udf");


    ResourceType(int code, String descp) {
        this.code = code;
        this.descp = descp;
    }

    private final int code;
    private final String descp;

    public int getCode() {
        return code;
    }

    public String getDescp() {
        return descp;
    }
}
