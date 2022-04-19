package com.qk.dm.dataingestion.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 文件类型
 */
public enum HiveFileType {
    TEXT("text","text"),
    ORC("orc","orc"),
    RC("rc","rc"),
    SEQ("seq","seq"),
    CSV("csv","csv"),
    ;

    private final String name;
    private final String desc;

    HiveFileType(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public static Map<String,String> getMap(){
        return Arrays.stream(values()).collect(Collectors.toMap(HiveFileType::getName, HiveFileType::getDesc));
    }

}
