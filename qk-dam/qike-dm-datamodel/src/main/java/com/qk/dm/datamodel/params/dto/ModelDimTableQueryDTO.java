package com.qk.dm.datamodel.params.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class ModelDimTableQueryDTO extends ModelPageDTO {

    private String dimName;
    /**
     * 维度名称
     */
    private String themeName;
}
