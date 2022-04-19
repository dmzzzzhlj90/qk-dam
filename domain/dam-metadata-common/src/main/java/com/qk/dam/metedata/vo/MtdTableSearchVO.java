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

    public MtdTableSearchVO(Integer limit, Integer offset, String typeName, String dbName, String server) {
        super(limit, offset, typeName, server);
        this.dbName=dbName;
    }
    @NotNull(message = "dbName不能为空！")
    private String dbName;

}
