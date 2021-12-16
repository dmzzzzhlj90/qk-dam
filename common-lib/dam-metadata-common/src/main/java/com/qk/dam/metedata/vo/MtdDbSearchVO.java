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

    public MtdDbSearchVO(Integer limit, Integer offset, String typeName,String server) {
        super(limit, offset);
        this.typeName = typeName;
        this.server = server;
    }

    @NotNull(message = "元数据数据库类型不能为空！")
    private String typeName;
    @NotNull(message = "server不能为空！")
    private String server;
}
