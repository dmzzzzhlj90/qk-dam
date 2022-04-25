package com.qk.dm.dataingestion.vo;

import lombok.Data;
import javax.validation.constraints.NotNull;

/**
 * 修改datax json参数
 */
@Data
public class DisJsonParamsVO {

    @NotNull(message = "作业id不能为空")
    private Long id;
    @NotNull(message = "dataxJson不能为空")
    private String dataxJson;
}
