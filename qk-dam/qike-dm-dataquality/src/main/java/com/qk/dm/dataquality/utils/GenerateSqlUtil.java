package com.qk.dm.dataquality.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 根据模板生成SQL工具类
 *
 * @author wjq
 * @date 2021/12/4
 * @since 1.0.0
 */
public class GenerateSqlUtil {

    /**
     * Schema_Table
     */
    public static final String SCHEMA_TABLE = "t";

    /**
     * Column
     */
    public static final String COL = "col";

    /**
     * 默认序列号
     */
    public static final String DEFAULT_NUM = "1";

    /**
     * UNION ALL
     */
    public static final String UNION_ALL = " UNION ALL ";

    public static final String WHERE = "where";
    public static final String AND = " and ";
    public static final String EQUAL_SIGN = " = ";

    public static final String WHERE_PART = " where 1=1 ";
    public static final String SINGLE_QUOTES = "'";
    public static final String SINGLE_QUOTES_MARK = "<S_Q> ";

    private static final String START_SPACES_REGEX = "^\\s+";
    public static final String EMPTY_STRING = "";

    /**
     * 根据规则模板生成执行SQL
     *
     * @param tempSql
     * @param conditionMap
     * @return
     */
    public static String generateSql(String tempSql, Map<String, String> conditionMap, String wherePartSql) {
        if (conditionMap.size() > 0) {
            for (String conditionKey : conditionMap.keySet()) {
                String value = conditionMap.get(conditionKey);
                // 正则规则匹配占位符位置
                tempSql = matchReplaceWithCondition(tempSql, conditionKey, value, wherePartSql);
            }
        }
        return tempSql;
    }

    /**
     * 正则规则匹配占位符位置
     * @param tempSql
     * @param condition
     * @param value
     * @param wherePartSql
     * @return
     */
    public static String matchReplaceWithCondition(String tempSql, String condition, String value, String wherePartSql) {
        String replaceSqlStr = "";
        String pattern = "\\$\\{" + condition + "\\}";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(tempSql);
        StringBuffer sqlBuffer = new StringBuffer();
        while (m.find()) {
            String group = m.group();
            m.appendReplacement(sqlBuffer, group == null ? "" : value);
        }
        m.appendTail(sqlBuffer);
        replaceSqlStr = setSqlWhereCondition(condition, value, sqlBuffer, wherePartSql);
        return replaceSqlStr;
    }

    /**
     * 匹配where条件
     *
     * @param condition
     * @param value
     * @param sqlBuffer
     * @param wherePartSql
     * @return
     */
    private static String setSqlWhereCondition(String condition, String value, StringBuffer sqlBuffer, String wherePartSql) {
        String replaceSqlStr;
        if (condition.contains(SCHEMA_TABLE)) {
            String sqlStr = sqlBuffer.toString();
            if (sqlStr.contains(WHERE)) {
                replaceSqlStr = sqlStr.replace(WHERE, AND);
                //wherePart "where 1=1 and code ='???' "
                replaceSqlStr = replaceSqlStr.replace(value, " " + value + " " + wherePartSql);
            } else {
                //wherePart "where 1=1 and code ='???' "
                replaceSqlStr = sqlStr.replace(value, " " + value + " " + wherePartSql);
            }
        } else {
            replaceSqlStr = sqlBuffer.toString();
        }
        return replaceSqlStr;
    }

    /**
     * 去除sql片段首位置空格
     * @param value
     * @return
     */
    public static String trimStart(String value) {
        return value.replaceFirst(START_SPACES_REGEX, EMPTY_STRING);
    }

    public static String getReplaceSql(String content) {
        String words = "([a-zA-Z0-9._]*)";
        String patternReg = (words + "\\s*=\\s*\\$\\{" + words + "\\}");

        String pattern = patternReg + " and|" + patternReg + "|and " + patternReg;

        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(content);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String value = m.group();
            m.appendReplacement(sb, value == null ? "" : "");
        }
        m.appendTail(sb);
        System.out.println("getReplaceSql: " + sb.toString());
        return sb.toString();
    }

    public static void main(String[] args) {
        Map<String, String> single = new HashMap<>();
        single.put("paramName", "me");
        single.put("mean", "wonders");

        List<String> one = Lists.newArrayList("paramName", "mean");
//        String content = "select * from account WHERE field_name1 = $param.paramName and field_name2 = $global.data and field_name3=  $mean and field_name4 = $dream";
        String content = "select * from account WHERE field_name1 = ${paramName} and field_name3=  ${mean} and aaa=${aaa}";
        for (String e : one) {
            String value = single.get(e);
            // 先匹配现有的内容
            content = matchReplaceWithCondition(content, e, value,"");
        }
        // 匹配参数内容为空的情况
        content = getReplaceSql(content).trim();
        if (StringUtils.endsWithIgnoreCase(content, "where")) {
            content = StringUtils.removeEndIgnoreCase(content, "where");
        }
        System.out.println("last " + content);
    }
}
