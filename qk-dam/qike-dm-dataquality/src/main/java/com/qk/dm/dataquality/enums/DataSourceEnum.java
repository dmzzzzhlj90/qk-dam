package com.qk.dm.dataquality.enums;

public enum DataSourceEnum {
    hive(1,"have"),
    mysql(2,"mysql");

    private Integer code;
    private String name;

    DataSourceEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String fromValue(Integer code) {
        for (DataSourceEnum b : DataSourceEnum.values()) {
            if (b.code.equals(code)) {
                return b.getName();
            }
        }
        throw new IllegalArgumentException("Unexpected id '" + code + "'");
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
