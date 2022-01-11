package com.qk.dam.groovy.model;

import lombok.Data;

import java.util.List;

/**
 * 规则函数模型
 *
 * @author wjq
 * @date 2021/12/23
 * @since 1.0.0
 */
@Data
public class FactModel {
    //    private List<FactField> factField;

    public List<RuleFunctionInfo> ruleFunctionInfo;

    public List<Object> data;

}
