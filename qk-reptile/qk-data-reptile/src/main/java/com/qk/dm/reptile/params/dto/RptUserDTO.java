package com.qk.dm.reptile.params.dto;

import com.qk.dam.jpa.pojo.Pagination;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RptUserDTO {
    @NotNull(message = "分页信息不能为空")
    private Pagination pagination;

    private String userName;
}
