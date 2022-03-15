package com.qk.dm.dataingestion.model;

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


    @Override
    public String toString() {
        return type;
    }
}
