package com.qk.dam.log.annotation;

import com.qk.dam.log.enums.LogLevel;

import java.lang.annotation.*;

/**
 * 后置返回通知
 * @author shenpengjie
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AfterReturnLogger {
    /**
     * ture-打印返回值 false-不打印
     */
    boolean returnResult() default false;

    /**
     * 单个参数 例：#product.name 不支持整体类
     */
    String param() default "";

    /**
     * 日志级别
     */
    LogLevel logLevel() default LogLevel.INFO;
}
