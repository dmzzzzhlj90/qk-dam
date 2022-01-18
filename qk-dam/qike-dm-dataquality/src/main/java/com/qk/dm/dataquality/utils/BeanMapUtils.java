package com.qk.dm.dataquality.utils;

import org.springframework.cglib.beans.BeanMap;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * BeanMapUtils
 *
 * @author wjq
 * @date 2022/01/18
 * @since 1.0.0
 */
public class BeanMapUtils {
    /**
     * 将对象转换为map
     */
    public static <T> Map<String, Object> changeBeanToMap(T bean) {
        Map<String, Object> map = new HashMap<>();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(String.valueOf(key), beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * 将map转换为bean对
     */
    public static <T> T changeMapToBean(Map<String, Object> map, T bean) {
        BeanMap beanMap = BeanMap.create(bean); // 识别对应类型的内容
        beanMap.putAll(map); // 添加数据
        return bean;
    }

    /**
     * 将List<Bean>转换为List<Map<String, Object>>
     */
    public static <T> List<Map<String, Object>> changeBeansToList(List<T> sourceList) {
        List<Map<String, Object>> srclist = Collections.emptyList();
        if (sourceList != null && sourceList.size() > 0) {
            srclist = new ArrayList<>(sourceList.size());
            Map<String, Object> map;
            for (T bean : sourceList) {
                map = changeBeanToMap(bean);
                srclist.add(map);
            }
        }
        return srclist;
    }

    /**
     * 将List<Map<String,Object>>转换为List<Bean>
     */
    public static <T> List<T> changeListToBeans(List<Map<String, Object>> sourceList, Class<T> clazz) {
        List<T> srclist = Collections.emptyList();
        if (sourceList != null && sourceList.size() > 0) {
            try {
                srclist = new ArrayList<>(sourceList.size());
                T bean;
                for (Map<String, Object> map1 : sourceList) {
                    bean = changeMapToBean(map1, clazz.getDeclaredConstructor().newInstance());
                    srclist.add(bean);
                }
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException ex) {
                ex.printStackTrace();
            }
        }
        return srclist;
    }
}