package com.qk.dm.dataingestion.model.mysql;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReaderPara extends BasePara{
    public ReaderPara(){}

    public ReaderPara(String username, String password, List<String> column, List<Connection> connection, String splitPk) {
        super(username, password, column);
        this.splitPk = splitPk;
        this.connection = connection;
    }

    private String splitPk;

    private List<Connection> connection;

    @Data
    @Builder
    public static class Connection{
        private List<String> table;
        private List<String> jdbcUrl;
        private List<String> querySql;
    }
}
