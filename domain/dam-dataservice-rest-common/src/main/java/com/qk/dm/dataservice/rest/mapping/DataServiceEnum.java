package com.qk.dm.dataservice.rest.mapping;

/**
 * @author zhudaoming
 */

public enum DataServiceEnum {
    /**
     * path 匹配规则
     */
    MATCH_ALL("/**");

    private final String value;

    DataServiceEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
