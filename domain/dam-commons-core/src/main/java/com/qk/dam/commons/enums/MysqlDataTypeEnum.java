package com.qk.dam.commons.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * mysql数据类型
 */
public enum MysqlDataTypeEnum {

    TINYINT("tinyint","tinyint"),
    SMALLINT("smallint","smallint"),
    MEDIUMINT("mediumint","mediumint"),
    INT("int","int"),
    INTEGER("integer","integer"),
    BIGINT("bigint","bigint"),
    BIT("bit","bit"),
    REAL("real","real"),
    DOUBLE("double","double"),
    FLOAT("float","float"),
    DECIMAL("decimal","decimal"),
    NUMERIC("numeric","numeric"),
    CHAR("char","char"),
    VARCHAR("varchar","varchar"),
    DATE("date","date"),
    TIME("time","time"),
    YEAR("year","year"),
    TIMESTAMP("timestamp","timestamp"),
    DATETIME("datetime","datetime"),
    TINYBLOB("tinyblob","tinyblob"),
    BLOB("blob","blob"),
    TINYTEXT("tinytext","tinytext"),
    TEXT("text","text");

    public static Map<String,String> getAllType(){
        return Arrays.stream(values()).collect(Collectors.
                toMap(MysqlDataTypeEnum::getCode, MysqlDataTypeEnum::getValue));
    }





    private String code;
    private String value;

    MysqlDataTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
