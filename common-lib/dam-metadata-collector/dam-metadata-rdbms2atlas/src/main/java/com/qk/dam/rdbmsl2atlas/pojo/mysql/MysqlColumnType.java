package com.qk.dam.rdbmsl2atlas.pojo.mysql;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.apache.atlas.model.instance.AtlasEntity;

@Data
@Builder
@ToString
public class MysqlColumnType {
  private String name;
  private String displayName;
  private String data_type;
  private int position;
  private String default_value;
  private String description;
  private String extra;
  private boolean isNullable;
  private boolean isPrimaryKey;
  private String owner;
  private String qualifiedName;
  @JsonIgnore private AtlasEntity columnEntity;
}
