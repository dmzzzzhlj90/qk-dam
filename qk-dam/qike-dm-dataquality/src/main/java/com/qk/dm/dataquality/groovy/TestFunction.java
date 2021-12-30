package com.qk.dm.dataquality.groovy;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.qk.dam.groovy.facts.RuleFunctionGenerator;
import com.qk.dam.groovy.model.RuleFunctionModel;
import com.qk.dam.groovy.model.base.RuleFunctionInfo;

import java.util.List;
import java.util.Map;

/**
 * @author wjq
 * @date 2021/12/17
 * @since 1.0.0
 */
public class TestFunction {

    public static void main(String[] args) {
        RuleFunctionModel model = new RuleFunctionModel();

        RuleFunctionInfo functionInfo = new RuleFunctionInfo("format3('20211215','yyyyMMdd')", Maps.newHashMap());
        RuleFunctionInfo functionInfo2 = new RuleFunctionInfo("format('20211216','yyyyMMdd')", Maps.newHashMap());
        List<RuleFunctionInfo> ruleFunctionInfos = Lists.newArrayList();
        ruleFunctionInfos.add(functionInfo);
        ruleFunctionInfos.add(functionInfo2);

        model.setRuleFunctionInfos(ruleFunctionInfos);

        for (int i = 0; i < 1; i++) {
            RuleFunctionGenerator ruleFunctionGenerator = new RuleFunctionGenerator(model);
            Map<String, Object> resultData = ruleFunctionGenerator.generateFunction();

            for (String key : resultData.keySet()) {
                System.out.println(resultData.get(key));
            }
        }

    }
}
