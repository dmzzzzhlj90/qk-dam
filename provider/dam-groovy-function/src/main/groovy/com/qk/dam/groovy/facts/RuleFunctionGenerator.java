package com.qk.dam.groovy.facts;

import com.qk.dam.groovy.facts.handler.RuleFunctionHandler;
import com.qk.dam.groovy.function.RuleFun;
import com.qk.dam.groovy.model.FactModel;
import com.qk.dam.groovy.model.RuleFunctionInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 规则函数对象服务
 *
 * @author wjq
 * @date 2021/12/23
 * @since 1.0.0
 */
public class RuleFunctionGenerator {
    private final RuleFunctionContext ruleFunctionContext = new RuleFunctionContext();

    public RuleFunctionGenerator(FactModel factModel) {
        ruleFunctionContext
                .option()
                .initHandler(handlers -> {
                    RuleFun ruleFun = new RuleFun();
                    handlers.add(new RuleFunctionHandler(
                            factModel.getRuleFunctionInfo().stream().collect(Collectors.toMap(RuleFunctionInfo::getFunctionName, v -> v, (o, n) -> n, HashMap::new)),
                            ruleFun.getRuleFunction()));
                });
    }

    private List<Map<String, Object>> getSourceDataList(FactModel factModel) {
        List<Map<String, Object>> sourceDataList = new ArrayList<>();

        List<RuleFunctionInfo> ruleFunctionInfos = factModel.getRuleFunctionInfo();
        for (RuleFunctionInfo ruleFunctionInfo : ruleFunctionInfos) {
            Map<String, Object> sourceDataMap = new HashMap<>(16);
            sourceDataMap.put(ruleFunctionInfo.getDescription(),ruleFunctionInfo.getDefaultVal());
            sourceDataList.add(sourceDataMap);
        }
        return sourceDataList;
    }

    /**
     * 执行规则函数
     *
     * @return Map<String, Object>
     */
    public Map<String, Object> generateFunction() {
        ruleFunctionContext.execute();
        return ruleFunctionContext.result();
    }


}
