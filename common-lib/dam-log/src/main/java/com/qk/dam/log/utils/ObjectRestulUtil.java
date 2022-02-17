package com.qk.dam.log.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;

import java.lang.reflect.Method;

/**
 * @author shenpj
 * @date 2022/1/20 10:23 下午
 * @since 1.0.0
 */
public class ObjectRestulUtil {
    private static final ExpressionEvaluator<String> EVALUATOR = new ExpressionEvaluator<>();
    private static final int MAX_NUMBER = 3;
    private static final int MIN_NUMBER = 2;

    /**
     * 根据EL表达式,获取单个对象中的值
     *
     * @param result
     * @param methodName
     * @return
     */
    public static Object evalLockReturn(Object result, String methodName) {
        String replace = methodName.replace("#", "");
        String[] split = replace.split("\\.");
        //如果长度为1，直接打印，如果为2，截取第二个，直接getFieldValueByName()
        if (split.length >= MAX_NUMBER) {
            return getFieldValue(split, 1, result);
        } else if (split.length >= MIN_NUMBER) {
            return getFieldValueByName(split[1], result);
        } else {
            return result;
        }
    }

    public static Object getFieldValue(String[] fieldArray, int i, Object result) {
        if (i < fieldArray.length) {
            Object fieldValue = getFieldValueByName(fieldArray[i], result);
            if (fieldValue != null) {
                return getFieldValue(fieldArray, i + 1, fieldValue);
            }
        }
        return result;

    }

    /**
     * 在object型单个对象中获取某值
     *
     * @param fieldName
     * @param result
     * @return
     */
    public static Object getFieldValueByName(String fieldName, Object result) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = result.getClass().getMethod(getter, new Class[]{});
            Object value = method.invoke(result, new Object[]{});
            return value;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 解析EL表达式
     *
     * @param lockParam 需要解析的EL表达式
     * @return 解析出的值
     */
    public static String evalLockParam(JoinPoint joinPoint, String lockParam) {
        Object[] args = joinPoint.getArgs();
        Object target = joinPoint.getTarget();
        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        return evalLockParam(args, target, ms, lockParam);
    }

    public static String evalLockParam(Object[] args, Object target, MethodSignature ms, String lockParam) {
        Method method = ms.getMethod();
        Class<?> targetClass = target.getClass();
        EvaluationContext context = EVALUATOR.createEvaluationContext(target, target.getClass(), method, args);
        AnnotatedElementKey elementKey = new AnnotatedElementKey(method, targetClass);
        return EVALUATOR.condition(lockParam, elementKey, context, String.class);
    }
}
