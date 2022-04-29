
package com.qk.dam.cache;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface QueryCache {

	@AliasFor("cacheNames")
	CacheManagerEnum[] value() default {};

	@AliasFor("value")
	CacheManagerEnum[] cacheNames() default {};

	String key() default "";

	String keyGenerator() default "";

	String cacheManager() default "";

	String cacheResolver() default "damCacheResolver";

	String condition() default "";

	String unless() default "";

	boolean sync() default false;

}
