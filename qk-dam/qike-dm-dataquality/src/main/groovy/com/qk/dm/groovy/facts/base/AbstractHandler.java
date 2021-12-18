package com.qk.dm.groovy.facts.base;

import lombok.Data;

import java.util.function.BiFunction;

/**
 * @author daomingzhu
 * @date 2020/4/13 15:56
 */
@Data
public abstract class AbstractHandler {
    public AbstractHandler(String handlerName, BiFunction expressCall) {
        this.handlerName = handlerName;
        this.expressCall = expressCall;
    }

    public abstract void doHandler();

    private String handlerName;
    private BiFunction expressCall;
    public Object result;

}
