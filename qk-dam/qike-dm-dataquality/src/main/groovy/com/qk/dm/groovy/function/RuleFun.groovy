package com.qk.dm.groovy.function

import com.qk.dm.groovy.cache.ObjectCache
import com.qk.dm.groovy.engine.shell.FactsScriptShell

import java.util.function.BiFunction
/**
 * @author daomingzhu
 * @date 2020/4/14 16:29
 */
class RuleFun {
    Binding binding = new Binding()

    FactsScriptShell factsScriptShell = new FactsScriptShell(binding)

    BiFunction computeFunc = {ruleName,functionInfos ->
        Script script = ObjectCache.getIfNull(ruleName,{ -> factsScriptShell.parse(ruleName) })
//        source.forEach({k,v->script.setProperty(k,v)});
//        script.setProperty("source",source);
        def computeField = script.run()
        computeField
    }

}
