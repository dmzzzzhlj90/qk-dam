package com.qk.dm.datastandards.datasource_connect.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Builder
@ToString
public class MysqlDb {
  private String name;
  @JsonIgnore private List<MysqlTable> mysqlTables;
}
