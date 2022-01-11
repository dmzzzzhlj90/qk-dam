package com.qk.dam.groovy

import com.google.common.collect.Lists
import com.google.common.collect.Maps
import com.qk.dam.groovy.facts.RuleFunctionGenerator
import com.qk.dam.groovy.model.FactModel
import com.qk.dam.groovy.model.base.FactParam

import java.time.LocalDateTime

/**
 * 公共Groovy函数测试
 *
 * @author wjq
 * @date 2021/12/23
 * @since 1.0.0
 */
class FactsJg {
    static void main(String[] args) {
//
//        RuleFunctionModel model = new RuleFunctionModel()
//
//
//        RuleFunctionInfo functionInfo = new RuleFunctionInfo("format('20211215','yyyyMMdd')", Maps.newHashMap())
//        RuleFunctionInfo functionInfo2 = new RuleFunctionInfo("format3('20211216','yyyyMMdd')", Maps.newHashMap())
//        List<RuleFunctionInfo> ruleFunctionInfos = Lists.newArrayList()
//        ruleFunctionInfos.add(functionInfo)
//        ruleFunctionInfos.add(functionInfo2)
//
//        model.ruleFunctionInfos = ruleFunctionInfos
//        for (int i = 0; i < 1; i++) {
//            def generater = new RuleFunctionGenerator(model)
//            def start = System.currentTimeMillis()
//            def cc = generater.generateFunction()
//            def end = System.currentTimeMillis()
//            println('执行时间：' + (end - start) / 1000 + '秒')
//            println cc
//        }


        FactModel factModel = new FactModel()

        //factField
        List<FactParam> factFields = Lists.newArrayList()
        def factField = new FactParam()
        factField.field

        //ruleFunctionInfo

        //data
        List<Map<String, Object>> uu = Lists.newArrayList()
        for (int i = 0; i < 1; i++) {
            Map<String, Object> dataMap = Maps.newHashMap()
            dataMap.put("birthday", LocalDateTime.now())
            uu.add(dataMap)
        }
        factModel.data = uu
        for (int i = 0; i < 1; i++) {
            def generater = new RuleFunctionGenerator(factModel)
            def start = System.currentTimeMillis()
            def cc = generater.generateFunction()
            def end = System.currentTimeMillis()
            println('执行时间：' + (end - start) / 1000 + '秒')
            println cc
        }
    }
}
