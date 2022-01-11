package com.qk.dam.groovy.function

import com.qk.dam.groovy.cache.ObjectCache
import com.qk.dam.groovy.engine.shell.FactsScriptShell

import java.util.function.BiFunction

/**
 * 执行函数
 *
 * @author wjq
 * @date 2021/12/23
 * @since 1.0.0
 */
class RuleFun {
    Binding binding = new Binding()

    FactsScriptShell factsScriptShell = new FactsScriptShell(binding)

//    BiFunction dateFunction = {ruleName,source ->
//        Script script = ObjectCache.getIfNull(ruleName,{ -> factsScriptShell.parse(ruleName) })
//        source.forEach({k,v->script.setProperty(k,v)});
//        script.setProperty("source",source);
//        def computeField = script.run()
//        computeField
//    }

    BiFunction ruleFunction = { source, entity ->
        Script script = ObjectCache.getIfNull(entity.expression,{ -> factsScriptShell.parse(entity.expression) })
        source.forEach({k,v->script.setProperty(k,v)});
        script.setProperty("source",source);
        def computeField = script.run()
        computeField
    }

}
