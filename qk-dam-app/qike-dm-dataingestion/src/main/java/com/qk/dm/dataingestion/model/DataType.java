package com.qk.dm.dataingestion.model;

import com.google.common.collect.Maps;
import com.qk.dam.commons.enums.DataTypeEnum;
import com.qk.dam.commons.enums.MysqlDataTypeEnum;
import com.qk.dm.dataingestion.enums.IngestionType;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class DataType {
    /**
     * datax数据类型
     */
    public enum Type {
       LONG("LONG"),
        DOUBLE("DOUBLE"),
        STRING("STRING"),
        BOOL("BOOL"),
        DATE("DATE"),
        BYTES("BYTES");

        private final String dataType;

        Type(String dataType) {
            this.dataType = dataType;
        }

        public String getDataType() {
            return dataType;
        }
    }

    public enum TypeMapper{
        STRING(List.of("varchar","char","tinytext","text","mediumtext","longtext",
                "tinyblob","mediumblob","blob","longblob","varbinary"),Type.STRING.getDataType(),List.of("STRING","VARCHAR","CHAR")),
        BOOL(List.of("int","tinyint","smallint","mediumint","int","bigint","year"),Type.BOOL.getDataType(),List.of("BOOLEAN")),
        DOUBLE(List.of("float","double","decimal"),Type.DOUBLE.getDataType(),List.of("FLOAT","DOUBLE")),
        LONG(List.of("int","tinyint","smallint","mediumint","int","bigint","year"),Type.LONG.getDataType(),
                List.of("TINYINT","SMALLINT","INT","BIGINT")),
        DATE(List.of("date","datetime","timestamp","time"),Type.DATE.getDataType(),List.of("DATE","TIMESTAMP"));


        private final List<String> mysqlTypeList;
        private final String dataType;
        private final List<String> hiveTypeList;

        TypeMapper(List<String> mysqlTypeList,String dataType, List<String> hiveTypeList) {
            this.mysqlTypeList = mysqlTypeList;
            this.dataType = dataType;
            this.hiveTypeList = hiveTypeList;
        }

        public static Map<String,List<String>> getDataType(String sourceConnectType, String targetConnectType){
            Map<String,List<String>> map = Maps.newHashMap();
            if(Objects.equals(sourceConnectType, IngestionType.MYSQL.getType())&&
                    Objects.equals(targetConnectType, IngestionType.HIVE.getType())){
                List<MysqlDataTypeEnum> mysqlList = MysqlDataTypeEnum.getList();
                mysqlList.forEach(t->{
                    TypeMapper hiveType = Stream.of(values()).filter(e -> e.mysqlTypeList.contains(t.getCode())).
                            findAny().orElse(null);
                    map.put(t.getCode(),hiveType==null?List.of():hiveType.getHiveTypeList());
                });
            }else if(Objects.equals(sourceConnectType, IngestionType.HIVE.getType())&&
                    Objects.equals(targetConnectType, IngestionType.MYSQL.getType())){
                List<DataTypeEnum> hiveList = DataTypeEnum.getList();
                hiveList.forEach(t-> {
                    TypeMapper mysqlType = Stream.of(values()).filter(e -> e.hiveTypeList.contains(t.getCode())).
                            findAny().orElse(null);
                    map.put(t.getCode(), mysqlType == null ? List.of() : mysqlType.getMysqlTypeList());
                });
            }else if(Objects.equals(sourceConnectType, IngestionType.MYSQL.getType())&&
                    Objects.equals(targetConnectType, IngestionType.MYSQL.getType())){
                List<MysqlDataTypeEnum> mysqlList = MysqlDataTypeEnum.getList();
                mysqlList.forEach(t->{
                    TypeMapper mysql = Stream.of(values()).filter(e -> e.mysqlTypeList.contains(t.getCode())).
                            findAny().orElse(null);
                    map.put(t.getCode(),mysql==null?List.of():mysql.getMysqlTypeList());
                });
            }else if(Objects.equals(sourceConnectType, IngestionType.HIVE.getType())&&
                    Objects.equals(targetConnectType, IngestionType.HIVE.getType())){
                List<DataTypeEnum> hiveList = DataTypeEnum.getList();
                hiveList.forEach(t-> {
                    TypeMapper hiveType = Stream.of(values()).filter(e -> e.hiveTypeList.contains(t.getCode())).
                            findAny().orElse(null);
                    map.put(t.getCode(), hiveType == null ? List.of() : hiveType.getHiveTypeList());
                });
            }
            return map;
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




}
