package com.qk.dam.metedata.util;

import com.qk.dam.metedata.config.ApplicationContextSup;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author zhudaoming
 */
@Component
public class SpringApplicationContextHolder implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext ac) throws RuntimeException {
        ApplicationContextSup.applicationContext = ac;
    }
    public static ApplicationContext getApplicationContext(){
        return ApplicationContextSup.applicationContext;
    }
}
