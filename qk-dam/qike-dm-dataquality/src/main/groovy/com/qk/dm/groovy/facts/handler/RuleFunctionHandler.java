package com.qk.dm.groovy.facts.handler;

import com.qk.dm.groovy.facts.base.AbstractHandler;
import com.qk.dm.groovy.model.base.RuleFunctionInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * @author daomingzhu
 */
public class RuleFunctionHandler extends AbstractHandler {
    private Map<String, RuleFunctionInfo> ruleFunctionMap;

    public RuleFunctionHandler(Map<String, RuleFunctionInfo> ruleFunctionMap, BiFunction express) {
        super("ruleFunction", express);
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