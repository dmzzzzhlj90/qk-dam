package com.qk.dam.metadata.catacollect.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zys
 * @date 2022/4/21 16:45
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtlasBaseSearchVO {
  private String query;
  @Builder.Default private String typeName = "Asset";
  private String classification;
  @Builder.Default private int limit = 200;
  @Builder.Default private int offset = 0;
  private String[] typeNameValue;
  private String[] nameValue;
  private String[] labelsValue;
}