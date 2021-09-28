package com.qk.dam.authorization.access;

import com.qk.dam.authorization.Auth;
import org.apache.logging.log4j.util.Strings;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.log.LogMessage;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.method.AbstractMethodSecurityMetadataSource;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DamPostAnnotationSecurityMetadataSource  extends AbstractMethodSecurityMetadataSource {
    private final static String SPLITTER = ":";
    @Override
    public Collection<ConfigAttribute> getAttributes(Method method, Class<?> targetClass) {
        final Auth auth = findAnnotation(method, targetClass, Auth.class);
        ArrayList<ConfigAttribute> attrs = new ArrayList<>(2);
        if (auth!=null){
            ConfigAttribute configAttribute = new ConfigAttribute() {
                private static final long serialVersionUID = -3504669454162148665L;

                @Override
                public String getAttribute() {
                    String bizCls = auth.bizType().getBizCls();
                    String type = auth.actionType().getType();
                    return String.join(SPLITTER, bizCls, type);
                }
            };
            attrs.add(configAttribute);
        }

        attrs.trimToSize();
        return attrs;
    }

    private <A extends Annotation> A findAnnotation(Method method, Class<?> targetClass, Class<A> annotationClass) {
        // The method may be on an interface, but we need attributes from the target
        // class.
        // If the target class is null, the method will be unchanged.
        Method specificMethod = ClassUtils.getMostSpecificMethod(method, targetClass);
        A annotation = AnnotationUtils.findAnnotation(specificMethod, annotationClass);
        if (annotation != null) {
            this.logger.debug(LogMessage.format("%s found on specific method: %s", annotation, specificMethod));
            return annotation;
        }
        // Check the original (e.g. interface) method
        if (specificMethod != method) {
            annotation = AnnotationUtils.findAnnotation(method, annotationClass);
            if (annotation != null) {
                this.logger.debug(LogMessage.format("%s found on: %s", annotation, method));
                return annotation;
            }
        }
        // Check the class-level (note declaringClass, not targetClass, which may not
        // actually implement the method)
        annotation = AnnotationUtils.findAnnotation(specificMethod.getDeclaringClass(), annotationClass);
        if (annotation != null) {
            this.logger.debug(
                    LogMessage.format("%s found on: %s", annotation, specificMethod.getDeclaringClass().getName()));
            return annotation;
        }
        return null;
    }
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }
}
