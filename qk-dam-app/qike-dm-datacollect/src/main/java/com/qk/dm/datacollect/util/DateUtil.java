package com.qk.dm.datacollect.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author shenpj
 * @date 2022/1/17 2:53 下午
 * @since 1.0.0
 */
public class DateUtil {

    private final static SimpleDateFormat SHORTSDF = new SimpleDateFormat("yyyy-MM-dd");
    private final static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final static SimpleDateFormat LONGSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public static Date parseDate(String strDate,String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date parseDate(String strDate) {
        try {
            return SDF.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据格式，Date 转 string
     *
     * @param time
     * @return
     */
    public static String toStr(Date time) {
        try {
            return toStr(time,"yyyy-MM-dd HH:mm:ss");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String toStr(Date time, String format) {
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

    /**
     * 获取小时
     * @param date
     * @return
     */
    public static int hour(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取分钟
     * @param date
     * @return
     */
    public static int minute(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MINUTE);
    }

    /**
     * 获取秒
     * @param date
     * @return
     */
    public static int second(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.SECOND);
    }



}
