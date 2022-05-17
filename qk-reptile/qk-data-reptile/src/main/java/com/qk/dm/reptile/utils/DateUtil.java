package com.qk.dm.reptile.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 时间工具
 * @author wangzp
 * @date 2022-05-16 19:51
 */
public class DateUtil {
  private final static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  public static Date getDate(String timeType){
    Date date = new Date();
    switch (timeType){
      case "7"://今天
        return getFrontDay(date,1);
      case "2"://近七天
        return getFrontDay(date,7);
      case "3"://近30天
        return getFrontDay(date,30);
      case "8"://近90天
        return  getFrontDay(date,90);
      case "9"://近半年
        return getFrontDay(date,180);
      case "1"://近半年
        return getFrontDay(date,365);
      default:
        return null;
    }
  }


  // 返回某个日期前几天的日期
  public static Date getFrontDay(Date date, int i) {
    Calendar cal = new GregorianCalendar();
    cal.setTime(date);
    cal.set(Calendar.DATE, cal.get(Calendar.DATE) - i);
    return cal.getTime();
  }


}
