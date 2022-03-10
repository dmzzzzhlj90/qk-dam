package com.qk.dam.authority.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author shenpj
 * @date 2022/3/7 10:37
 * @since 1.0.0
 */
public class TimeUtil {
    private final static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * string 转 Date format  "yyyy-MM-dd HH:mm:ss"
     *
     * @param format
     * @param strDate
     * @return
     */
    public static Date parseDate(String format, String strDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据格式，Date 转 string
     *
     * @param format
     * @param time
     * @return
     */
    public static String toStr(String format, Date time) {
        SimpleDateFormat df = null;
        if (null == format) {
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else {
            df = new SimpleDateFormat(format);
        }
        try {
            return df.format(time);
        } catch (Exception e) {
            return null;
        }
    }

    public static String parseLong(Long timestamp){
        Date date = new Date(timestamp);
        return toStr("yyyy-MM-dd HH:mm:ss",date);
    }
}
