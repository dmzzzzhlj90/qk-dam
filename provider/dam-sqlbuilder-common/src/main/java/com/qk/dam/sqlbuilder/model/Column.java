package com.qk.dam.sqlbuilder.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 表字段
 * @author wangzp
 * @date 2021/11/12 16:01
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Column {
    /**
     * 字段名称
     */
    private String name ;

    /**
     * 字段数据类型
     */
    private String dataType;

    /**
     * 字段长度
     */
    private int length;

    /**
     * 字段注释
     */
    private String comments;

    /**
     * 是否为主键
     */
    @Builder.Default
    private Boolean primaryKey = false ;

    /**
     * 是否为自增量
     */
    @Builder.Default
    private Boolean autoIncrement = false;

    /**
     * 默认值
     */
    private String defaultValue ;

    /**
     * 是否不为空  DEFAULT NULL
     */
    @Builder.Default
    private Boolean empty = true;
}