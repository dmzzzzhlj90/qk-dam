package com.qk.dam.groovy;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.qk.dam.groovy.facts.RuleFunctionGenerator;
import com.qk.dam.groovy.model.FactModel;
import com.qk.dam.groovy.model.RuleFunctionInfo;
import com.qk.dam.groovy.model.base.FactField;

import java.time.LocalDateTime;
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
//        //factField
//        List<FactField> factFields = Lists.newArrayList();
//        FactField factField = new FactField();
//        factField.setField("birthday");
//        factField.setFieldName("birthday");
//        factField.setType("String");
//        factFields.add(factField);
//        factModel.setFactField(factFields);
//
//        //ruleFunctionInfo
//        List<RuleFunctionInfo> ruleFunctionInfos = Lists.newArrayList();
//        RuleFunctionInfo ruleFunctionInfo = new RuleFunctionInfo("birthday", "birthday", "String", null);
//        ruleFunctionInfo.setExpression("format('birthday','yyyyMMdd')");
//        ruleFunctionInfos.add(ruleFunctionInfo);
//        factModel.setRuleFunctionInfo(ruleFunctionInfos);
//
//        //data
//        List<Object> uu = Lists.newArrayList();
//        for (int i = 0; i < 1; i++) {
//            Map<String, Object> dataMap = Maps.newHashMap();
//            dataMap.put("birthday", LocalDateTime.now());
//            uu.add(dataMap);
//        }
//        factModel.setData(uu);

        //直接表达式
        FactModel factModel = new FactModel();
        //factField
        List<FactField> factFields = Lists.newArrayList();
        FactField factField = new FactField();
        factField.setField("tradeDay");
        factField.setFieldName("tradeDay");
        factField.setType("String");
        factFields.add(factField);
        factModel.setFactField(factFields);

        //ruleFunctionInfo
        List<RuleFunctionInfo> ruleFunctionInfos = Lists.newArrayList();
        RuleFunctionInfo ruleFunctionInfo = new RuleFunctionInfo("tradeDay", "tradeDay", "String", null);
        ruleFunctionInfo.setExpression("tradeDay('20220106')");
        ruleFunctionInfos.add(ruleFunctionInfo);
        factModel.setRuleFunctionInfo(ruleFunctionInfos);

        //data
        List<Object> uu = Lists.newArrayList();
        for (int i = 0; i < 1; i++) {
            Map<String, Object> dataMap = Maps.newHashMap();
            dataMap.put("tradeDay", LocalDateTime.now());
            uu.add(dataMap);
        }
        factModel.setData(uu);

        for (int i = 0; i < 1; i++) {
            RuleFunctionGenerator generater = new RuleFunctionGenerator(factModel);
            long start = System.currentTimeMillis();
            Map<String, Object> cc = generater.generateFunction();
            long end = System.currentTimeMillis();
            System.out.println("执行时间：" + (end - start) / 1000 + '秒');
            System.out.println(cc);
        }
    }
}
