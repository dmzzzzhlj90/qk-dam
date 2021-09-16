package com.qk.dm.datastandards.datasource_connect.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class MysqlDb {
  private String name;
  @JsonIgnore private List<MysqlTable> mysqlTables;
}
