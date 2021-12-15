package com.qk.dam.metedata.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author zhudaoming
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MtdColumnSearchVO extends MtdTableSearchVO {
    public MtdColumnSearchVO(){}
    public MtdColumnSearchVO(String typeName, String dbName, String server, String tableName) {
        super(typeName, dbName, server);
        this.tableName = tableName;
    }

    public MtdColumnSearchVO(Integer limit, Integer offset, String typeName, String dbName, String server, String tableName) {
        super(limit, offset, typeName, dbName, server);
        this.tableName = tableName;
    }

    @NotNull(message = "元数据数据库数据表不能为空！")
    private String tableName;
}
