package com.qk.dm.dataingestion.model;

import lombok.Data;

@Data
public class DataSourceServer {
    private String type;
    private String driverInfo;
    private String server;
    private String port;
    private String userName;
    private String password;
}
