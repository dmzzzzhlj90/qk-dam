package com.qk.dm.indicator.vo;

import lombok.Data;

import java.util.Date;

@Data
public class IdcFunctionVO {
    private Integer id;

    /**
     * 函数名称
     */
    private String name;

    /**
     * 分类
     */
    private String classify;

    /**
     * 函数
     */
    private String function;

    /**
     * 引擎
     */
    private String engine;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 父级id
     */
    private Integer parentId;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 是否删除；0逻辑删除，1物理删除
     */
    private Integer delFlag;

}
