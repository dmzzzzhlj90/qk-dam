package com.qk.dm.dataingestion.model.mysql;

import lombok.Data;

import java.util.List;

@Data
public class BasePara {

    public BasePara(){}
    public BasePara(String username, String password, List<String> column) {
        this.username = username;
        this.password = password;
        this.column = column;
    }

    private String username;
    private String password;
    private List<String> column;

}
