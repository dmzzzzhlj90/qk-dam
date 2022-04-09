package com.qk.dm.dataingestion.model.mysql;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class WriterPara extends BasePara{
    public WriterPara(){}

    public WriterPara(String username, String password, List<String> column, List<Connection> connection, String writeMode) {
        super(username, password, column, connection);
        this.writeMode = writeMode;
    }

    public WriterPara(String username, String password, List<String> column, List<Connection> connection, String writeMode, List<String> preSql) {
        super(username, password, column, connection);
        this.writeMode = writeMode;
        this.preSql = preSql;
    }

    /**
     * 控制写入数据到目标表采用 insert into 或者 replace into 或者 ON DUPLICATE KEY UPDATE 语句
     */
    private String writeMode;
    /**
     * 写入数据到目的表前，会先执行这里的SQL语句
     */
    private List<String> preSql;
}
