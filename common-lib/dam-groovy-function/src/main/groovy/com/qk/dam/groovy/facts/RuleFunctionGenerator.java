package com.qk.dam.groovy.facts;

import com.qk.dam.groovy.constant.FunctionConstant;
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
    private final RuleFunctionContext ruleFunctionContext = new RuleFunctionContext();

    public RuleFunctionGenerator(RuleFunctionModel ruleFunctionModel) {
        //函数信息
        Map<String, RuleFunctionInfo> ruleFunctionMap =
                ruleFunctionModel.getRuleFunctionInfos().stream()
                        .filter(ruleFunctionInfo -> FunctionConstant.isExistFunctionName(ruleFunctionInfo.getFunctionName()))
                        .collect(
                                Collectors.toMap(RuleFunctionInfo::getFunctionName, v -> v, (o, n) -> n, HashMap::new));

        ruleFunctionContext
                .option()
                .initHandler(handlers -> {
                    RuleFun ruleFun = new RuleFun();
                    handlers.add(new RuleFunctionHandler(
                            ruleFunctionMap,
                            ruleFun.getComputeFunc()));
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
