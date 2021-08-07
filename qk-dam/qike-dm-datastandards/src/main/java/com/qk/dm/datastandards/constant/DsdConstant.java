package com.qk.dm.datastandards.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DsdConstant {
  /** 目录顶级层级父级id */
  public static final String TREE_DIR_TOP_PARENT_ID = "-1";

  /** 分页默认参数设置,当前页1,每页10条,根据ID进行排序 */
  public static final int PAGE_DEFAULT_NUM = 1;

  public static final int PAGE_DEFAULT_SIZE = 10;
  public static final String PAGE_DEFAULT_SORT = "id";

  // 码表管理数据类型
  public static List<Map<String, String>> getDataTypes() {
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
    timestampMap.put("id", "OTHER_TYPE");
    timestampMap.put("label", "其他数据类型(OTHER_TYPE)");
    dataTypes.add(otherTypeMap);
    return dataTypes;
  }

  public static String defaultTableConfFields() {
    return "[{\"code_table_id\":\"code\",\"name_ch\":\"编码\",\"name_en\":\"code\",\"data_type\":\"STRING\"},{\"code_table_id\":\"value\",\"name_ch\":\"值\",\"name_en\":\"value\",\"data_type\":\"STRING\"}]";
  }

  // 码表元数据字段key
  public static final String CODE_INFO_TABLE_ID = "code_table_id";
  public static final String CODE_INFO_NAME_CH = "name_ch";
  public static final String CODE_INFO_NAME_EN = "name_en";
  public static final String CODE_INFO_DATA_TYPE = "data_type";

  // 码表元数据字段默认值__code
  public static final String CODE_INFO_CODE_EN_NAME = "code";
  public static final String CODE_INFO_CODE_CH_NAME = "编码";
  public static final String CODE_INFO_CODE_TYPE = "STRING";
  // 码表元数据字段默认值__过滤掉ID字段
  public static final String CODE_INFO_FILTER_ID = "id";
  // 码表元数据字段默认值__value
  public static final String CODE_INFO_VALUE_EN_NAME = "value";
  public static final String CODE_INFO_VALUE_CH_NAME = "值";
  public static final String CODE_INFO_VALUE_TYPE = "STRING";

  // 码表信息同步策略 更新已有表: 0 :不更新, 1: 更新
  public static final Integer CODE_INFO_INSERT = 0;
  public static final Integer CODE_INFO_UPDATE = 1;

  // 码表码值同步策略 逆向表数据: 0 :不逆向, 1: 覆盖
  public static final Integer CODE_INFO_VALUES_INSERT = 0;
  public static final Integer CODE_INFO_VALUES_UPDATE = 1;
}
