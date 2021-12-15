package com.qk.dm.dataquality.groovy;

import groovy.lang.GroovyObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * groovy脚本执行器
 *
 * @author wjq
 * @date 2021/12/15
 * @since 1.0.0
 */
@Component
public class GroovyScriptHandler {

    public static Object execute(String scriptFileName, String scriptFunctionName, List<Object> params) {
        //scriptFileName:文件名,scriptFunctionName:文件的方法,params:参数
        GroovyObject groovyObject = GroovyScriptFactory.getGroovyObject(scriptFileName);
        return groovyObject.invokeMethod(scriptFunctionName, params);
    }

    public static void main(String[] args) {
        List<Object> params = new ArrayList<>();
        params.add("curry");
        params.add("33");
        params.add(2974);

        Object execute = execute("test", "threePointerRecord", params);
        System.out.println(execute);
    }
}
