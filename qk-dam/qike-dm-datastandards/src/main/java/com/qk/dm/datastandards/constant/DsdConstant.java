package com.qk.dm.datastandards.constant;

import java.util.Arrays;
import java.util.List;

public class DsdConstant {
  /** 目录顶级层级父级id */
  public static final String TREE_DIR_TOP_PARENT_ID = "-1";

  /** 分页默认参数设置,当前页1,每页10条,根据ID进行排序 */
  public static final int PAGE_DEFAULT_NUM = 1;

  public static final int PAGE_DEFAULT_SIZE = 10;
  public static final String PAGE_DEFAULT_SORT = "id";

  // 数据容量下拉列表
  // 字符串
  public static final String DATA_TYPE_CAPACITY_STRING = "string";

  public static List getListString() {
    return Arrays.asList("10", "20", "50", "100", "200", "500", "1000", "2000", "4000");
  }

  // 双精度
  public static final String DATA_TYPE_CAPACITY_DOUBLE = "Double";

  public static List getListDouble() {
    return Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
  }

  // 布尔类型
  public static final String DATA_TYPE_CAPACITY_BOOLEAN = "boolean";

  public static List getListBoolean() {
    return Arrays.asList("0", "1");
  }

  // 高精度
  public static final String DATA_TYPE_CAPACITY_DECIMAL = "decimal";

  public static List getListDecimal() {
    return Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
  }

  // 日期类型
  public static final String DATA_TYPE_CAPACITY_DATE = "date";

  public static List getListDate() {
    return Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8");
  }

  // 时间戳类型
  public static final String DATA_TYPE_CAPACITY_TIMESTAMP = "timestamp";

  public static List getListTimeStamp() {
    return Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8");
  }
}
