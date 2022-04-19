package com.qk.dam.metadata.catacollect.pojo.mysql;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.apache.atlas.model.instance.AtlasEntity;

import java.util.List;

@Data
@Builder
@ToString
public class MysqlTableType {
  private String name;
  private String type;
  private String displayName;
  private String name_path;
  private Long createTime;
  private Long updateTime;
  private String description;
  private String comment;
  private String owner;
  private String tableRows;
  private Long dataLength;
  private Long indexLength;
  private String tableCollation;
  private String qualifiedName;
  @JsonIgnore private List<MysqlColumnType> mysqlColumnTypes;
  @JsonIgnore private AtlasEntity tableEntity;
  @JsonIgnore private List<AtlasEntity> columnEntity;
}
