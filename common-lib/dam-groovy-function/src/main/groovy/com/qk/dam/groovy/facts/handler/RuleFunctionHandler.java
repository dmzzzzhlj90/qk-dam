package com.qk.dam.groovy.facts.handler;


import com.google.common.collect.Maps;
import com.qk.dam.groovy.constant.FunctionConstant;
import com.qk.dam.groovy.facts.base.AbstractHandler;
import com.qk.dam.groovy.model.RuleFunctionInfo;

import java.util.ArrayList;
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


    public RuleFunctionHandler(Map<String, RuleFunctionInfo> functionInfoMap, BiFunction express) {
        super(FunctionConstant.RULE_FUNCTION_TYPE, express);
        this.ruleFunctionMap = functionInfoMap;
    }

    @Override
    public void doHandler() {
        Map<String, Object> result = Maps.newHashMap();
        for (String key : ruleFunctionMap.keySet()) {
            RuleFunctionInfo ruleFunctionInfo = ruleFunctionMap.get(key);
            //设置函数多个参数
            Map<String, Object> defaultVal = ruleFunctionInfo.getDefaultVal();
            try {
                result.put(key, getExpressCall().apply(defaultVal,ruleFunctionInfo));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.result = result;
    }
}