package com.qk.dm.datastandards.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MysqlDataConnectVO {
  private String db;
  private String table;
  private String host;
  private String port;
  private String username;
  private String password;

}
