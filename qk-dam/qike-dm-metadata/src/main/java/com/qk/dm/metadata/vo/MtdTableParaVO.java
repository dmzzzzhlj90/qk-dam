package com.qk.dm.metadata.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MtdTableParaVO {

    @NotNull(message = "元数据数据库类型不能为空！")
    private String typeName;
    @NotNull(message = "server不能为空！")
    private String server;
    @NotNull(message = "dbName不能为空！")
    private String dbName;
    @NotNull(message = "limit不能为空！")
    private Integer limit;
    @NotNull(message = "offset不能为空！")
    private Integer offset;
}
