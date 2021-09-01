package com.qk.dm.indicator.params.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author shenpj
 * @date 2021/9/1 2:48 下午
 * @since 1.0.0
 */
@Data
@Builder
public class IdcFunctionVO {
    /**
     * 函数名称
     */
    private String name;

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
}
