package com.qk.dm.dataquality.utils;

import java.math.BigDecimal;

/**
 * @author shenpj
 * @date 2022/1/17 3:00 下午
 * @since 1.0.0
 */
public class XmathUtil {
    private static final int DEF_DIV_SCALE = 2;

    public static Double divide(Long v1, Integer v2) {
        if (null == v1 || null == v2 || v2 == 0) {
            return 0.0;
        }
        return divide(String.valueOf(v1), String.valueOf(v2)).doubleValue();
    }

    public static Double divide(Double v1, Double v2) {
        if (null == v1 || null == v2 || v2 == 0) {
            return 0.0;
        }
        return divide(String.valueOf(v1), String.valueOf(v2)).doubleValue();
    }

    public static BigDecimal divide(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(String.valueOf(v1));
        BigDecimal b2 = new BigDecimal(String.valueOf(v2));
        return divide(b1, b2, DEF_DIV_SCALE);
    }

    /**
     * ( 相对 )精确除法运算 . 当发生除不尽情况时 , 由scale参数指 定精度 , 以后数字四舍五入
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位
     * @return 两个参数的商(BigDecimal)
     */
    public static BigDecimal divide(BigDecimal v1, BigDecimal v2, Integer scale) {
        if (null == v1) {
            return BigDecimal.ZERO;
        }
        if (null == v2) {
            v2 = BigDecimal.ONE;
        }
        if (v2.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        if (scale < 0) {
            throw new IllegalArgumentException("精确度不能小于0");
        }
        return v1.divide(v2, scale, BigDecimal.ROUND_HALF_UP);
    }
}
