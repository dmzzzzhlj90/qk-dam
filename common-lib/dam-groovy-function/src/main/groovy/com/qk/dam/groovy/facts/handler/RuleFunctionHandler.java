package com.qk.dam.groovy.facts.handler;


import com.qk.dam.groovy.constant.FunctionConstant;
import com.qk.dam.groovy.facts.base.AbstractHandler;
import com.qk.dam.groovy.model.base.RuleFunctionInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * 规则函数处理器
 *
 * @author wjq
 * @date 2021/12/23
 * @since 1.0.0
 */
public class RuleFunctionHandler extends AbstractHandler {
    private final Map<String, RuleFunctionInfo> ruleFunctionMap;

    public RuleFunctionHandler(Map<String, RuleFunctionInfo> ruleFunctionMap, BiFunction express) {
        super(FunctionConstant.RULE_FUNCTION_TYPE, express);
        this.ruleFunctionMap = ruleFunctionMap;
    }

    @Override
    public void doHandler() {
        Map<String, Object> resultMap = new HashMap<>(16);
        ruleFunctionMap.forEach(
                (k, v) -> resultMap.put(k,
                        getExpressCall().apply(k, v)));

        super.result = resultMap;
    }
}