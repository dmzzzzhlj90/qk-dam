package com.qk.dm.dataquality.groovy.pojo;

import com.qk.dam.groovy.model.RuleFunctionInfo;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * 加载Groovy函数配置信息
 *
 * @author wjq
 * @date 2022/1/10
 * @since 1.0.0
 */
@Data
@Builder
public class RuleFunctionModelInfo {

    Map<String, RuleFunctionInfo> ruleFunctionInfoMap;

}
