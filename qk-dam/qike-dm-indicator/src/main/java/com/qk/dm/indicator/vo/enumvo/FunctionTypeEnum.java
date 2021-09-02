package com.qk.dm.indicator.vo.enumvo;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum FunctionTypeEnum {
  SUM(1, "聚合函数");

  Integer type;
  String typeName;

  public Integer getType() {
    return type;
  }

  public String getTypeName() {
    return typeName;
  }

  FunctionTypeEnum(Integer type, String typeName) {
    this.type = type;
    this.typeName = typeName;
  }

  public static FunctionTypeEnum getFunctionTypeEnum(Integer status) {
    for (FunctionTypeEnum sum : values()) {
      if (status == sum.type) {
        return sum;
      }
    }
    return SUM;
  }

  public static Map<Object, String> EnumToMap() {
    return Arrays.stream(values())
        .collect(Collectors.toMap(FunctionTypeEnum::getType, FunctionTypeEnum::getTypeName));
  }
}
