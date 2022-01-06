package com.qk.dam.groovy.facts;

import com.qk.dam.groovy.facts.handler.RuleFunctionHandler;
import com.qk.dam.groovy.function.RuleFun;
import com.qk.dam.groovy.model.FactModel;
import com.qk.dam.groovy.model.RuleFunctionInfo;

import java.time.LocalDateTime;
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
        //函数信息
//        Map<String, RuleFunctionInfo> ruleFunctionMap =
//                ruleFunctionModel.getRuleFunctionInfos().stream()
//                        .filter(ruleFunctionInfo -> FunctionConstant.isExistFunctionName(ruleFunctionInfo.getFunctionName()))
//                        .collect(
//                                Collectors.toMap(RuleFunctionInfo::getFunctionName, v -> v, (o, n) -> n, HashMap::new));
        final List<Map<String, Object>> sourceDataList = new ArrayList<>();
        Map<String, Object> sourceDataMap = new HashMap<>();
        sourceDataMap.put("tradeDay", LocalDateTime.now());
        sourceDataList.add(sourceDataMap);

        ruleFunctionContext
                .option()
                .initHandler(handlers -> {
                    RuleFun ruleFun = new RuleFun();
                    handlers.add(new RuleFunctionHandler(
                            factModel.getRuleFunctionInfo().stream().collect(Collectors.toMap(RuleFunctionInfo::getField, v -> v, (o, n) -> n, HashMap::new)),
                            sourceDataList,
                            ruleFun.getRuleFunction()));
                });
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
