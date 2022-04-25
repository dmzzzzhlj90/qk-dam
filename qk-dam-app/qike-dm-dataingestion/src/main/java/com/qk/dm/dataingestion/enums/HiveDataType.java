package com.qk.dm.dataingestion.enums;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * hive所有数据类型
 */
public enum HiveDataType {
    /**
     * 字符类型(STRING)
     */
    STRING("string"),

    /**
     * 双精度(DOUBLE)
     */
    DOUBLE("double"),

    /**
     * 长整型(BIGINT)
     */
    BIGINT("bigint"),

    /**
     * 布尔类型(BOOLEAN)
     */
    BOOLEAN("boolean"),

    /**
     * 高精度(DECIMAL)
     */
    DECIMAL("decimal"),

    /**
     * 日期类型(DATE)
     */
    DATE("date"),

    /**
     * 时间戳类型(TIMESTAMP)
     */
    TIMESTAMP("timestamp"),

    INT("int"),
    TINYINT("tinyint"),
    SMALLINT("smallint"),
    FLOAT("float"),
    CHAR("char"),
    VARCHAR("varchar");

    public static List<HiveDataType> getList(){
        return Arrays.stream(values()).collect(Collectors.toList());
    }



    private final String code;

    HiveDataType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
