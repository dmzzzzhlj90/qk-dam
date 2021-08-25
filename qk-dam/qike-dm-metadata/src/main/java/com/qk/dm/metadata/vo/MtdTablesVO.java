package com.qk.dm.metadata.vo;

import lombok.Data;

@Data
public class MtdTablesVO {

  private String guid;
  private String typeName;
  private String entityStatus;
  private String displayText;
  private String relationshipType;
  private String relationshipGuid;
  private String relationshipStatus;
}
