package com.qk.dm.groovy.model;

import com.qk.dm.groovy.model.base.RuleFunctionInfo;
import lombok.Data;

import java.util.List;

/**
 * @author daomingzhu
 * @date 2020/4/13 13:50
 */
@Data
public class RuleFunctionModel {
    private List<RuleFunctionInfo> ruleFunctionInfos;
    private List<Object> data;
}
