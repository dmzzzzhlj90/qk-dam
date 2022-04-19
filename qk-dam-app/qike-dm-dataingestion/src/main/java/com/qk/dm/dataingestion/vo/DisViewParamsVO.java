package com.qk.dm.dataingestion.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DisViewParamsVO {

    /**
     * 类型 source 源 target 目标
     */
    @NotNull(message = "球球类型不能为空")
    private String type;

    /**
     *连接类型类型 如 mysql  hive
     */
    @NotNull(message = "连接类型不能为空")
    private String connectType;
}
