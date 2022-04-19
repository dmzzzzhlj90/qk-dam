package com.qk.dam.metedata.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zhudaoming
 */
@Data
public class AtlasPagination {
    public static final Integer DEF_LIMIT=1000;
    public static final Integer DEF_OFFSET=0;
    public AtlasPagination(Integer limit, Integer offset) {
        this.limit = limit;
        this.offset = offset;
    }

    public AtlasPagination() {
    }

    @NotNull(message = "limit不能为空！")
    private Integer limit;
    @NotNull(message = "offset不能为空！")
    private Integer offset;
}
