package com.qk.dam.cache;

import org.springframework.cache.Cache;
import org.springframework.lang.Nullable;

import java.util.Collection;

/**
 * @author zhudaoming
 */
public interface CaffeineCacheManager {

	@Nullable
	Cache getCache(String name);

	Collection<String> getCacheNames();

}