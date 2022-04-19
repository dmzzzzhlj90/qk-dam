package com.qk.dam.metadata.catacollect.pojo.mysql;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

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
