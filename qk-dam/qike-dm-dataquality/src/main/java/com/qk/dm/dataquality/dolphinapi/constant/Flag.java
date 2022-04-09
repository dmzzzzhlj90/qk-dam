package com.qk.dm.dataquality.dolphinapi.constant;

/**
 * 运行标志
 *
 * @author wjq
 * @date 2021/12/21
 * @since 1.0.0
 */
public enum Flag {
    /**
     * 0 no
     * 1 yes
     */
    NO(0, "NO"),
    YES(1, "YES");

    Flag(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private final int code;
    private final String desc;

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
