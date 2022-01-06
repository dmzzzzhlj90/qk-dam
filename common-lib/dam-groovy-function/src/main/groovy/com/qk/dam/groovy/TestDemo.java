package com.qk.dam.groovy;

import com.qk.dam.groovy.engine.GroovyShellExecutor;
import com.qk.dam.groovy.engine.shell.FactsScriptShell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wjq
 * @date 2021/12/28
 * @since 1.0.0
 */
public class TestDemo {
    static double[] ac = {10, 800, 0.2};
    static double[] ac2 = {10, 800, 0.2};

    static FactsScriptShell factsScriptShell;

    public static void main(String args[]) {
//        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
//        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("groovy");
//        Bindings bindings =  scriptEngine.createBindings();
//        long start = System.currentTimeMillis();
//        for(int status = 1 ; status < 10; status++){
//            scriptEngine.put("status",status);
//            String expression = "status = 2 ? '1' : 0;";
//            try {
//                Object oj = scriptEngine.eval(expression);
//                System.out.println(oj);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        System.out.println(System.currentTimeMillis() - start);

//        var acc =  new Binding();
//        acc.setVariable("code",ac);
//        acc.setVariable("name",ac2);
//        factsScriptShell = new FactsScriptShell( acc);
//
//        Object evaluate = factsScriptShell.evaluate("(code[0]>0&&code[2]>0.1)&&(name[0]>0&&name[2]>0.1)");
        //
        String expression = "(code[0]>0&&code[2]>0.1)&&(name[0]>0&&name[2]>0.1)";
        List<String> fields = new ArrayList<>();
        fields.add("code");
        fields.add("name");

        Map<String, List<Object>> dataMap = new HashMap<>(16);
        List<Object> data1 = new ArrayList<>();
        data1.add(10);
        data1.add(800);
        data1.add(0.2);
        List<Object> data2 = new ArrayList<>();
        data2.add(10);
        data2.add(800);
        data2.add(0.2);
        dataMap.put("code", data1);
        dataMap.put("name", data2);

        Object evaluate = GroovyShellExecutor.evaluateBinding(fields, dataMap, expression);
        System.out.println(evaluate);
    }


}
