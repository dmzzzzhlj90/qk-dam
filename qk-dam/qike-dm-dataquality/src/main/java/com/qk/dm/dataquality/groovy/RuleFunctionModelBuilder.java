package com.qk.dm.dataquality.groovy;

import com.qk.dam.groovy.model.RuleFunctionInfo;
import com.qk.dm.dataquality.groovy.pojo.RuleFunctionModelInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 规则函数模型构建器
 *
 * @author wjq
 * @date 2022/1/7
 * @since 1.0.0
 */
@Component
public class RuleFunctionModelBuilder {

    private RuleFunctionModelInfo ruleFunctionModelInfo;

    @Autowired
    public RuleFunctionModelBuilder(RuleFunctionModelInfo ruleFunctionModelInfo) {
        this.ruleFunctionModelInfo = ruleFunctionModelInfo;
    }

    public Map<String, RuleFunctionInfo> isExistFunction(String scanSql) {
        Map<String, RuleFunctionInfo> existFunctionMap = new HashMap<>(16);
        Map<String, RuleFunctionInfo> ruleFunctionInfoMap = ruleFunctionModelInfo.getRuleFunctionInfoMap();

        Set<String> ruleFunctionKeySet = ruleFunctionInfoMap.keySet();
        for (String functionName : ruleFunctionKeySet) {
            if (scanSql.contains(functionName)) {
                existFunctionMap.put(functionName, ruleFunctionInfoMap.get(functionName));
            }
        }
        return existFunctionMap;
    }

}
