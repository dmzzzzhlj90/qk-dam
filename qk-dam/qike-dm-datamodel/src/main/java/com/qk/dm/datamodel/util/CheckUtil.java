package com.qk.dm.datamodel.util;

import java.text.NumberFormat;
import java.util.*;
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
    String result = numberFormat.format((float)size/(float)size1*100)+"%";
    return result;
  }

  public static List<Map<String,String>> getDataTypes() {
    List<Map<String, String>> dataTypes = new ArrayList();
    Map<String, String> stringMap = new HashMap<>();
    stringMap.put("id", "STRING");
    stringMap.put("label", "字符类型(STRING)");
    dataTypes.add(stringMap);

    Map<String, String> doubleMap = new HashMap<>();
    doubleMap.put("id", "DOUBLE");
    doubleMap.put("label", "双精度(DOUBLE)");
    dataTypes.add(doubleMap);

    Map<String, String> bigintMap = new HashMap<>();
    bigintMap.put("id", "BIGINT");
    bigintMap.put("label", "长整型(BIGINT)");
    dataTypes.add(bigintMap);

    Map<String, String> booleanMap = new HashMap<>();
    booleanMap.put("id", "BOOLEAN");
    booleanMap.put("label", "布尔类型(BOOLEAN)");
    dataTypes.add(booleanMap);

    Map<String, String> decimalMap = new HashMap<>();
    decimalMap.put("id", "DECIMAL");
    decimalMap.put("label", "高精度(DECIMAL)");
    dataTypes.add(decimalMap);

    Map<String, String> dateMap = new HashMap<>();
    dateMap.put("id", "DATE");
    dateMap.put("label", "日期类型(DATE)");
    dataTypes.add(dateMap);

    Map<String, String> timestampMap = new HashMap<>();
    timestampMap.put("id", "TIMESTAMP");
    timestampMap.put("label", "时间戳类型(TIMESTAMP)");
    dataTypes.add(timestampMap);

    Map<String, String> otherTypeMap = new HashMap<>();
    otherTypeMap.put("id", "OTHER_TYPE");
    otherTypeMap.put("label", "其他数据类型(OTHER_TYPE)");
    dataTypes.add(otherTypeMap);
    return dataTypes;
  }
}