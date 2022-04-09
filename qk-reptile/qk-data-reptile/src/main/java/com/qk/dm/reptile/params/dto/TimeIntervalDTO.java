package com.qk.dm.reptile.params.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TimeIntervalDTO {

    @NotNull(message = "id不能为空")
    private Long id;
    /**
     * 定时间隔
     */
    @NotNull(message = "定时间隔不能为空")
    private String timeInterval;
}
