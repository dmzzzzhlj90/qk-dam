package com.qk.dm.dataquality.utils;

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
     * Date 转 String
     *
     * @param time
     * @return
     */
    public static String toStr(Date time) {
        if (time == null) {
            return null;
        }
        return toStr(time, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 根据格式，Date 转 string
     *
     * @param time
     * @param format
     * @return
     */
    public static String toStr(Date time, String format) {
        SimpleDateFormat df = null;
        if (null == format) {
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
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
