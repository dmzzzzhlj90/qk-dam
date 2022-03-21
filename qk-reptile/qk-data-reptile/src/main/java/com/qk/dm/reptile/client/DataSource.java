package com.qk.dm.reptile.client;

import lombok.Data;

@Data
public class DataSource {
    private String driver;
    private String url;
    private String user;
    private String password;
}
