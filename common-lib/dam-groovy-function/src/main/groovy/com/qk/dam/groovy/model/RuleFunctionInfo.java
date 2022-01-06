package com.qk.dam.groovy.model;

import com.qk.dam.groovy.model.base.FactField;
import lombok.*;

import java.util.Map;

/**
 * 规则函数信息
 *
 * @author wjq
 * @date 2021/12/17
 * @since 1.0.0
 */
public class RuleFunctionInfo extends FactField {
    private String expression;
    public RuleFunctionInfo(String field, String fieldName, String type, Object defaultVal) {
        super(field, fieldName, type, defaultVal);
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}


