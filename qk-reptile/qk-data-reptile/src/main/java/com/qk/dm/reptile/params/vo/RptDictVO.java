package com.qk.dm.reptile.params.vo;

import lombok.Data;

@Data
public class RptDictVO {
    /**
     * 区划信息id
     */
    private Long id;

    /**
     * 父级挂接id
     */
    private Long pid;

    /**
     * 区划编码
     */
    private String code;

    /**
     * 区划名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 级次id 0:省/自治区/直辖市 1:市级 2:区
     */
    private Boolean level;
}
