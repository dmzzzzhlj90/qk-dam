package com.qk.dm.dolphin.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ReleaseStateEnum {
    OFFLINE("OFFLINE"),
    
    ONLINE("ONLINE");

    private String value;

    ReleaseStateEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static ReleaseStateEnum fromValue(String value) {
      for (ReleaseStateEnum b : ReleaseStateEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }