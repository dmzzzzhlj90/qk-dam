package com.qk.dam.groovy;

import com.google.common.collect.Lists;
import com.qk.dam.groovy.facts.RuleFunctionGenerator;
import com.qk.dam.groovy.model.FactModel;
import com.qk.dam.groovy.model.RuleFunctionInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wjq
 * @date 2022/1/6
 * @since 1.0.0
 */
public class FactsJava {
    public static void main(String[] args) {

//        FactModel factModel = new FactModel();
        //factField
//        List<FactField> factFields = Lists.newArrayList();
//        FactField factField = new FactField();
//        factField.setField("birthday");
//        factField.setFieldName("birthday");
//        factField.setType("String");
//        factFields.add(factField);
//        factModel.setFactField(factFields);

        //ruleFunctionInfo
//        List<RuleFunctionInfo> ruleFunctionInfos = Lists.newArrayList();
//        RuleFunctionInfo ruleFunctionInfo = new RuleFunctionInfo("date", "date", "String", LocalDateTime.now());
//        ruleFunctionInfo.setExpression("format('date','yyyyMMdd')");
//        ruleFunctionInfos.add(ruleFunctionInfo);
//        factModel.setRuleFunctionInfo(ruleFunctionInfos);
//
//        //data
//        List<Object> uu = Lists.newArrayList();
//        for (int i = 0; i < 1; i++) {
//            Map<String, Object> dataMap = Maps.newHashMap();
//            dataMap.put("date", LocalDateTime.now());
//            uu.add(dataMap);
//        }
//        factModel.setData(uu);

        //直接表达式
        FactModel factModel = new FactModel();
        //factField
//        List<FactField> factFields = Lists.newArrayList();
//        FactField factField = new FactField();
//        factField.setField("tradeDay");
//        factField.setFieldName("tradeDay");
//        factField.setType("String");
//        factFields.add(factField);
//        factModel.setFactField(factFields);

        //ruleFunctionInfo
        //设置参数方式
        List<RuleFunctionInfo> ruleFunctionInfos = Lists.newArrayList();
//        List<String> params = new ArrayList<>();
//        params.add("date");
//        Map<String, Object> defaultVal = new HashMap<>(16);
//        defaultVal.put("date", LocalDateTime.now());
//
//        RuleFunctionInfo ruleFunctionInfo1 = new RuleFunctionInfo("format", params, "日期格式化", "String", defaultVal);
//        ruleFunctionInfo1.setExpression("format('date','yyyy/MM/dd')");
//        ruleFunctionInfos.add(ruleFunctionInfo1);

        //直接执行sql片段方式
        List<String> params2 = new ArrayList<>();
        Map<String, Object> defaultVal2 = new HashMap<>(16);

        RuleFunctionInfo ruleFunctionInfo2 = new RuleFunctionInfo("tradeDay", params2, "日期格式化2", "String", null);
        ruleFunctionInfo2.setExpression("tradeDay('20220111',1,'yyyyMMdd')");
        ruleFunctionInfos.add(ruleFunctionInfo2);

        factModel.setRuleFunctionInfo(ruleFunctionInfos);

        //data
//        List<Object> uu = Lists.newArrayList();
//        for (int i = 0; i < 1; i++) {
//            Map<String, Object> dataMap = Maps.newHashMap();
//            dataMap.put("tradeDay", LocalDateTime.now());
//            uu.add(dataMap);
//        }
//        factModel.setData(uu);

        RuleFunctionGenerator generater = new RuleFunctionGenerator(factModel);
        long start = System.currentTimeMillis();
        Map<String, Object> cc = generater.generateFunction();
        long end = System.currentTimeMillis();
        System.out.println("执行时间：" + (end - start) / 1000 + '秒');
        System.out.println(cc);
    }

}
