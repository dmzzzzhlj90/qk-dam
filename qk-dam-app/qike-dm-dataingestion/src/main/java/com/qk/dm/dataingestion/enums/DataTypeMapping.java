package com.qk.dm.dataingestion.enums;

import com.google.common.collect.Maps;
import com.qk.dam.commons.enums.DataTypeEnum;
import com.qk.dam.commons.enums.MysqlDataTypeEnum;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * 数据类型映射
 */
public enum DataTypeMapping {

    STRING(List.of("varchar","char","tinytext","text","mediumtext","longtext",
            "tinyblob","mediumblob","blob","longblob","varbinary"), DataType.STRING.getDataType(),List.of("string")),
    BOOL(List.of("int","tinyint","smallint","mediumint","int","bigint","year"), DataType.BOOL.getDataType(),List.of("boolean")),
    DOUBLE(List.of("float","double","decimal"), DataType.DOUBLE.getDataType(),List.of("float","double","decimal")),
    LONG(List.of("int","tinyint","smallint","mediumint","int","bigint","year"), DataType.LONG.getDataType(),
            List.of("tinyint","smallint","int","bigint")),
    DATE(List.of("date","datetime","timestamp","time"),DataType.DATE.getDataType(),List.of("date","timestamp"));


    private final List<String> mysqlTypeList;
    private final String dataType;
    private final List<String> hiveTypeList;

    public static Map<String,List<String>> getDataType(String sourceConnectType, String targetConnectType){
        Map<String,List<String>> map = Maps.newHashMap();
        if(Objects.equals(sourceConnectType, IngestionType.MYSQL.getType())&&
                Objects.equals(targetConnectType, IngestionType.HIVE.getType())){
           return mysqlToHive();
        }else if(Objects.equals(sourceConnectType, IngestionType.HIVE.getType())&&
                Objects.equals(targetConnectType, IngestionType.MYSQL.getType())){
           return hiveToMysql();
        }else if(Objects.equals(sourceConnectType, IngestionType.MYSQL.getType())&&
                Objects.equals(targetConnectType, IngestionType.MYSQL.getType())){
          return mysqlToMysql();
        }else if(Objects.equals(sourceConnectType, IngestionType.HIVE.getType())&&
                Objects.equals(targetConnectType, IngestionType.HIVE.getType())){
            return hiveToHive();
        }
        return map;
    }


    public static Map<String,List<String>> mysqlToHive(){
        Map<String,List<String>> map = Maps.newHashMap();
        List<MysqlDataTypeEnum> mysqlList = MysqlDataTypeEnum.getList();
        mysqlList.forEach(t->{
            DataTypeMapping hiveType = Stream.of(values()).filter(e -> e.mysqlTypeList.contains(t.getCode())).
                    findAny().orElse(null);
            map.put(t.getCode(),hiveType==null?List.of():hiveType.getHiveTypeList());
        });
        return map;
    }

    public static Map<String,List<String>> hiveToMysql(){
        Map<String,List<String>> map = Maps.newHashMap();
        List<DataTypeEnum> hiveList = DataTypeEnum.getList();
        hiveList.forEach(t-> {
            DataTypeMapping mysqlType = Stream.of(values()).filter(e -> e.hiveTypeList.contains(t.getCode().toLowerCase())).
                    findAny().orElse(null);
            map.put(t.getCode().toLowerCase(), mysqlType == null ? List.of() : mysqlType.getMysqlTypeList());
        });
        return map;
    }
    public static Map<String,List<String>> mysqlToMysql(){
        Map<String,List<String>> map = Maps.newHashMap();
        List<MysqlDataTypeEnum> mysqlList = MysqlDataTypeEnum.getList();
        mysqlList.forEach(t->{
            DataTypeMapping mysql = Stream.of(values()).filter(e -> e.mysqlTypeList.contains(t.getCode())).
                    findAny().orElse(null);
            map.put(t.getCode(),mysql==null?List.of():mysql.getMysqlTypeList());
        });
        return map;
    }

    public static Map<String,List<String>> hiveToHive(){
        Map<String,List<String>> map = Maps.newHashMap();
        List<DataTypeEnum> hiveList = DataTypeEnum.getList();
        hiveList.forEach(t-> {
            DataTypeMapping hiveType = Stream.of(values()).filter(e -> e.hiveTypeList.contains(t.getCode().toLowerCase())).
                    findAny().orElse(null);
            map.put(t.getCode().toLowerCase(), hiveType == null ? List.of() : hiveType.getHiveTypeList());
        });
        return map;
    }

    DataTypeMapping(List<String> mysqlTypeList,String dataType, List<String> hiveTypeList) {
        this.mysqlTypeList = mysqlTypeList;
        this.dataType = dataType;
        this.hiveTypeList = hiveTypeList;
    }

    public List<String> getMysqlTypeList() {
        return mysqlTypeList;
    }

    public String getDataType() {
        return dataType;
    }

    public List<String> getHiveTypeList() {
        return hiveTypeList;
    }
}
