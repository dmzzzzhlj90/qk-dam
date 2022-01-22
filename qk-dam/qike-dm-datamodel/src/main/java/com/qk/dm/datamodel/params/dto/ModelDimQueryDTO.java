package com.qk.dm.datamodel.params.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class ModelDimQueryDTO extends ModelPageDTO{

    /**
     * 维度名称
     */
    private String dimName;

    /**
     * 0待审核 1已发布2 已下线
     */
    private Integer status;
}
