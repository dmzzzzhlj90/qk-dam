package com.qk.dm.datamodel.util;

import java.text.NumberFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author zys
 * @date 2021/11/10 20:27
 * @since 1.0.0
 */
public class CheckUtil {
  public static Boolean checkRepeat(List<String> list) {
    AtomicBoolean checkParms = new AtomicBoolean(false);
    Set stringSet=new HashSet<>(list);
    if (stringSet.size() == list.size()){
      checkParms.set(true);
    }
    return checkParms.get();
  }

  /**
   * 获取百分比
   * @param size
   * @param size1
   * @return
   */
  public static String getCoverage(int size, int size1) {
    NumberFormat numberFormat = NumberFormat.getInstance();
    // 设置精确到小数点后2位
    numberFormat.setMaximumFractionDigits(2);
    String result = numberFormat.format((float)size/(float)size1*100);
    return result;
  }
}