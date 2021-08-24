package com.qk.dam.commons.enums;

/**
 * 数据源连接类型
 *
 * @author weijunqi
 */
public enum ConnTypeEnum {

    /*数据连接枚举信息*/
    MYSQL("db-mysql"), ORACLE("db-oracle"), DB2("db-db2"), SQLSERVER("db-sqlserver"), HIVE("db-hive"),POSTGETSQL("db-postgetsql"),
    HBASE("hbase"), REDIS("redis"), EXCEL("excel"), CSV("csv"), REST("rest");

    /**定义一个public修饰的实例变量*/
    private String name;

    ConnTypeEnum(String name) {
        this.name = name;
    }

    public static ConnTypeEnum locateEnum(String name) {

        for(ConnTypeEnum dataDealEnum : values()) {
            if(dataDealEnum.getName().equalsIgnoreCase(name)) {
                return dataDealEnum;
            }
        }
        throw new IllegalArgumentException("未知的枚举类型：" + name);
    }

    public String getName() {
        return name;
    }
}