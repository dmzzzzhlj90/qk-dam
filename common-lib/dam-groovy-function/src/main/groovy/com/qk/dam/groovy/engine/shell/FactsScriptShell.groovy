package com.qk.dam.groovy.engine.shell


import com.qk.dam.groovy.function.AbstractFunc
import org.codehaus.groovy.control.CompilerConfiguration

/**
 * 规则函数GroovyShell
 *
 * @author wjq
 * @date 2021/12/23
 * @since 1.0.0
 */
class FactsScriptShell {
    GroovyShell groovyShell

    FactsScriptShell(Binding binding){
        init(binding)
    }
    void init(Binding binding){
        def config = new CompilerConfiguration()
        config.setScriptBaseClass(AbstractFunc.class.getName())
        groovyShell = new GroovyShell(this.class.classLoader,binding,config)
    }
    def evaluate(String script){
        groovyShell.evaluate(script)
    }
    def parse(String script){
        groovyShell.parse(script)
    }
}
