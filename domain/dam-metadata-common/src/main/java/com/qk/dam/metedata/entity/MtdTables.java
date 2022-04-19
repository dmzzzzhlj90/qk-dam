package com.qk.dam.metedata.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MtdTables {

  private String guid;
  private String typeName;
  private String entityStatus;
  private String displayText;
  private String relationshipType;
  private String relationshipGuid;
  private String relationshipStatus;
  private String comment;
}
