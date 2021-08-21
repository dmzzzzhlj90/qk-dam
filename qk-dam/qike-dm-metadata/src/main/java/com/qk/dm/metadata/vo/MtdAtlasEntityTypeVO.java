package com.qk.dm.metadata.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MtdAtlasEntityTypeVO {
  private String guid;

  private String name;

  private String serviceType;
}
