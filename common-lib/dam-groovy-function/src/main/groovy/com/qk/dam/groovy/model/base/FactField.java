package com.qk.dam.groovy.model.base;

import lombok.*;

/**
 * 事实条件函数
 *
 * @author wjq
 * @date 2021/12/17
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FactField {
    private String field;
    private String fieldName;
    private String type;
    private Object defaultVal;
}


