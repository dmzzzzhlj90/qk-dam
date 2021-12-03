package com.qk.dm.dataquality.constant.ruletemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 质量纬度
 */
public enum DimensionTypeEnum {
  INTEGRITY("INTEGRITY", "完整性"),
  UNIQUENESS("UNIQUENESS", "唯一性"),
  TIMELINESS("TIMEBASED", "及时性"),
  VALIDITY("VALIDITY", "有效性"),
  ACCURACY("ACCURACY", "准确性"),
  UNIFORMITY("UNIFORMITY", "一致性");

  private String code;
  private String name;

  DimensionTypeEnum(String code, String name) {
    this.code = code;
    this.name = name;
  }

  public static DimensionTypeEnum fromValue(Integer code) {
    for (DimensionTypeEnum b : DimensionTypeEnum.values()) {
      if (b.code.equals(code)) {
        return b;
      }
    }
    return null;
  }

  public static Map<String, String> getAllValue() {
    Map<String, String> val = new HashMap<>();
    for (DimensionTypeEnum enums : DimensionTypeEnum.values()) {
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
