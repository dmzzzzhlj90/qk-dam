package com.qk.dam.indicator.common.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum DateCycleEnum {
  YEARS("date-0001", "年"),
  MONTH("date-0002", "月"),
  WEEK("date-0003", "星期"),
  DAY("date-0004", "日"),
  HOURS("date-0005","时");

  String code;
  String name;

  public String getCode() {
    return code;
  }

  public String getName() {
    return name;
  }

  DateCycleEnum(String code, String name) {
    this.code = code;
    this.name = name;
  }

  public static DateCycleEnum getFunctionTypeEnum(String code) {
    for (DateCycleEnum date : values()) {
      if (code == date.code) {
        return date;
      }
    }
    return DAY;
  }

  public static Map<Object, String> EnumToMap() {
    return Arrays.stream(values())
        .collect(Collectors.toMap(DateCycleEnum::getCode, DateCycleEnum::getName));
  }
}
