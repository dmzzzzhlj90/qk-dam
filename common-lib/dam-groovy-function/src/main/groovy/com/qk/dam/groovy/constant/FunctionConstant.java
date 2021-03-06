package com.qk.dam.groovy.constant;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 规则函数名称
 *
 * @author wjq
 * @date 2021/12/23
 * @since 1.0.0
 */
public class FunctionConstant {

    /**
     * 规则类型通用
     */
    public static final String RULE_FUNCTION_TYPE = "ruleFunction";

    private FunctionConstant() {
        throw new UnsupportedOperationException(" Construct Constants FunctionConstant ");
    }

    public static final String[] DEFINITION_FUNCTION_NAME_ARR = new String[]{
            "format"
    };

    public static boolean isExistFunctionName(String functionName) {
        return Arrays.asList(DEFINITION_FUNCTION_NAME_ARR).contains(functionName.split("\\(")[0]);
    }

    /**
     * 是否存在函数
     */
    public static List<String> isExistFunction(String sqlPartStr) {
        List<String> functionNames = new ArrayList<>();
        for (String functionName : DEFINITION_FUNCTION_NAME_ARR) {
            if (sqlPartStr.contains(functionName)) {
                functionNames.add(functionName);
            }
        }
        return functionNames;
    }
}