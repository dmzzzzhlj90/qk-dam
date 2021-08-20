package com.qk.dm.dataservice.constant;

import java.util.*;

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

  public static Map<String, String> getRequestParasHeaderInfos() {
    LinkedHashMap<String, String> headMap = new LinkedHashMap<>();
    headMap.put("paraName", "http请求参数");
    headMap.put("paraPosition", "入参位置");
    headMap.put("paraType", "参数类型");
    headMap.put("necessary", "是否必填");
    headMap.put("supportNull", "允许空值");
    headMap.put("defaultValue", "示例值");
    headMap.put("exampleValue", "默认值");
    headMap.put("description", "描述");
    return headMap;
  }

  public static Map<String, String> getBackendParaHeaderInfo() {
    LinkedHashMap<String, String> headMap = new LinkedHashMap<>();
    headMap.put("paraName", "入参名称");
    headMap.put("paraPosition", "入参位置");
    headMap.put("paraType", "入参类型");
    headMap.put("backendParaName", "后端参数名称");
    headMap.put("backendParaPosition", "后端参数位置");
    return headMap;
  }

  public static Map<String, String> getConstantParaHeaderInfo() {
    LinkedHashMap<String, String> headMap = new LinkedHashMap<>();
    headMap.put("constantParaName", "常量参数名称");
    headMap.put("constantParaPosition", "参数位置");
    headMap.put("constantParaType", "参数类型");
    headMap.put("constantParaValue", "参数值");
    headMap.put("description", "描述");
    return headMap;
  }
}
