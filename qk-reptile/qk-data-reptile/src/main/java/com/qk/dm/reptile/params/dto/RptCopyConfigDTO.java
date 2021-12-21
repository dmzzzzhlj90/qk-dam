package com.qk.dm.reptile.params.dto;

import lombok.Data;


import javax.validation.constraints.NotNull;

/**
 * 爬虫配置复制
 */
@Data
public class RptCopyConfigDTO {
    /**
     * 来源id(爬虫id)
     */
    @NotNull(message = "来源id不能为空")
    private Long sourceId;
    /**
     * 目标id(待配id)
     */
    @NotNull(message = "目标id不能为空")
    private Long targetId;
}
