package com.qk.dam.groovy.facts;

import com.qk.dam.groovy.facts.handler.RuleFunctionHandler;
import com.qk.dam.groovy.function.RuleFun;
import com.qk.dam.groovy.model.RuleFunctionModel;
import com.qk.dam.groovy.model.base.RuleFunctionInfo;

import java.util.HashMap;
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
    private final RuleFunctionContext factsContext = new RuleFunctionContext();

    public RuleFunctionGenerator(RuleFunctionModel ruleFunctionModel) {
        //函数信息
        Map<String, RuleFunctionInfo> ruleFunctionMap =
                ruleFunctionModel.getRuleFunctionInfos().stream()
                        .collect(
                                Collectors.toMap(RuleFunctionInfo::getFunctionName, v -> v, (o, n) -> n, HashMap::new));

        factsContext
                .option()
                .initHandler(handlers -> {
                    RuleFun factsFun = new RuleFun();
                    handlers.add(new RuleFunctionHandler(
                            ruleFunctionMap,
                            factsFun.getComputeFunc()));
                });
    }

    /**
     * 执行规则函数
     *
     * @return Map<String, Object>
     */
    public Map<String, Object> generateFunction() {
        factsContext.execute();
        return factsContext.result();
    }


}
