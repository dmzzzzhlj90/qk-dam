package com.qk.dam.openapi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ComponentField {
  private String fieldName;
  private String desc;
  private String type;
  private Object defaultValue;
  private boolean required;
}
