package com.qk.dm.dataservice.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期工具类
 *
 * @author wjq
 * @date 2022/03/14
 * @since 1.0.0
 */
@Slf4j
public final class DateUtil {

    private static final String STR_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    private static final String STR_YYYY_MM_DD = "yyyy-MM-dd";
    private static final String STR_YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
    private static final String STR_ERROR = "error";

    /**
     * 小时
     */
    public static final int HOUR = 60 * 60 * 1000;
    public static final int DAY_HOUR_COUNT = 24;

    /**
     * 天,分组间隔计算时间参数设置
     */
    public static final int DAY_GROUP_NUM = 6;
    public static final int DAY_INTERVAL = 4 * HOUR;

    /**
     * 周,分组间隔计算时间参数设置
     */
    public static final int WEEK_GROUP_NUM = 7;
    public static final int WEEK_INTERVAL = DAY_HOUR_COUNT * HOUR;

    /**
     * 月,分组间隔计算时间参数设置
     */
    public static final int MONTH_GROUP_NUM = 6;
    public static final int MONTH_INTERVAL = 5 * DAY_HOUR_COUNT * HOUR;
    public static final int MONTH_DAY_COUNT = 30;

    /**
     * 年,分组间隔计算时间参数设置
     */
    public static final int YEAR_GROUP_NUM = 12;
    public static final int YEAR_INTERVAL = 1;


    /**
     * 字符串日期转为Date
     *
     * @param time
     * @return
     */
    public static Date strToDate(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(STR_YYYY_MM_DD_HH_MM_SS);
        Date dateTime = null;
        try {
            dateTime = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateTime;
    }

    /**
     * 凌晨BEGIN
     *
     * @return
     */
    public static String beginTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(STR_YYYY_MM_DD_HH_MM_SS);

        Calendar cld = Calendar.getInstance();
        cld.setTime(date);

        String todayStr = simpleDateFormat.format(cld.getTime());
        //前一天凌晨0点的字符串
        return todayStr.substring(0, 10) + " 00:00:00";
    }

    /**
     * 凌晨END
     *
     * @return
     */
    public static String endTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(STR_YYYY_MM_DD_HH_MM_SS);

        Calendar cld = Calendar.getInstance();
        cld.setTime(date);

        String todayStr = simpleDateFormat.format(cld.getTime());
        //前一天凌晨0点的字符串
        return todayStr.substring(0, 10) + " 23:59:59";
    }

    /**
     * Date转字符串
     *
     * @param date
     * @return
     */
    public static String dateToStr(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(STR_YYYY_MM_DD_HH_MM_SS);
        return dateFormat.format(date);
    }

    /**
     * 周一
     *
     * @return
     */
    public static Date monday() {
        Calendar cld = Calendar.getInstance(Locale.CHINA);
        cld.setFirstDayOfWeek(Calendar.MONDAY);
        cld.setTimeInMillis(System.currentTimeMillis());

        cld.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cld.getTime();
    }

    /**
     * 周末
     *
     * @return
     */
    public static Date sunday() {
        Calendar cld = Calendar.getInstance(Locale.CHINA);
        cld.setFirstDayOfWeek(Calendar.MONDAY);
        cld.setTimeInMillis(System.currentTimeMillis());

        cld.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return cld.getTime();
    }

    /**
     * 月初
     *
     * @return
     */
    public static Date monthStart() {
        Calendar cld = Calendar.getInstance();
        cld.add(Calendar.MONTH, 0);
        cld.set(Calendar.DAY_OF_MONTH, 1);
        return cld.getTime();
    }

    /**
     * 月初
     *
     * @return
     */
    public static Date monthEnd() {
        Calendar cld = Calendar.getInstance();
        cld.add(Calendar.MONTH, 1);
        cld.set(Calendar.DAY_OF_MONTH, 0);
        return cld.getTime();
    }

    /**
     * 取得当月天数
     */
    public static int getCurrentMonthLastDay() {
        Calendar cld = Calendar.getInstance();
        cld.set(Calendar.DATE, 1);
        cld.roll(Calendar.DATE, -1);
        return cld.get(Calendar.DATE);
    }

    /**
     * 当年年初
     */
    public static Date yearStart() {
        return getYearFirst(getSysYear());
    }

    /**
     * 当年年末
     */
    public static Date yearEnd() {
        return getYearEnd(getSysYear());
    }

    /**
     * 获取当前时间年份
     */
    public static Integer getSysYear() {
        Calendar cld = Calendar.getInstance();
        return cld.get(Calendar.YEAR);

    }

    /**
     * 获取某年第一天日期
     */
    public static Date getYearFirst(int year) {
        Calendar cld = Calendar.getInstance();
        cld.clear();
        cld.set(Calendar.YEAR, year);
        return cld.getTime();

    }

    /**
     * 获取某年最后一天日期
     */
    public static Date getYearEnd(int year) {
        Calendar cld = Calendar.getInstance();
        cld.clear();
        cld.set(Calendar.YEAR, year);
        cld.roll(Calendar.DAY_OF_YEAR, -1);
        return cld.getTime();
    }

    /**
     * 获取某个时间的月初
     *
     * @return
     */
    public static Date getMonthStart(String time) {
        Calendar cld = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat(STR_YYYY_MM_DD_HH_MM_SS);

        try {
            cld.setTime(formatter.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cld.add(Calendar.MONTH, 0);
        cld.set(Calendar.DAY_OF_MONTH, 1);
        return cld.getTime();
    }

    /**
     * 获取某个时间的月末
     *
     * @return
     */
    public static Date getMonthEnd(String time) {
        Calendar cld = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat(STR_YYYY_MM_DD_HH_MM_SS);

        try {
            cld.setTime(formatter.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cld.add(Calendar.MONTH, 1);
        cld.set(Calendar.DAY_OF_MONTH, 0);
        return cld.getTime();
    }

    /**
     * 获取指定日期下个月月初
     *
     * @param beginTime
     * @return
     */
    public static Date getNextMonthStart(Date beginTime) {
        Calendar cld = Calendar.getInstance();
        cld.setTime(beginTime);
        cld.add(Calendar.MONTH, 1);
        cld.set(Calendar.DAY_OF_MONTH, 1);
        return cld.getTime();
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.getNextMonthStart(new Date()));
    }
}
