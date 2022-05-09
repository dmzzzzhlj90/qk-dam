package com.qk.dm.dataingestion.enums;

import com.qk.dm.dataingestion.vo.ColumnVO;
import org.springframework.util.CollectionUtils;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *mysql字段类型
 * @author wangzp
 * @date 2022/04/20 19:58
 * @since 1.0.0
 */
public enum MysqlDataType {

    TINYINT("tinyint","tinyint"),
    SMALLINT("smallint","smallint"),
    MEDIUMINT("mediumint","mediumint"),
    INT("int","int"),
    BIGINT("bigint","bigint"),
    DOUBLE("double","double"),
    FLOAT("float","float"),
    DECIMAL("decimal","decimal"),
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
                toMap(MysqlDataType::getCode, MysqlDataType::getValue));
    }


    public static List<MysqlDataType> getList(){
        return Arrays.stream(values()).collect(Collectors.toList());
    }

    /**
     * 校验mysql数据类型是否合法
     * @param columnList
     * @return
     */
    public static List<ColumnVO.Column> checkColumn(List<ColumnVO.Column> columnList){
        if(CollectionUtils.isEmpty(columnList)){return List.of();}

        return  columnList.stream()
                .filter(e->!isMatch(e.getDataType())&&!List.of("int","bigint",
                        "date","text","year","time","blob").contains(e.getDataType()))
                .collect(Collectors.toList());

    }

    /**
     * 判断指定内容是否匹配正则
     * @param content 内容
     * @return
     */
    public static boolean isMatch(String content) {

        return Pattern.compile("^[a-z].*\\([0-9]+\\)").matcher(content).matches();
    }



    private final String code;
    private final String value;

    MysqlDataType(String code, String value) {
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
