package com.qk.dam.log.annotation;

import com.qk.dam.log.enums.LogLevel;

import java.lang.annotation.*;

/**
 * 前置通知
 * @author shenpengjie
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BeforeLogger {
    /**
     * 参数集 {1,2,3} 支持类
     */
    int[] parameters() default {};

    /**
     * 单个参数 例：#product.name 不支持整体类
     */
    String param() default "";

    /**
     * 日志级别
     */
    LogLevel logLevel() default LogLevel.INFO;
}
