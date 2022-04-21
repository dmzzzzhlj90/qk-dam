package com.qk.dm.datacollect.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

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
            return SDF.format(time);
        } catch (Exception e) {
            e.printStackTrace();
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


    /**
     * 根据传入时间获得本天的开始时间
     *
     * @param date
     * @return
     */
    public static Date beginOfDay(Date date) {
        Date dt = null;
        if (date != null) {
            try {
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.MILLISECOND, 0);
                dt = cal.getTime();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dt;
    }

    /**
     * 根据传入时间获得本天的结束时间
     *
     * @param date
     * @return
     */
    public static Date endOfDay(Date date) {
        Date dt = null;
        if (date != null) {
            try {
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                cal.set(Calendar.HOUR_OF_DAY, 23);
                cal.set(Calendar.SECOND, 59);
                cal.set(Calendar.MINUTE, 59);
                cal.set(Calendar.MILLISECOND, 0);
                dt = cal.getTime();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dt;
    }

    /**
     * 时间加减去X天
     *
     * @param date
     * @param day
     * @return
     */
    public static Date getDateReductionDay(Date date, Integer day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }

    /**
     * 获取两个日期之间的所有日期
     *
     * @return
     */
    public static Map<String, Integer> getDatesByDay(Date date, Date dateEnd) {
        //保存日期的集合 
        Map<String, Integer> map = new LinkedHashMap<>();
        //用Calendar 进行日期比较判断
        Calendar cd = Calendar.getInstance();
        while (date.getTime() <= dateEnd.getTime()) {
            map.put(SHORTSDF.format(date), 0);
            cd.setTime(date);
            //增加一天 放入集合
            cd.add(Calendar.DATE, 1);
            date = cd.getTime();
        }
        return map;
    }
}
