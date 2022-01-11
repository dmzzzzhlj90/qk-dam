package com.qk.dam.groovy.model.base;

import lombok.*;

import java.util.List;
import java.util.Map;

/**
 * 事实条件函数
 *
 * @author wjq
 * @date 2021/12/17
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FactParam {
    private String functionName;
    private List<String> params;
    private String description;
    private String type;
    private Map<String, Object> defaultVal;
}


