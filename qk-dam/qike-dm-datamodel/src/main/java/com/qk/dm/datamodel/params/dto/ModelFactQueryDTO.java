package com.qk.dm.datamodel.params.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ModelFactQueryDTO extends ModelPageDTO{
    /**
     * 主题id集合
     */
    private List<String> themeIdList;
    /**
     * 事实表名称
     */
    private String factName;
}
