package com.qk.dam.sqlbuilder.model;

import com.qk.dam.sqlbuilder.enums.DataType;
import lombok.Builder;
import lombok.Data;

/**
 * @author Haidnor
 */
@Data
@Builder
public class Column {
    /**
     * 字段名称
     */
    private String name ;

    /**
     * 字段数据类型
     */
    private DataType dataType;

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
    private Boolean empty = false;
}