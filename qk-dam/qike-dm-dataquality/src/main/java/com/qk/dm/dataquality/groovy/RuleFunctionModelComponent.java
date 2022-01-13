package com.qk.dm.dataquality.groovy;

import com.google.common.collect.Lists;
import com.qk.dam.groovy.constant.FunctionConstant;
import com.qk.dam.groovy.facts.RuleFunctionGenerator;
import com.qk.dam.groovy.model.FactModel;
import com.qk.dam.groovy.model.RuleFunctionInfo;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 规则函数模型构建器
 *
 * @author wjq
 * @date 2022/1/7
 * @since 1.0.0
 */
@Component
public class RuleFunctionModelComponent {

    /**
     * 根据正则扫描指定需要执行的函数
     */
    public static final Pattern regex1 = Pattern.compile("\\$\\{(.*?)}");
    /**
     * 根据正则扫描执行表达式
     */
    public static final Pattern regex2 = Pattern.compile("\\$\\{([^}]*)\\}");

    /**
     * 执行Groovy函数生成sql片段
     *
     * @param scanSql
     * @return String
     */
    public String scanSqlByGroovyFunction(String scanSql) {
        String realScanSql = scanSql;
        //匹配扫描条件sql中存在的函数
        List<String> existSqlPartList = matcherSqlPart(scanSql);
        if (existSqlPartList.size() > 0) {
            //执行表达式
            List<String> expressions = getExpressions(existSqlPartList);
            //执行Groovy函数
            Map<String, Object> groovyResultMap = executeGroovyFunction(expressions);
            //替换函数sql参数
            realScanSql = getRealScanSql(existSqlPartList, groovyResultMap, realScanSql);
        } else {
            //存在特殊函数
            realScanSql = scanSql;
        }
        return realScanSql;
    }

    /**
     * 匹配扫描条件sql中存在的函数
     */
    public List<String> matcherSqlPart(String scanSql) {
        List<String> existSqlPartList = new ArrayList<>();
        Matcher m = regex1.matcher(scanSql);
        while (m.find()) {
            String group = m.group();
            existSqlPartList.add(group);
        }
        return existSqlPartList;
    }

    /**
     * 执行表达式
     */
    private List<String> getExpressions(List<String> existSqlPartList) {
        List<String> expressions = new ArrayList<>();
        //获取需要执行函数的sql片段
        for (String sqlPart : existSqlPartList) {
            Matcher matcher2 = regex2.matcher(sqlPart);
            while (matcher2.find()) {
                String expression = matcher2.group(1);
                expressions.add(expression);
            }
        }
        return expressions;
    }

    /**
     * 执行Groovy函数
     */
    private Map<String, Object> executeGroovyFunction(List<String> expressions) {
        FactModel model = new FactModel();
        List<RuleFunctionInfo> ruleFunctionInfos = Lists.newArrayList();

        for (String exp : expressions) {
            String functionName = "${" + exp + "}";
            RuleFunctionInfo ruleFunctionInfo = new RuleFunctionInfo(functionName, null, "", null, null);
            ruleFunctionInfo.setExpression(exp);
            ruleFunctionInfos.add(ruleFunctionInfo);
        }
        model.setRuleFunctionInfo(ruleFunctionInfos);
        RuleFunctionGenerator ruleFunctionGenerator = new RuleFunctionGenerator(model);
        return ruleFunctionGenerator.generateFunction();
    }

    /**
     * 替换函数sql参数
     */
    private String getRealScanSql(
                                List<String> existSqlPartList,
                                Map<String, Object> groovyResultMap,
                                String realScanSql) {
        for (String functionName : existSqlPartList) {
            //获取函数执行结果
            Map<String, Object> groovySqlResultMap = (Map<String, Object>) groovyResultMap.get(FunctionConstant.RULE_FUNCTION_TYPE);
            String executeResult = (String) groovySqlResultMap.get(functionName);
            if (!ObjectUtils.isEmpty(executeResult)) {
                //替换执行表达式
                realScanSql = realScanSql.replace(functionName, executeResult);
            }
        }
        return realScanSql;
    }

}
