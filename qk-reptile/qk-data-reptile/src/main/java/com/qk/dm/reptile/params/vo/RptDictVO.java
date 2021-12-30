package com.qk.dm.reptile.params.vo;

import lombok.Data;

import java.util.List;

@Data
public class RptDictVO {

    private Long id;
    /**
     * 区划编码
     */
    private String value;

    /**
     * 区划名称
     */
    private String label;
    /**
     * 父id
     */
    private Long pid;


    private List<RptDictVO> children;
}
