package com.qk.dm.datamodel.params.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper = true)
public class ModelDimTableQueryDTO extends ModelPageDTO {

    private String dimName;
    /**
     * 主题id集合
     */
    private List<String> themeIdList;
}
