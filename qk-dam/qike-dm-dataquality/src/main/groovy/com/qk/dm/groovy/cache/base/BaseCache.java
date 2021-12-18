package com.qk.dm.groovy.cache.base;


public abstract class BaseCache<E> {
    public abstract void set(String key, E e);
    public abstract E get(String key);


}
