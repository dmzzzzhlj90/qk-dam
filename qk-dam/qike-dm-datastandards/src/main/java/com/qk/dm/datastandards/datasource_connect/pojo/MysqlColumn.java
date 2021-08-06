package com.qk.dm.datastandards.datasource_connect.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class MysqlColumn {
    private String tableName;
    private String colName;
    private String displayName;
    private String data_type;
}
