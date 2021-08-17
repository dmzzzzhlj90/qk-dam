package com.qk.dm.datastandards.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataSourceJobVO {
  private String name;
  private String dataConnectType;
  private MysqlDataConnectVO mysqlDataConnect;
}
