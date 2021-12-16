package com.qk.dm.reptile.params.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RptRunStatusDTO {
    @NotNull(message = "id不能为空")
    private  Long id;
    @NotNull(message = "运行状态不能为空")
    private Integer runStatus;
}
