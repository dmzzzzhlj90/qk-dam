package com.qk.dam.groovy.model;

import com.qk.dam.groovy.model.base.FactParam;

import java.util.List;
import java.util.Map;

/**
 * 规则函数信息
 *
 * @author wjq
 * @date 2021/12/17
 * @since 1.0.0
 */
public class RuleFunctionInfo extends FactParam {
    private String expression;

    public RuleFunctionInfo(String functionName, List<String> params, String description, String type, Map<String, Object> defaultVal) {
        super(functionName, params, description, type, defaultVal);
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}


