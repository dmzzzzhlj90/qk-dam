package com.qk.dam.log.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shenpj
 * @date 2022/1/19 5:45 下午
 * @since 1.0.0
 */
public class ParameteUtil {
    private static final ExpressionEvaluator<String> EVALUATOR = new ExpressionEvaluator<>();

    public static Map<String, Object> invokeParamters(JoinPoint joinPoint, int[] parameterNames) {
        //参数名称
        String[] paramNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
        //结果
        Object[] paramValues = joinPoint.getArgs();
        return invokeParamters(paramNames,paramValues,parameterNames);
    }

    public static Map<String, Object> invokeParamters(String[] paramNames, Object[] paramValues, int[] parameterNames) {
        Map<String, Object> param = new HashMap<>(8);
        Arrays.stream(parameterNames)
                .forEach(item -> {
                    int i = item - 1;
                    if (i < paramValues.length && i < paramNames.length) {
                        param.put(paramNames[i], paramValues[i]);
                    }
                });
        return param;
    }

    public static String evalLockParam(JoinPoint joinPoint, String lockParam) {
        Object[] args = joinPoint.getArgs();
        Object target = joinPoint.getTarget();
        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        return evalLockParam(args, target, ms, lockParam);
    }

    /**
     * 解析EL表达式
     *
     * @param lockParam 需要解析的EL表达式
     * @return 解析出的值
     */
    public static String evalLockParam(Object[] args, Object target, MethodSignature ms, String lockParam) {
        Method method = ms.getMethod();
        Class<?> targetClass = target.getClass();
        EvaluationContext context = EVALUATOR.createEvaluationContext(target, target.getClass(), method, args);
        AnnotatedElementKey elementKey = new AnnotatedElementKey(method, targetClass);
        return EVALUATOR.condition(lockParam, elementKey, context, String.class);
    }


}
