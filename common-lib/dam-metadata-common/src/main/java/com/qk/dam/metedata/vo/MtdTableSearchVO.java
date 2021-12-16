package com.qk.dam.metedata.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author zhudaoming
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MtdTableSearchVO extends MtdDbSearchVO{
    public MtdTableSearchVO(){}
    public MtdTableSearchVO(String typeName, String dbName, String server) {
        super(typeName, dbName);
        this.server = server;
    }

    public MtdTableSearchVO(Integer limit, Integer offset, String typeName, String dbName, String server) {
        super(limit, offset, typeName, dbName);
        this.server = server;
    }
    @NotNull(message = "dbName不能为空！")
    private String dbName;
    @NotNull(message = "元数据数据库地址不能为空！")
    protected String server;

}
