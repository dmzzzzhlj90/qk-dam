package com.qk.dm.dataingestion.enums;

/**
 * datax 内部数据类型
 */
public enum DataType {
    LONG("LONG"),
    DOUBLE("DOUBLE"),
    STRING("STRING"),
    BOOL("BOOL"),
    DATE("DATE"),
    BYTES("BYTES");

    private final String dataType;

    DataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataType() {
        return dataType;
    }
}
