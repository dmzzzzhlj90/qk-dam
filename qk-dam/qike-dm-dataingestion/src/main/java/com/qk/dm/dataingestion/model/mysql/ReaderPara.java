package com.qk.dm.dataingestion.model.mysql;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReaderPara extends BasePara{
    public ReaderPara(){}

    public ReaderPara(String username, String password, List<String> column, List<Connection> connection, String splitPk) {
        super(username, password, column, connection);
        this.splitPk = splitPk;
    }

    private String splitPk;
}
