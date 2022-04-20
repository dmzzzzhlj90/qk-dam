package com.qk.dm.dataquality.groovy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wjq
 * @date 2022/1/12
 * @since 1.0.0
 */
public class RuleFunctionTest {

    public static void main(String[] args) {
        String sql = " date =${tradeDay('2022/01/11',1,'yyyy/MM/dd')} ";


        String pattern = "/(\\$\\{.+})/";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(sql);
        StringBuffer sqlBuffer = new StringBuffer();
        while (m.find()) {
            String group = m.group();
            System.out.println(group);
        }
    }

}
