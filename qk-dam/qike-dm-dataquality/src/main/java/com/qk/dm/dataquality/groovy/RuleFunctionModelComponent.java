package com.qk.dm.dataquality.groovy;

import com.google.common.collect.Lists;
import com.qk.dam.groovy.constant.FunctionConstant;
import com.qk.dam.groovy.facts.RuleFunctionGenerator;
import com.qk.dam.groovy.model.FactModel;
import com.qk.dam.groovy.model.RuleFunctionInfo;
import com.qk.dm.dataquality.groovy.pojo.RuleFunctionModelInfo;
import com.qk.dm.dataquality.utils.GenerateSqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 规则函数模型构建器
 *
 * @author wjq
 * @date 2022/1/7
 * @since 1.0.0
 */
@Component
public class RuleFunctionModelComponent {

    private RuleFunctionModelInfo ruleFunctionModelInfo;

    @Autowired
    public RuleFunctionModelComponent(RuleFunctionModelInfo ruleFunctionModelInfo) {
        this.ruleFunctionModelInfo = ruleFunctionModelInfo;
    }

    /**
     * 执行Groovy函数生成sql片段
     *
     * @param scanSql
     * @return String
     */
    public String scanSqlByGroovyFunction(String scanSql) {
        String realScanSql = "";
        //是否存在需要执行的函数
        Map<String, RuleFunctionInfo> existFunctionMap = isExistFunction(scanSql);

        if (existFunctionMap.size() > 0) {
            //真正需要被执行的函数带有参数信息等
            getSqlPartFunctions(scanSql, existFunctionMap);
            //执行Groovy函数
            Map<String, Object> groovyResultMap = executeGroovyFunction(existFunctionMap);
            //替换函数sql参数
            realScanSql = getRealScanSql(scanSql, existFunctionMap, groovyResultMap, realScanSql);
        } else {
            //存在特殊函数
            realScanSql = scanSql;
        }
        return realScanSql;
    }

    /**
     * 是否存在需要执行的函数
     */
    public Map<String, RuleFunctionInfo> isExistFunction(String scanSql) {
        Map<String, RuleFunctionInfo> existFunctionMap = new HashMap<>(16);
        Map<String, RuleFunctionInfo> ruleFunctionInfoMap = ruleFunctionModelInfo.getRuleFunctionInfoMap();

        Set<String> ruleFunctionKeySet = ruleFunctionInfoMap.keySet();
        for (String functionName : ruleFunctionKeySet) {
            if (scanSql.contains(functionName)) {
                existFunctionMap.put(functionName, ruleFunctionInfoMap.get(functionName));
            }
        }
        return existFunctionMap;
    }

    /**
     * 真正需要被执行的函数带有参数信息等
     */
    private void getSqlPartFunctions(String scanSql, Map<String, RuleFunctionInfo> existFunctionMap) {
        //去除sql片段首位置空格
        String trimStartScanSql = GenerateSqlUtil.trimStart(scanSql);
        //and截取
        String[] andPartArr = trimStartScanSql.split(GenerateSqlUtil.AND);
        //获取需要执行函数的sql片段
        for (String sqlPart : andPartArr) {
            String sqlPartFunction = "";
            for (String functionName : existFunctionMap.keySet()) {
                if (sqlPart.contains(functionName)) {
                    RuleFunctionInfo ruleFunctionInfo = existFunctionMap.get(functionName);
                    String[] sqlPartArr = sqlPart.split(functionName);
                    //sql片段执行函数表达获取
                    sqlPartFunction = functionName + sqlPartArr[sqlPartArr.length - 1];
                    //Groovy函数执行表达式
                    ruleFunctionInfo.setExpression(sqlPartFunction);
                    existFunctionMap.put(functionName, ruleFunctionInfo);
                }
            }
        }
    }

    /**
     * 执行Groovy函数
     */
    private Map<String, Object> executeGroovyFunction(Map<String, RuleFunctionInfo> existFunctionMap) {
        FactModel model = new FactModel();
        List<RuleFunctionInfo> ruleFunctionInfos = Lists.newArrayList();

        for (String functionName : existFunctionMap.keySet()) {
            RuleFunctionInfo ruleFunctionInfo = existFunctionMap.get(functionName);
            List<String> params = ruleFunctionInfo.getParams();
            if (params != null && params.size() > 0) {
                //TODO 需要指定函数参数信息
            }
            ruleFunctionInfos.add(ruleFunctionInfo);
        }
        model.setRuleFunctionInfo(ruleFunctionInfos);
        RuleFunctionGenerator ruleFunctionGenerator = new RuleFunctionGenerator(model);
        return ruleFunctionGenerator.generateFunction();
    }

    /**
     * 替换函数sql参数
     */
    private String getRealScanSql(String scanSql,
                                  Map<String, RuleFunctionInfo> existFunctionMap,
                                  Map<String, Object> groovyResultMap,
                                  String realScanSql) {
        for (String functionName : existFunctionMap.keySet()) {
            RuleFunctionInfo ruleFunctionInfo = existFunctionMap.get(functionName);
            //获取函数执行结果
            Map<String, Object> groovySqlResultMap = (Map<String, Object>) groovyResultMap.get(FunctionConstant.RULE_FUNCTION_TYPE);
            String executeResult = (String) groovySqlResultMap.get(functionName);
            if (!ObjectUtils.isEmpty(executeResult)) {
                //替换执行表达式
                realScanSql = scanSql.replace(ruleFunctionInfo.getExpression(), executeResult);
            } else {
                realScanSql = scanSql;
            }
        }
        return realScanSql;
    }

}
