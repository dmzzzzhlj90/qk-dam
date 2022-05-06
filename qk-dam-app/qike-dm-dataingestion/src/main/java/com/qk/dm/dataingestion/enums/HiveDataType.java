package com.qk.dm.dataingestion.enums;


import com.qk.dm.dataingestion.vo.ColumnVO;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * hive所有数据类型
 */
public enum HiveDataType {
    /**
     * 字符类型(STRING)
     */
    STRING("string","string"),

    /**
     * 双精度(DOUBLE)
     */
    DOUBLE("double","double"),

    /**
     * 长整型(BIGINT)
     */
    BIGINT("bigint","bigint"),

    /**
     * 布尔类型(BOOLEAN)
     */
    BOOLEAN("boolean","boolean"),

    /**
     * 高精度(DECIMAL)
     */
    DECIMAL("decimal","decimal"),

    /**
     * 日期类型(DATE)
     */
    DATE("date","date"),

    /**
     * 时间戳类型(TIMESTAMP)
     */
    TIMESTAMP("timestamp","timestamp"),

    INT("int","int"),
    TINYINT("tinyint","tinyint"),
    SMALLINT("smallint","smallint"),
    FLOAT("float","float"),
    CHAR("char","char"),
    VARCHAR("varchar","varchar");

    public static List<HiveDataType> getList(){
        return Arrays.stream(values()).collect(Collectors.toList());
    }

    public static Map<String,String> getAllType(){
        return Arrays.stream(values()).collect(Collectors.
                toMap(HiveDataType::getCode, HiveDataType::getValue));
    }

    public static List<ColumnVO.Column> checkColumn(List<ColumnVO.Column> columnList){
      return List.of();
    }

    private final String code;
    private final String value;

    HiveDataType(String code, String value) {
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
