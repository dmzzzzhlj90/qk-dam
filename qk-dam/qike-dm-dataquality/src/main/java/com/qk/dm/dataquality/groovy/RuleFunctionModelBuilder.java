package com.qk.dm.dataquality.groovy;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qk.dam.groovy.model.RuleFunctionInfo;
import com.qk.dm.dataquality.utils.FileUtil;

import java.util.Map;

/**
 * 规则函数模型构建器
 *
 * @author wjq
 * @date 2022/1/7
 * @since 1.0.0
 */
public class RuleFunctionModelBuilder {

    public static void main(String[] args) {
        Map<String, RuleFunctionInfo> functionInfoMap = buildRuleFunctionModel();
        System.out.println(functionInfoMap);
    }

    public static Map<String, RuleFunctionInfo> buildRuleFunctionModel() {
        String result = FileUtil.readFileContent("facts/RuleFunctionModel.json");

        return new Gson().fromJson(result, new TypeToken<Map<String, RuleFunctionInfo>>() {
        }.getType());
    }

}
