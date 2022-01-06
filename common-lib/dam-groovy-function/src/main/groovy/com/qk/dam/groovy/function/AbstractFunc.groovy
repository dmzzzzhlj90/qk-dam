package com.qk.dam.groovy.function

import java.time.format.DateTimeFormatter

/**
 * 业务数据-元数据处理的函数库
 *
 * @author wjq
 * @date 2021/12/23
 * @since 1.0.0
 */
abstract class AbstractFunc extends Script {

//    def format(String fieldKey,String pattern){
//        return fieldKey
//    }

    def format(String fieldKey,String pattern){
        Map<String,Object> source = this.getProperty("source")
        if (source!=null&&source[fieldKey]!=null){
            return source[fieldKey].format(DateTimeFormatter.ofPattern(pattern))
        }
        ''
    }

    def tradeDay(String tradeDay){
        return tradeDay
    }

}
