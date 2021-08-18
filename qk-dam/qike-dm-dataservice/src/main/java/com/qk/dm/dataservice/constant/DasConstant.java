package com.qk.dm.dataservice.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DasConstant {
  /** 目录顶级层级父级id */
  public static final String TREE_DIR_TOP_PARENT_ID = "-1";

  /** 分页默认参数设置,当前页1,每页10条,根据ID进行排序 */
  public static final int PAGE_DEFAULT_NUM = 1;

  public static final int PAGE_DEFAULT_SIZE = 10;
  public static final String PAGE_DEFAULT_SORT = "id";

  // API类型-注册API
  public static final String REGISTER_API_CODE = "REGISTER-API";
  // API类型-DM取数API
  public static final String DM_SOURCE_API_CODE = "DM-SOURCE-API";
  // API类型
  public static List<Map<String, String>> getApiType() {
    List<Map<String, String>> apiTypeList = new ArrayList();
    Map<String, String> registerTypeMap = new HashMap<>();
    registerTypeMap.put("code", "REGISTER-API");
    registerTypeMap.put("value", "注册API");
    apiTypeList.add(registerTypeMap);

    Map<String, String> dmSourceMap = new HashMap<>();
    dmSourceMap.put("code", "DM-SOURCE-API");
    dmSourceMap.put("value", "DM取数API");
    apiTypeList.add(dmSourceMap);
    return apiTypeList;
  }

  // 取数方式
  public static List<Map<String, String>> getDMSourceType() {
    List<Map<String, String>> sourceTypeList = new ArrayList();
    Map<String, String> configTypeMap = new HashMap<>();
    configTypeMap.put("code", "CONFIG-TYPE");
    configTypeMap.put("value", "配置方式");
    sourceTypeList.add(configTypeMap);

    Map<String, String> dmSourceMap = new HashMap<>();
    dmSourceMap.put("code", "SQL-SCRIPT-TYPE");
    dmSourceMap.put("value", "SQL脚本方式");
    sourceTypeList.add(dmSourceMap);
    return sourceTypeList;
  }
}
