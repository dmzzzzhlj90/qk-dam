package com.qk.dam.log.annotation;

import com.qk.dam.log.enums.LogLevel;

import java.lang.annotation.*;

/**
 * 后置通知
 * @author shenpengjie
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AfterLogger {

    /**
     * 日志级别
     */
    LogLevel logLevel() default LogLevel.INFO;
}
