package com.qk.dm.dataingestion.model;

import java.util.stream.Stream;

public enum IngestionType {
    /**
     * 支持的数据引入类型
     */
    HIVE("hive"),
    MYSQL("mysql"),
    HDFS("hdfs"),
    ;
    private final String type;

    IngestionType(String type) {
        this.type = type;
    }

    public static IngestionType getVal(String value) {
        return Stream.of(values()).filter(t -> t.type.equalsIgnoreCase(value)).findAny().orElse(null);
    }


    @Override
    public String toString() {
        return type;
    }
}
