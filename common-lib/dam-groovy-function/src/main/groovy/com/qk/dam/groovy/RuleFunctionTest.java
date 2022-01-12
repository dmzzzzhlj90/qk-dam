package com.qk.dam.groovy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wjq
 * @date 2022/1/12
 * @since 1.0.0
 */
public class RuleFunctionTest {

    public static void main(String[] args) {
        String sql = " date =${tradeDay('2022/01/11',1,'yyyy/MM/dd')} and date2 =date =${tradeDay2('2022/01/11',1,'yyyy/MM/dd')} ";

        // 匹配方式
        Pattern p = Pattern.compile("\\$\\{(.*?)}");
        // 匹配】
        Matcher matcher = p.matcher(sql);
        // 处理匹配到的值
        while (matcher.find()) {
            System.out.println("waaa: " + matcher.group());
        }
    }

}
