package com.qk.dm.metadata.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MtdApiDbVO {
  private String guid;
  private String displayText;
  private String typeName;
}
