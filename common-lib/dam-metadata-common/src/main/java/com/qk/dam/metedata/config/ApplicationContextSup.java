package com.qk.dam.metedata.config;

import org.springframework.context.ApplicationContext;

public class ApplicationContextSup {
    private ApplicationContextSup() {
        throw new IllegalStateException("Utility class");
    }
    public static ApplicationContext applicationContext;
}
