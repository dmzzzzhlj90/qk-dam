package com.qk.dm.dataingestion.model.mysql;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class BasePara {

    public BasePara(){}
    public BasePara(String username, String password, List<String> column, List<Connection> connection) {
        this.username = username;
        this.password = password;
        this.column = column;
        this.connection = connection;
    }

    private String username;
    private String password;
    private List<String> column;
    private List<Connection> connection;

    @Data
    @Builder
    public static class Connection{
        private List<String> table;
        private List<String> jdbcUrl;
    }
}
