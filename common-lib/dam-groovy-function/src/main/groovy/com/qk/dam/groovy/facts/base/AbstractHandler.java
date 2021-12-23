package com.qk.dam.groovy.facts.base;

import lombok.Data;

import java.util.function.BiFunction;

/**
 * 基础处理器
 *
 * @author wjq
 * @date 2021/12/23
 * @since 1.0.0
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
