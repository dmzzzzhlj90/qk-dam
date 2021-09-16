package com.qk.dm.dataservice.constant;

import java.util.HashMap;
import java.util.Map;
import org.springframework.util.StringUtils;

/**
 * 数据服务同步状态
 *
 * @author wjq
 * @date 20210907
 * @since 1.0.0
 */
public enum SyncStatusEnum {
  /** 新建未同步 */
  CREATE_NO_SYNC("0", "新建未同步"),

  /** 同步失败 */
  CREATE_FAIL_SYNC("1", "同步失败"),

  /** 已同步数据服务网关 */
  REQUEST_PARAMETER_POSITION_PATH("2", "已同步数据服务网关");

  private String code;
  private String name;

  SyncStatusEnum(String code, String name) {
    this.code = code;
    this.name = name;
  }

  public static SyncStatusEnum getVal(String name) {
    if (StringUtils.isEmpty(name)) {
      return null;
    }
    for (SyncStatusEnum enums : SyncStatusEnum.values()) {
      if (name.equals(enums.name)) {
        return enums;
      }
    }
    return null;
  }

  public static Map<String, String> getAllValue() {
    Map<String, String> val = new HashMap<>();
    for (SyncStatusEnum enums : SyncStatusEnum.values()) {
      val.put(enums.code, enums.name);
    }
    return val;
  }

  public String getCode() {
    return code;
  }

  public String getName() {
    return name;
  }
}
