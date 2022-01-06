package com.qk.dam.groovy.facts.handler;


import com.qk.dam.groovy.constant.FunctionConstant;
import com.qk.dam.groovy.facts.base.AbstractHandler;
import com.qk.dam.groovy.model.RuleFunctionInfo;

import java.util.HashMap;
import java.util.List;
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
    private List<Map<String,Object>> param;

    public RuleFunctionHandler(Map<String, RuleFunctionInfo> functionInfoMap, List<Map<String,Object>> param, BiFunction express) {
        super(FunctionConstant.RULE_FUNCTION_TYPE, express);
        this.ruleFunctionMap = functionInfoMap;
        this.param = param;
    }

    @Override
    public void doHandler() {
        param.forEach(sm->
                ruleFunctionMap.forEach((k,v)-> sm.put(k,getExpressCall().apply(sm,v)))
        );
        super.result = param;
    }
}