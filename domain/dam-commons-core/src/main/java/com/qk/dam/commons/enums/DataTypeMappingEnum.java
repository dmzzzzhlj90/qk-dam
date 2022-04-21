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


    public static Map<String,String> getDataType(String name, String code){
        String dataType = Stream.of(values()).filter(e -> e.getSourceName().equals(name)
                &&e.getSourceCode().equals(code)).
                findAny().orElse(EMPTY).getTargetCode();
        if(StringUtils.isEmpty(dataType)){
            dataType = Stream.of(values()).filter(e -> e.targetName.equals(name)
                    &&e.getTargetCode().equals(code)).
                    findAny().orElse(EMPTY).getSourceCode();
        }
        return Map.of("dataType",dataType);
    }

    private final String sourceName;
    private final String sourceCode;
    private final String targetName;
    private final String targetCode;

    DataTypeMappingEnum(String sourceName, String sourceCode, String targetName, String targetCode) {
        this.sourceName = sourceName;
        this.sourceCode = sourceCode;
        this.targetName = targetName;
        this.targetCode = targetCode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public String getTargetName() {
        return targetName;
    }

    public String getTargetCode() {
        return targetCode;
    }
}
