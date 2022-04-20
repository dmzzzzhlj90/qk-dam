package com.qk.dm.dataingestion.enums;

import java.util.stream.Stream;

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

    public static String getReader(String name){
        return Stream.of(values()).filter(e -> e.getName().equals(name+"Reader")).
                findAny().orElse(HIVE_READER).getType();
    }
    public static String getWriter(String name){
        return Stream.of(values()).filter(e -> e.getName().equals(name+"Writer")).
                findAny().orElse(HIVE_WRITER).getType();
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
