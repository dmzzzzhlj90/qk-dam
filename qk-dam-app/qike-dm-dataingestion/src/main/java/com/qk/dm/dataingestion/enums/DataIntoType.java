package com.qk.dm.dataingestion.enums;


public enum DataIntoType {
    MYSQL_READER("mysqlReader","mysqlreader"),
    MYSQL_WRITER("mysqlWriter","mysqlwriter"),
    HIVE_READER("hiveReader","hdfsreader"),
    HIVE_WRITER("hiveWriter","hdfswriter"),
    ;

    private final String name;
    private final String type;

    DataIntoType(String name,String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
