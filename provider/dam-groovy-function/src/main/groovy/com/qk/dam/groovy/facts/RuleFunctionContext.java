package com.qk.dam.groovy.facts;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.qk.dam.groovy.facts.base.AbstractHandler;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 规则函数上下文信息
 *
 * @author wjq
 * @date 2021/12/23
 * @since 1.0.0
 */
public class RuleFunctionContext {
    private final List<AbstractHandler> handlers = Lists.newLinkedList();

    public RuleFunctionContext _self() {
        return this;
    }

    public RuleFunctionContext option() {
        return _self();
    }

    public void initHandler(Consumer<List<AbstractHandler>> function) {
        function.accept(handlers);
    }

    public void execute() {
        handlers.forEach(AbstractHandler::doHandler);
    }

    public Map<String, Object> result() {
        Map<String, Object> rts = Maps.newHashMap();
        handlers.forEach(abstractHandler -> rts.put(abstractHandler.getHandlerName(), abstractHandler.result));
        return rts;
    }
}
