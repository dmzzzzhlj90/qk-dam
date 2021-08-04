package com.qk.dm.metadata.respose;

import java.lang.annotation.*;

/**
 * controller 统一返回值注解
 * @author wangzp
 * @date 2021-07-30 15:10
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseWrapper {

}
