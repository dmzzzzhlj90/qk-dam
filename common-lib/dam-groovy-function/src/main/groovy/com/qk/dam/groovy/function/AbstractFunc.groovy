package com.qk.dam.groovy.function

import java.time.LocalDate
import java.time.format.DateTimeFormatter


/**
 * 业务数据-元数据处理的函数库
 *
 * @author wjq
 * @date 2021/12/23
 * @since 1.0.0
 */
abstract class AbstractFunc extends Script {

    def format(String fieldKey, String pattern) {
        Map<String, Object> source = this.getProperty("source")
        if (source != null && source[fieldKey] != null) {
            return source[fieldKey].format(DateTimeFormatter.ofPattern(pattern))
        }
        ''
    }

    def format2(String date, String pattern) {
        return date + "_" + pattern
    }

    /**
     * 交易日处理函数
     *
     * @param tradeDay T日
     * @param num N
     * @param pattern 格式化
     * @return
     */
    def tradeDay(String tradeDay, int num, String pattern) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(pattern)
        def date = LocalDate.parse(tradeDay, pattern)
        date = date.minusDays(num)
        return date.format(fmt)
    }

    /**
     * 交易日处理函数
     *
     * @param tradeDay T日
     * @param num N
     * @param pattern 格式化
     * @return
     */
    def tradeDay2(String tradeDay, int num, String pattern) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(pattern)
        def date = LocalDate.parse(tradeDay, pattern)
        date = date.minusDays(num)
        return date.format(fmt)
    }

}
