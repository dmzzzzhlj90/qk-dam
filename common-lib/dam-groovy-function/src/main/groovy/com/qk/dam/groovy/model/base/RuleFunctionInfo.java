package com.qk.dam.groovy.model.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Map;

/**
 * 规则函数信息
 *
 * @author wjq
 * @date 2021/12/17
 * @since 1.0.0
 */
@Data
@ToString
@AllArgsConstructor
public class RuleFunctionInfo {

    /**
     * 函数名称
     */
    private String functionName;

    /**
     * 参数信息
     */
    private Map<String, Object> params;
}


