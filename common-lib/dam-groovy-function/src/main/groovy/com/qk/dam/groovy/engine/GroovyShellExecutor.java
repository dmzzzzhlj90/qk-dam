package com.qk.dam.groovy.engine;

import com.qk.dam.groovy.engine.shell.FactsScriptShell;
import groovy.lang.Binding;

import java.util.List;
import java.util.Map;

/**
 * Groovy Shell执行器
 *
 * @author wjq
 * @date 2021/12/29
 * @since 1.0.0
 */
public class GroovyShellExecutor {
    static FactsScriptShell factsScriptShell;

    public static FactsScriptShell factsScriptShell() {
        return factsScriptShell;
    }

    /**
     * 绑定参数执行规则表达式
     */
    public static Object evaluateBinding(List<String> fields, Map<String, List<Object>> dataMap, String expression) {
        var binding = new Binding();
        for (String field : fields) {
            binding.setVariable(field, dataMap.get(field));
        }

        factsScriptShell = new FactsScriptShell(binding);
        return factsScriptShell.evaluate(expression);
    }
}
