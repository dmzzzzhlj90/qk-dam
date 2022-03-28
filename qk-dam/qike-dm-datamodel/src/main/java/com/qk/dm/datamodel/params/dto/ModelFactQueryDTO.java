package com.qk.dm.datamodel.params.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ModelFactQueryDTO extends ModelPageDTO{
    /**
     * 主题名称
     */
    private String themeName;
    /**
     * 事实表名称
     */
    private String factName;
}
