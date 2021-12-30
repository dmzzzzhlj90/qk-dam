package com.qk.dam.groovy.model;

import com.qk.dam.groovy.model.base.RuleFunctionInfo;
import lombok.Data;

import java.util.List;

/**
 * 规则函数模型
 *
 * @author wjq
 * @date 2021/12/23
 * @since 1.0.0
 */
@Data
public class RuleFunctionModel {
    private List<RuleFunctionInfo> ruleFunctionInfos;
    private List<Object> data;
}
