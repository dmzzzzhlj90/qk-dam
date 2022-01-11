package com.qk.dam.groovy;

import com.google.common.collect.Lists;
import com.qk.dam.groovy.facts.RuleFunctionGenerator;
import com.qk.dam.groovy.model.FactModel;
import com.qk.dam.groovy.model.RuleFunctionInfo;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        List<RuleFunctionInfo> ruleFunctionInfos = Lists.newArrayList();
        List<String> params = new ArrayList<>();
        params.add("date");
        RuleFunctionInfo ruleFunctionInfo1 = new RuleFunctionInfo(params, "date", "String", LocalDateTime.now());
        ruleFunctionInfo1.setExpression("format('date','yyyy/MM/dd')");
        ruleFunctionInfos.add(ruleFunctionInfo1);

//        RuleFunctionInfo ruleFunctionInfo2 = new RuleFunctionInfo("tradeDay", "tradeDay", "String", LocalDateTime.now());
//        ruleFunctionInfo2.setExpression("tradeDay('20220106')");
//        ruleFunctionInfos.add(ruleFunctionInfo2);

        factModel.setRuleFunctionInfo(ruleFunctionInfos);

        //data
//        List<Object> uu = Lists.newArrayList();
//        for (int i = 0; i < 1; i++) {
//            Map<String, Object> dataMap = Maps.newHashMap();
//            dataMap.put("tradeDay", LocalDateTime.now());
//            uu.add(dataMap);
//        }
//        factModel.setData(uu);

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
