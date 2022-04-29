package com.qk.dm.dataingestion.vo;

import lombok.Data;
import javax.validation.constraints.NotNull;

/**
 * 修改datax json参数
 */
@Data
public class DisJsonParamsVO {
    /**
     * 作业id
     */
    @NotNull(message = "作业id不能为空")
    private Long id;
    /**
     * datax json数据
     */
    @NotNull(message = "dataxJson不能为空")
    private String dataxJson;
}
