package com.qk.dm.metadata.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shenpj
 * @date 2021/8/26 5:00 下午
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MtdAtlasSearchVO {
  String attributeName;
  /** 数组，或者关系 */
  String[] attributeValue;

  String operator;
}
