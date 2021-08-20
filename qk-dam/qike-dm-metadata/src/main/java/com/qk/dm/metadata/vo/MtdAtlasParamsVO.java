package com.qk.dm.metadata.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MtdAtlasParamsVO {
  private String query;

  private String typeName;

  private String classification;
  @Builder.Default private int limit = 20;
  @Builder.Default private int offse = 0;
}
