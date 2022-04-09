//package com.qk.dm.dataquality.groovy;
//
//import groovy.lang.GroovyObject;
//import org.springframework.stereotype.Component;
//
//import java.com.qk.dm.groovy.util.ArrayList;
//import java.com.qk.dm.groovy.util.List;
//import java.com.qk.dm.groovy.util.concurrent.ConcurrentHashMap;
//
///**
// * groovy脚本执行器
// *
// * @author wjq
// * @date 2021/12/15
// * @since 1.0.0
// */
//@Component
//public class GroovyScriptHandler {
//
//    /**
//     *
//     *
//     * @param scriptFileName 文件名
//     * @param scriptFunctionName 文件的方法
//     * @param params 参数
//     * @return Object
//     */
//    public static Object execute(String scriptFileName, String scriptFunctionName, List<Object> params) {
//        //获取Groovy脚本对象
//        ConcurrentHashMap<String, GroovyObject> groovyObjects = GroovyScriptResource.groovyObjects;
//        GroovyObject groovyObject = groovyObjects.get(scriptFileName);
//
//        return groovyObject.invokeMethod(scriptFunctionName, params);
//    }
//
//    public static void main(String[] args) {
//        List<Object> params = new ArrayList<>();
//        params.add("20211215");
//        params.add(1);
//
//        Object execute = execute("TradeDate", "tradeDay", params);
//        System.out.println(execute);
//    }
//}
