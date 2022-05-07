package com.qk.dm.dolphin.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;

import static com.qk.dam.datasource.utils.ConnectInfoConvertUtils.objectMapper;

/**
 * 数据质量_规则分类目录
 *
 * @author wjq
 * @date 2021/11/12
 * @since 1.0.0
 */
public class ConstantUtil {
    public static <T> T changeObjectToClass(Object data, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(objectMapper.writeValueAsString(data), clazz);
    }
}
