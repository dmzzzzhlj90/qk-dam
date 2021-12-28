package com.qk.dam.hive;

/**
 * @author zhudaoming
 */

public enum DbTypeEnum {
    /**
     * hive
     */
    HIVE("hive2", 10000, "org.apache.hive.jdbc.HiveDriver"),
    MYSQL("mysql",3306 , "com.mysql.cj.jdbc.Driver"),
    ;
    private String schema;
    private int port;
    private String driverName;

    DbTypeEnum(String schema, int port, String driverName) {
        this.schema = schema;
        this.port = port;
        this.driverName = driverName;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
