package com.qk.dam.rdbmsl2atlas.pojo.mysql;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class MysqlDbType {
  private String name;
  private String displayName;
  private String description;
  private String owner;
  private String qualifiedName;
  private String applicationName;
  private String serverInfo;
  @JsonIgnore private List<MysqlTableType> mysqlTableTypes;

}
