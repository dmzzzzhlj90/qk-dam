package com.qk.dm.groovy.engine.shell


import com.qk.dm.groovy.function.AbstractFunc
import org.codehaus.groovy.control.CompilerConfiguration

/**
 * @author daomingzhu
 * @date 2020/4/9 16:40
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
