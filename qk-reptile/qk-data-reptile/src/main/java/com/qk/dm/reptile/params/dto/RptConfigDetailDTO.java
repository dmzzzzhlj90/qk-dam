package com.qk.dm.reptile.params.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 查询配置信息参数
 * @author wangzp
 * @date 2022/01/11 16:27
 * @since 1.0.0
 */
@Data
public class RptConfigDetailDTO {
    /**
     * 待配id、爬虫id或配置id
     */
    @NotNull(message = "id不能为空")
    private Long id;
    /**
     * 是否第一层，默认false
     */
    private Boolean firstFloor;
}
