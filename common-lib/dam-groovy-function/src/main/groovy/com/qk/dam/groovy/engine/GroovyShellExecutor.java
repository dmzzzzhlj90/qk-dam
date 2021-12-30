package com.qk.dam.groovy.engine;

import com.qk.dam.groovy.engine.shell.FactsScriptShell;
import groovy.lang.Binding;

/**
 * Groovy Shell执行器
 *
 * @author wjq
 * @date 2021/12/29
 * @since 1.0.0
 */
public class GroovyShellExecutor {
    static FactsScriptShell factsScriptShell = new FactsScriptShell( new Binding());

    public static FactsScriptShell factsScriptShell() {
        return factsScriptShell;
    }

    public static Object evaluate(String script) {
        return factsScriptShell.evaluate(script);
    }
}
