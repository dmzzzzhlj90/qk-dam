package com.qk.dam.metedata.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MtdApiParams {
  private String typeName;
  private String dbName;
  private String tableName;
  private String server;
}
