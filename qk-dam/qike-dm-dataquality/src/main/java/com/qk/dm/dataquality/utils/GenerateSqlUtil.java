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
     *  UNION ALL
     */
    public static final String UNION_ALL = " UNION ALL ";

    public static String generateSql(String tempSql, Map<String, String> conditionMap) {
        if (conditionMap.size() > 0) {
            for (String conditionKey : conditionMap.keySet()) {
                String value = conditionMap.get(conditionKey);
                // 先匹配现有的内容
                tempSql = matchReplaceWithCondition(tempSql, conditionKey, value);
            }
        }
        return tempSql;
    }

    public static String matchReplaceWithCondition(String tempSql, String condition, String value) {
        String pattern = "\\$\\{" + condition + "\\}";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(tempSql);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String group = m.group();
            m.appendReplacement(sb, group == null ? "" : value);
        }
        m.appendTail(sb);
        return sb.toString();
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
            content = matchReplaceWithCondition(content, e, value);
        }
        // 匹配参数内容为空的情况
        content = getReplaceSql(content).trim();
        if (StringUtils.endsWithIgnoreCase(content, "where")) {
            content = StringUtils.removeEndIgnoreCase(content, "where");
        }
        System.out.println("last " + content);
    }
}
