package com.qk.dm.dataservice.mapping;

import com.qk.dm.dataservice.rest.mapping.DataServiceEnum;
import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

/**
 * @author zhudaoming
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface DataServiceMapping {
    String name() default "";

    @AliasFor("path")
    DataServiceEnum[] value() default {};

    @AliasFor("value")
    DataServiceEnum[] path() default {};

    RequestMethod[] method() default {};

    String[] params() default {};

    String[] headers() default {};

    String[] consumes() default {};

    String[] produces() default {};
}
