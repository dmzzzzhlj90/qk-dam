package com.qk.dam.metedata.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author zhudaoming
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MtdDbSearchVO extends AtlasPagination{
    public MtdDbSearchVO(){}
    public MtdDbSearchVO(String typeName, String dbName) {
        this.typeName = typeName;
        this.dbName = dbName;
    }

    public MtdDbSearchVO(Integer limit, Integer offset, String typeName, String dbName) {
        super(limit, offset);
        this.typeName = typeName;
        this.dbName = dbName;
    }

    @NotNull(message = "元数据数据库类型不能为空！")
    private String typeName;
    @NotNull(message = "dbName不能为空！")
    private String dbName;
}
