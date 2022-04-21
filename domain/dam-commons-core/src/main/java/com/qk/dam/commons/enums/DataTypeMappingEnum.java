package com.qk.dam.commons.enums;

import org.springframework.util.StringUtils;
import java.util.Map;
import java.util.stream.Stream;

/**
 * hive与mysql字段类型映射
 * @author wangzp
 * @date 2022/04/20 19:53
 * @since 1.0.0
 */
public enum DataTypeMappingEnum {

    STRING("hive","STRING","mysql","varchar"),
    DOUBLE("hive","DOUBLE","mysql","double"),
    BIGINT("hive","BIGINT","mysql","bigint"),
    BOOLEAN("hive","BOOLEAN","mysql","char(1)"),
    DECIMAL("hive","DECIMAL","mysql","decimal"),
    DATE("hive","DATE","mysql","date"),
    TIMESTAMP("hive","TIMESTAMP","mysql","timestamp"),
    EMPTY("","","","");


    public static Map<String,String> getDataType(String name, String type){
        String dataType = Stream.of(values()).filter(e -> e.getSourceName().equals(name)
                &&e.getSourceType().equals(type)).
                findAny().orElse(EMPTY).getTargetType();
        if(StringUtils.isEmpty(dataType)){
            dataType = Stream.of(values()).filter(e -> e.targetName.equals(name)
                    &&e.getTargetType().equals(type)).
                    findAny().orElse(EMPTY).getSourceType();
        }
        return Map.of("dataType",dataType);
    }

    private final String sourceName;
    private final String sourceType;
    private final String targetName;
    private final String targetType;

    DataTypeMappingEnum(String sourceName, String sourceType, String targetName, String targetType) {
        this.sourceName = sourceName;
        this.sourceType = sourceType;
        this.targetName = targetName;
        this.targetType = targetType;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getSourceType() {
        return sourceType;
    }

    public String getTargetName() {
        return targetName;
    }

    public String getTargetType() {
        return targetType;
    }
}
