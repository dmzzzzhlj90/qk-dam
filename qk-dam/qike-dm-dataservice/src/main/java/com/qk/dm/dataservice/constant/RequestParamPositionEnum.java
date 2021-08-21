package com.qk.dm.dataservice.constant;

import org.springframework.util.StringUtils;

/**
 * 入参定义参数位置位置
 *
 * @author wjq
 * @date 20210821
 * @since 1.0.0
 */
public enum RequestParamPositionEnum {
  /** 请求参数 */
  REQUEST_PARAMETER_POSITION_QUERY("REQUEST_PARAMETER_POSITION_QUERY"),

  /** HTTP 请求头 */
  REQUEST_PARAMETER_POSITION_HEADER("REQUEST_PARAMETER_POSITION_HEADER"),

  /** Cookie */
  REQUEST_PARAMETER_POSITION_COOKIE("REQUEST_PARAMETER_POSITION_COOKIE");

  private String typeName;

  RequestParamPositionEnum(String typeName) {
    this.typeName = typeName;
  }

  public static RequestParamPositionEnum getValByTypeName(String typeName) {
    if (StringUtils.isEmpty(typeName)) {
      return null;
    }
    for (RequestParamPositionEnum enums : RequestParamPositionEnum.values()) {
      if (typeName.equals(enums.typeName)) {
        return enums;
      }
    }
    return null;
  }

  public String getTypeName() {
    return typeName;
  }
}
