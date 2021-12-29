package com.qk.dam.groovy;

import com.qk.dam.groovy.engine.shell.FactsScriptShell;
import groovy.lang.Binding;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * @author wjq
 * @date 2021/12/28
 * @since 1.0.0
 */
public class TestDemo {

    static FactsScriptShell factsScriptShell = new FactsScriptShell( new Binding());
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
        Object evaluate = factsScriptShell.evaluate("10>0 && 5>10?'2':'3'");
        System.out.println(evaluate);

    }
}
