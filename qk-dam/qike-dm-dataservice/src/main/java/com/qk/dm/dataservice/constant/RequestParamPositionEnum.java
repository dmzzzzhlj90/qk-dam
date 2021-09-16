package com.qk.dm.dataservice.constant;

import java.util.HashMap;
import java.util.Map;
import org.springframework.util.StringUtils;

/**
 * 入参定义参数位置
 *
 * @author wjq
 * @date 20210821
 * @since 1.0.0
 */
public enum RequestParamPositionEnum {
  /** 请求参数 */
  REQUEST_PARAMETER_POSITION_QUERY("QUERY", "REQUEST_PARAMETER_POSITION_QUERY"),

  /** HTTP 请求头 */
  REQUEST_PARAMETER_POSITION_HEADER("HEADER", "REQUEST_PARAMETER_POSITION_HEADER"),

  /** PATH */
  REQUEST_PARAMETER_POSITION_PATH("PATH", "REQUEST_PARAMETER_POSITION_PATH"),

  /** Cookie */
  REQUEST_PARAMETER_POSITION_COOKIE("COOKIE", "REQUEST_PARAMETER_POSITION_COOKIE");

  private String name;
  private String typeName;

  RequestParamPositionEnum(String name, String typeName) {
    this.name = name;
    this.typeName = typeName;
  }

  public static RequestParamPositionEnum getVal(String name) {
    if (StringUtils.isEmpty(name)) {
      return null;
    }
    for (RequestParamPositionEnum enums : RequestParamPositionEnum.values()) {
      if (name.equals(enums.name)) {
        return enums;
      }
    }
    return null;
  }

  public static Map<String, String> getAllValue() {
    Map<String, String> val = new HashMap<>();
    for (RequestParamPositionEnum enums : RequestParamPositionEnum.values()) {
      val.put(enums.name, enums.typeName);
    }
    return val;
  }

  public String getName() {
    return name;
  }

  public String getTypeName() {
    return typeName;
  }
}
