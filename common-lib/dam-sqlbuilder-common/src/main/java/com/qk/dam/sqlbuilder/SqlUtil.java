package com.qk.dam.sqlbuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author shenpj
 * @date 2021/12/6 4:02 下午
 * @since 1.0.0
 */
public class SqlUtil {
    public static void main(String[] args) {
        Map<String, String> single = new HashMap<>();
        single.put("t1", "qk_dqc_rule_template");
        single.put("col1", "engine_type");

        String content = "select ifnull(a,0), ifnull(b,0), ifnull(a/b, 1) from (select (select count(1) from ${t1} where isnull(${col1})) a,(select count(1) from ${t1}) b) c";
        String scan = "1=1";

//        String content = "select min($col1),max($col1),ROUND(avg($col1),2),sum($col1) from $t1";
//        String scan = "";

        if (!"".equals(scan)) {
            //带where条件
            for (Map.Entry<String, String> name :  single.entrySet()) {
                String condition = "\\$\\{" + name.getKey() + "\\}";
                if (name.getKey().equals("t1")) {
                    content = matchReplaceWithCondition(content, condition + " where", name.getValue(), " where 1=1 and");
                    content = matchReplaceWithCondition(content, condition, name.getValue(), " where 1=1");
                } else {
                    content = matchReplaceWithCondition(content, condition, name.getValue(), "");
                }
            }
        } else {
            for (Map.Entry<String, String> name :  single.entrySet()) {
                String condition = "\\$\\{" + name.getKey() + "\\}";
                // 先匹配现有的内容
                content = matchReplaceWithCondition(content, condition, name.getValue(), "");
            }
        }
        System.out.println("last : " + content);
    }

    private static String matchReplaceWithCondition(String content, String pattern, String value, String scan) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(content);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String group = m.group();
            m.appendReplacement(sb, group == null ? "" : ("").concat(value).concat(scan));
        }
        m.appendTail(sb);
        return sb.toString();
    }
}
